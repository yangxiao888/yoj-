package com.yuoj.judge;

import cn.hutool.json.JSONUtil;
import com.yuoj.common.ErrorCode;
import com.yuoj.exception.BusinessException;
import com.yuoj.judge.codesandbox.CodeSandbox;
import com.yuoj.judge.codesandbox.CodeSandboxFactory;
import com.yuoj.judge.codesandbox.CodeSandboxPorxy;
import com.yuoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yuoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yuoj.judge.strategy.JudgeContext;
import com.yuoj.judge.strategy.JudgeManage;
import com.yuoj.model.dto.question.JudgeCase;
import com.yuoj.model.dto.questionsubmit.JudgeInfo;
import com.yuoj.model.entity.Question;
import com.yuoj.model.entity.QuestionSubmit;
import com.yuoj.model.enums.QuestionSubmitStatusEnum;
import com.yuoj.service.QuestionService;
import com.yuoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 判题服务实现类
 */
@Service
public class JudgeServiceImpl implements JudgeService {
    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManage judgeManage;


    @Value("${code-sandbox.type}")
    private String codeSandboxType;

    @Override
    public QuestionSubmit doJudge(Long questionSubmitId) {
        //1.根据传入的题目提交 ID ,获取提交信息(代码、语言等)和题目信息
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目提交不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        //2.判断状态(只执行等待中的提交题目)
        Integer status = questionSubmit.getStatus();
        if (status != null && !status.equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目提交状态不可判题");
        }
        //3.更新题目提交状态
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目提交更新错误");
        }
        //4.调用代码沙箱
        String judgeCase = question.getJudgeCase();
        List<String> input = JSONUtil.toList(judgeCase, JudgeCase.class).stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(questionSubmit.getCode())
                .language(questionSubmit.getLanguage())
                .inputList(input)
                .build();
        //根据类型获取沙箱
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(codeSandboxType);
        //调用代理获取沙箱结果
        codeSandbox = new CodeSandboxPorxy(codeSandbox);
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);

        //5.根据返回结果进行判断
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(input);
        judgeContext.setOutputList(executeCodeResponse.getOutputList());
        judgeContext.setJudgeCaseList(JSONUtil.toList(judgeCase, JudgeCase.class));
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);

        JudgeInfo judgeInfo = judgeManage.doJudge(judgeContext);

        //修改题目提交的状态
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目提交更新错误");
        }

        //返回最新的提交信息
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionSubmitId);
        return questionSubmitResult;

    }
}
