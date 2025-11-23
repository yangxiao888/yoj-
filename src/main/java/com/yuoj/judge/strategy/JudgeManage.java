package com.yuoj.judge.strategy;

import com.yuoj.judge.strategy.impl.DefaultJudgeStrategy;
import com.yuoj.judge.strategy.impl.JavaJudgeStrategy;
import com.yuoj.model.dto.questionsubmit.JudgeInfo;
import com.yuoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理(判断选取策略)
 */
@Service
public class JudgeManage {
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        if ("java".equals(language)) {
            return new JavaJudgeStrategy().doJudge(judgeContext);
        }
        return new DefaultJudgeStrategy().doJudge(judgeContext);

    }
}
