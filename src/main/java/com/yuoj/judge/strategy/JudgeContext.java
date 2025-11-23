package com.yuoj.judge.strategy;

import com.yuoj.model.dto.question.JudgeCase;
import com.yuoj.model.dto.questionsubmit.JudgeInfo;
import com.yuoj.model.entity.Question;
import com.yuoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 判断策略上下文
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;
}
