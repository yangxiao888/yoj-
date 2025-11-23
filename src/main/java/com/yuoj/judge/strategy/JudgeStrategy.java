package com.yuoj.judge.strategy;

import com.yuoj.model.dto.questionsubmit.JudgeInfo;

/**
 * 结果判断策略接口
 */
public interface JudgeStrategy {

    /**
     * 执行判断
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
