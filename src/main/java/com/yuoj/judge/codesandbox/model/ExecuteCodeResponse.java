package com.yuoj.judge.codesandbox.model;

import com.yuoj.model.dto.questionsubmit.JudgeInfo;
import lombok.Data;

import java.util.List;

/**
 * 代码沙箱返回类
 */
@Data
public class ExecuteCodeResponse {

    /**
     * 状态
     */
    private Integer status;

    /**
     * 输出
     */
    private List<String> outputList;

    /**
     * 判断对象
     */
    private JudgeInfo judgeInfo;

}
