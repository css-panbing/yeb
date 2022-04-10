package com.cssnj.server.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 百度富文本返回类
 * @author panbing
 * @date 2022/4/9 20:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespUEditorData {

    private static final long serialVersionUID = 1L;

    private String state;

    private String url;

    private String title;

    private String type;

    private long size;

    private String original;

    /**
     * 返回成功结果
     * @param url
     * @param title
     * @param type
     * @param size
     * @param original
     * @return
     */
    public static RespUEditorData success(String url, String title, String type, long size, String original){
        return new RespUEditorData("SUCCESS", url, title, type, size, original);
    }

    /**
     * 返回失败结果
     * @param url
     * @param title
     * @param type
     * @param size
     * @param original
     * @return
     */
    public static RespUEditorData error(String url, String title, String type, long size, String original){
        return new RespUEditorData("ERROR", url, title, type, size, original);
    }

    @Override
    public String toString() {
        return "{" +
                "state='" + state + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", original='" + original + '\'' +
                '}';
    }
}
