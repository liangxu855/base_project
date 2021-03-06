package com.lujiaozhuang.client.widget.empty_layout;

/**
 * Created by Administrator on 2016/4/12.
 */
public class ContextData {
    //标识
    private int flag;
    // 失败 时候的错误类型
    private String errCode;
    //显示的标题
    private String title;
    //显示图片的Id 大于0就会显示， 其他的显示默认
    private int resId = 0;

    //按钮文字
    private String buttonText;

    public ContextData() {
    }

    public ContextData(String title) {
        this.title = title;
    }

    public ContextData(String title, int resId) {
        this.title = title;
        this.resId = resId;
    }

    public ContextData(String errCode, String title) {
        this.errCode = errCode;
        this.title = title;
    }

    public ContextData(String title, int resId, String buttonText) {
        this(title, resId, buttonText, -1);
    }

    public ContextData(String title, int resId, String buttonText, int flag) {
        this.flag = flag;
        this.title = title;
        this.resId = resId;
        this.buttonText = buttonText;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = String.valueOf(errCode);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }
}
