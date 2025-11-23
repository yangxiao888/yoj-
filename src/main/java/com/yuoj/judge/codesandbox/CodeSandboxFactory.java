package com.yuoj.judge.codesandbox;

import com.yuoj.judge.codesandbox.impl.ExampleCodeSandboxImpl;
import com.yuoj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.yuoj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱工厂
 */
public class CodeSandboxFactory {

    /**
     * 根据传来的字符串，获取对应的代码沙箱
     *
     * @param type
     * @return
     */
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandboxImpl();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandboxImpl();
        }

    }

}
