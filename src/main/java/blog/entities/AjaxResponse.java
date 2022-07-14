package blog.entities;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class AjaxResponse {
    /**
     * 依据接口文档对返回结果的封装
     * <a href="https://xiedaimala.com/tasks/0e61bf37-d479-481b-a43e-8d7dd6069f93/text_tutorials/606cfb19-ca16-4fec-8564-75c1979871d6">文档地址</a>
     */
    private String status;      // 是否成功
    private String msg;         // 文字信息说明
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

    public static AjaxResponse registrySuccess(User user) {
        return AjaxResponse.success("注册成功", user);
    }

    public static AjaxResponse loginSuccess(Object urlReturnData) {
        return AjaxResponse.success("登录成功", urlReturnData);
    }

    public static AjaxResponse failure(String msg) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("fail");
        ajaxResponse.setMsg(msg);
        return ajaxResponse;
    }

    public static AjaxResponse loginFailure() {
        return AjaxResponse.failure("用户不存在/密码不正确");
    }

    public static AjaxResponse registryFailure() {
        return AjaxResponse.failure("用户已经存在");
    }

}
