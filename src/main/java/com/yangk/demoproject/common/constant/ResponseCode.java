package com.yangk.demoproject.common.constant;

/**
 * 操作代码枚举类
 *
 * @author yangk
 * @date 2020/3/11
 */
public enum ResponseCode {

    /**
     * 系统操作返回信息
     */
    OK("0", "操作成功"),
    ERROR("-1", "系统繁忙，此时请开发者稍候再试"),

    /**
     * 登录、用户、角色相关错误信息
     */
    NOT_LOGIN("100001", "登陆信息失效"),
    INVALID_ARGUMENT("100002", "无效的参数"),
    USER_NOT_FOUND("100003", "用户未找到"),
    USERNAME_OR_PASSWORD_INVALID("20001", "用户名或密码不正确"),
    USERNAME_OR_PASSWORD_TOW_ERROR("20009", "用户名或密码错误次数过多"),
    PASSWORD_ERROR("100004", "密码不正确"),
    USER_ACCOUNT_IS_BAN("100004", "账号被禁用"),
    USER_NAME_IS_HAVE("100005", "用户名被占用"),
    ROLE_NAME_IS_HAVE("100006", "角色名称已被占用"),

    /**
     * 上传相关错误信息
     **/
    FILES_NOT_FOUND("200001", "附件未找到"),
    FILES_PATH_NOT_FOUND("200002", "上传目录不可为空"),

    /**
     * 工作流相关错误信息
     **/
    FLOW_MODEL_KEY_IS_EXIST("300001", "工作流模型KEY已存在"),
    FLOW_MODEL_NOT_FOUND("300001", "工作流模型未找到"),
    FLOW_MODEL_DESIGN_IS_NULL("300001", "请先设计流程,再进行发布"),
    FLOW_MODEL_IS_ERROR("300002", "数据模型不符要求，请至少设计一条主线流程"),
    FLOW_IS_NOT_FOUND("300001", "工作流未定义"),
    FLOW_START_ERROR("300001", "流程启动失败"),
    FLOW_EXAMPLE_IS_NOT_FOUND("300003", "流程实例未找到"),

    /**
     * 报表模板信息
     */
    REPORT_TEMPLATE_NOT_EXIST("500001", "报表模板不存在"),

    /**
     * 业务订单相关错误信息
     */
    ORDER_NO_NOT_FOUND("400001", "未传入订单编号");

    private String code;
    private String desc;

    ResponseCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
