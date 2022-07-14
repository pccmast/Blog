package blog.component;

import blog.entities.AjaxResponse;
import blog.mapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private static final ObjectMapper ObjectMapper = new ObjectMapper();

    final UserMapper userMapper;

    @Autowired
    public LoginSuccessHandler(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        response.setContentType("application/json");
        String username = request.getUserPrincipal().getName();
        response.getWriter().write(ObjectMapper.writeValueAsString(AjaxResponse.loginSuccess(userMapper.findUserByUserName(username))));
    }
}
