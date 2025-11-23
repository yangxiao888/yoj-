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
 * Java 判题策略(假设编译需要10秒种，需要减去这个编译的时间)
 */
public class JavaJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        List<String> inputList = judgeContext.getInputList();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        Question question = judgeContext.getQuestion();
        List<String> outputList = judgeContext.getOutputList();
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();

        //返回类
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);
        


        JudgeInfoMessageEnum judgeInfoMessage = JudgeInfoMessageEnum.ACCEPTED;
        //判断输入输出用例数量是否一致
        if(outputList.size() != inputList.size()){
            judgeInfoMessage = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessage.toString());
            return judgeInfoResponse;
        }
        //判断输入用例与理想输出用例是否相等
        for (int i = 0; i < outputList.size(); i++) {
            if(!outputList.get(i).equals(judgeCaseList.get(i).getOutput())){
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
        Long JAVA_TIME_COST = 10000L;
        if((memory - JAVA_TIME_COST) >needMemory){
            judgeInfoMessage = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessage.toString());
            return judgeInfoResponse;
        }
        if(time > needTime){
            judgeInfoMessage = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessage.toString());
            return judgeInfoResponse;
        }

        judgeInfoResponse.setMessage(judgeInfoMessage.toString());
        return judgeInfoResponse;
    }
}
