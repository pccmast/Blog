package blog.component;

import blog.entities.AjaxResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private static final com.fasterxml.jackson.databind.ObjectMapper ObjectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        if (exception instanceof BadCredentialsException) {
            response.getWriter().write(ObjectMapper.writeValueAsString(AjaxResponse.loginFailure("用户或者密码不正确")));
        } else {
            response.getWriter().write(ObjectMapper.writeValueAsString(AjaxResponse.loginFailure("其他类型错误，请咨询管理员")));
        }
    }
}
