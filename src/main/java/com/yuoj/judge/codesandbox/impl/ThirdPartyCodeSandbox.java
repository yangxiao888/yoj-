package com.yuoj.judge.codesandbox.impl;


import com.yuoj.judge.codesandbox.CodeSandbox;
import com.yuoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yuoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {
        System.out.println("thirdParty Code Sandbox");
        return null;
    }
}
