package com.yuoj.judge.codesandbox;

import com.yuoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yuoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口
 */
public interface CodeSandbox {
    /**
     * 执行代码
     *
     * @param request 判题参数
     * @return 判题结果
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest request);
}
