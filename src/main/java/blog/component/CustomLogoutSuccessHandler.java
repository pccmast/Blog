package blog.component;

import blog.entities.AjaxResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        if (authentication == null) {
            response.getWriter().write(objectMapper.writeValueAsString(AjaxResponse.logoutFailure()));
        } else {
            log.info("authentication:" + authentication.getPrincipal());
            response.getWriter().write(objectMapper.writeValueAsString(AjaxResponse.logoutSuccess()));
        }
    }
}
