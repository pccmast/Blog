package blog;

import blog.service.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.Resource;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class LoginFailureTest {
    @Resource
    private MockMvc mockMvc;

    @Resource
    private MyUserDetailsService myUserDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Test
    public void login() throws Exception {
        String loginJSON = "{\n" +
                "    \"username\":\"xiaowang\",\n" +
                "    \"password\":\"11112222\"\n" +
                "}";

        MvcResult result = mockHttpRequest(loginJSON);
        log.info(result.getResponse().getContentAsString());
    }

    private void testUserService() throws Exception {
        String password = myUserDetailsService.getUserByUserName("xiaowang").getEncryptedPassword();
        log.info("encryptedPassword:" + password);
        boolean isMatch = bCryptPasswordEncoder.matches("11112222", password);
        log.info("isMatch:" + isMatch);
    }

    @NotNull
    private MvcResult mockHttpRequest(String loginJSON) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                request(HttpMethod.POST, "/auth/login")
                .contentType("application/json")
                .content(loginJSON)).andReturn();
        result.getResponse().setCharacterEncoding("UTF-8");
        return result;
    }
}
