package com.yuoj.judge.codesandbox;

import com.yuoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yuoj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 代码沙箱代理类（提高能力，日志）
 */
@Slf4j
public class CodeSandboxPorxy implements CodeSandbox {

    private final CodeSandbox codeSandbox;

    public CodeSandboxPorxy(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {
        log.info("代码沙箱请求参数：" + request);
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(request);
        log.info("代码沙箱响应结果：" + executeCodeResponse);
        return executeCodeResponse;
    }
}
