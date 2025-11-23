package com.yuoj.judge.codesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 代码沙箱请求类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteCodeRequest {

    /**
     * 代码
     */

    private String code;


    /**
     * 输入
     */
    private List<String> inputList;

    /**
     * 编程语言
     */
    private String language;
}
