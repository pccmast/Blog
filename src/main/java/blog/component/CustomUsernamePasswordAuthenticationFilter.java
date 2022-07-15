package blog.component;

import blog.entities.UsernamePassword;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        //use jackson to deserialize json
        ObjectMapper mapper = new ObjectMapper();
        UsernamePasswordAuthenticationToken authRequest;

        try (InputStream is = request.getInputStream()) {
            UsernamePassword authenticationBean = mapper.readValue(is, UsernamePassword.class);
            authRequest = new UsernamePasswordAuthenticationToken(authenticationBean.getUsername(),
                    authenticationBean.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
            authRequest = new UsernamePasswordAuthenticationToken("", "");
        }
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
