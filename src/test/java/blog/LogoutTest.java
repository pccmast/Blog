package blog;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.Resource;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class LogoutTest {
    @Resource
    private MockMvc mockMvc;

    @Test
    public void logoutFailureTest() throws Exception {
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.
                                request(HttpMethod.GET, "/auth/logout")).
                andReturn();
        result.getResponse().setCharacterEncoding("UTF-8");
        log.info(result.getResponse().getContentAsString());
    }

    @Test
    public void logoutSuccessTest() throws Exception {
        String loginJSON = "{\n" +
                "    \"username\":\"xiaowang\",\n" +
                "    \"password\":\"11112222\"\n" +
                "}";

        MvcResult result1 = mockMvc.perform(
                        MockMvcRequestBuilders.
                                request(HttpMethod.POST, "/auth/login")
                                .contentType("application/json")
                                .content(loginJSON))
                .andReturn();
        result1.getResponse().setCharacterEncoding("UTF-8");
        log.info(result1.getResponse().getContentAsString());

        MvcResult result2 = mockMvc.perform(
                        MockMvcRequestBuilders.
                                request(HttpMethod.GET, "/auth")).
                andReturn();
        result2.getResponse().setCharacterEncoding("UTF-8");
        log.info(result2.getResponse().getContentAsString());

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.
                                request(HttpMethod.GET, "/auth/logout")).
                andReturn();
        result.getResponse().setCharacterEncoding("UTF-8");
        log.info(result.getResponse().getContentAsString());
    }
}
