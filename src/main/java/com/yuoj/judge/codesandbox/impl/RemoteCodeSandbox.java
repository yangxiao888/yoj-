package com.yuoj.judge.codesandbox.impl;


import com.yuoj.judge.codesandbox.CodeSandbox;
import com.yuoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yuoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱
 */
public class RemoteCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {
        System.out.println("remote Code Sandbox");
        return null;
    }
}
