package com.yuoj.judge;


import com.yuoj.model.entity.QuestionSubmit;

/**
 * 判题服务
 */

public interface JudgeService {

    QuestionSubmit doJudge(Long questionSubmitId);
}
