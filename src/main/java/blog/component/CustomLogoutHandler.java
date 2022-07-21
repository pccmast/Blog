package blog.component;

import blog.entities.AjaxResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        try {
            if (authentication == null) {
                response.getWriter().write(objectMapper.writeValueAsString(AjaxResponse.logoutFailure()));
            } else {
                log.info("authentication:" + authentication.getPrincipal());
                response.getWriter().write(objectMapper.writeValueAsString(AjaxResponse.logoutSuccess()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
