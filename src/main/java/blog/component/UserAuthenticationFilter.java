package blog.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    ThreadLocal<Map<String, String>> threadLocal = new ThreadLocal<>();

    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        log.info("authenticationManager: " + this.getAuthenticationManager());
    }

    @Nullable
    public String obtainPassword(HttpServletRequest request) {
        String password = getRequestBodyParams(request).get("password");
        if (StringUtils.hasText(password)) {
            log.info("password: " + password);
            return password;
        } else {
            return super.obtainPassword(request);
        }
    }

    @Nullable
    protected String obtainUsername(HttpServletRequest request) {
        String username = getRequestBodyParams(request).get("username");
        if (StringUtils.hasText(username)) {
            return username;
        } else {
            return super.obtainUsername(request);
        }
    }

    private Map<String, String> getRequestBodyParams(HttpServletRequest request) {

        Map<String, String> bodyParam = threadLocal.get();

        if (bodyParam == null) {
            //use jackson to deserialize json
            ObjectMapper mapper = new ObjectMapper();
            try (InputStream is = request.getInputStream()) {
                bodyParam = mapper.readValue(is, Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bodyParam == null) {
                bodyParam = new HashMap<>();
            }
            threadLocal.set(bodyParam);
        }
        return bodyParam;
    }
}
