package com.yuoj.judge.strategy.impl;

import cn.hutool.json.JSONUtil;
import com.yuoj.judge.strategy.JudgeContext;
import com.yuoj.judge.strategy.JudgeStrategy;
import com.yuoj.model.dto.question.JudgeCase;
import com.yuoj.model.dto.question.JudgeConfig;
import com.yuoj.model.dto.questionsubmit.JudgeInfo;
import com.yuoj.model.entity.Question;
import com.yuoj.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * 默认判断逻辑
 */
public class DefaultJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        Question question = judgeContext.getQuestion();
        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();

        //返回类
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);


        JudgeInfoMessageEnum judgeInfoMessage = JudgeInfoMessageEnum.ACCEPTED;
        //判断输入输出用例数量是否一致
        if (outputList.size() != inputList.size()) {
            judgeInfoMessage = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessage.toString());
            return judgeInfoResponse;
        }
        //判断输入用例与理想输出用例是否相等
        for (int i = 0; i < outputList.size(); i++) {
            if (!outputList.get(i).equals(judgeCaseList.get(i).getOutput())) {
                judgeInfoMessage = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessage.toString());
                return judgeInfoResponse;
            }
        }
        //判断时间/内存是否符合要求
        String judgeConfig = question.getJudgeConfig();
        JudgeConfig neddJudgeConfig = JSONUtil.toBean(judgeConfig, JudgeConfig.class);
        Long needMemory = neddJudgeConfig.getMemoryLimit();
        Long needTime = neddJudgeConfig.getTimeLimit();
        if (memory > needMemory) {
            judgeInfoMessage = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessage.toString());
            return judgeInfoResponse;
        }
        if (time > needTime) {
            judgeInfoMessage = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessage.toString());
            return judgeInfoResponse;
        }

        judgeInfoResponse.setMessage(judgeInfoMessage.toString());
        return judgeInfoResponse;
    }
}
