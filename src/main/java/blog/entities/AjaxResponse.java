package blog.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AjaxResponse {
    /**
     * 依据接口文档对返回结果的封装
     * <a href="https://xiedaimala.com/tasks/0e61bf37-d479-481b-a43e-8d7dd6069f93/text_tutorials/606cfb19-ca16-4fec-8564-75c1979871d6">文档地址</a>
     */
    private String status;      // 是否成功
    private String msg;         // 文字信息说明
    private Boolean isLogin;
    private Integer total;
    private Integer page;
    private Integer totalPage;

    private Object data;

    private AjaxResponse() {
    }    // 防止对类进行实例化

    private static AjaxResponse success(String msg, Object urlReturnData) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("ok");
        ajaxResponse.setMsg(msg);
        ajaxResponse.setData(urlReturnData);
        return ajaxResponse;
    }

    private static AjaxResponse success(String msg) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("ok");
        ajaxResponse.setMsg(msg);
        return ajaxResponse;
    }

    public static AjaxResponse failure(String msg) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("fail");
        ajaxResponse.setMsg(msg);
        return ajaxResponse;
    }

    public static AjaxResponse failure(String msg, Object exceptionMessage) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("fail");
        ajaxResponse.setMsg(msg);
        ajaxResponse.setData(exceptionMessage);
        return ajaxResponse;
    }

    public static AjaxResponse registrySuccess(User user) {
        return success("注册成功", user);
    }

    public static AjaxResponse loginSuccess(Object urlReturnData) {
        return success("登录成功", urlReturnData);
    }

    public static AjaxResponse haveLogin(Object urlReturnData) {
        AjaxResponse haveLogin = success("用户已经登录", urlReturnData);
        haveLogin.setIsLogin(true);
        return haveLogin;
    }

    public static AjaxResponse loginFailure(String msg) {
        return failure(msg);
    }

    public static AjaxResponse registryFailure(String errMessage) {
        return failure("用户已经存在", errMessage);
    }

    public static AjaxResponse haveNotLogin() {
        AjaxResponse ajaxResponse = success("用户没有登陆");
        ajaxResponse.setIsLogin(false);
        return ajaxResponse;
    }

    public static AjaxResponse logoutSuccess() {
        return success("注销成功");
    }

    public static AjaxResponse logoutFailure() {
        return failure("注销失败");
    }

    public static AjaxResponse getBlogListSuccess(Object urlReturnData) {
        AjaxResponse ajaxResponse = success("获取成功");
        ajaxResponse.setData(urlReturnData);
        return ajaxResponse;
    }

    public static AjaxResponse deleteBlogSuccess() {
        return success("修改成功");
    }
}
