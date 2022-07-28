package blog;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class LoginAndLogoutTest {
    @Resource
    private MockMvc mockMvc;

    @Test
    public void loginSuccessTest() throws Exception {
        String correctLoginJSON = "{\n" +
                "    \"username\":\"xiaowang\",\n" +
                "    \"password\":\"11112222\"\n" +
                "}";

        MvcResult result1 = mockMvc.perform(
                        MockMvcRequestBuilders.
                                request(HttpMethod.POST, "/auth/login")
                                .contentType("application/json")
                                .content(correctLoginJSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").
                        value("ok"))
                .andDo(print())
                .andReturn();

        result1.getResponse().setCharacterEncoding("UTF-8");
        log.info(result1.getResponse().getContentAsString());
    }

    @Test
    public void loginFailureTest() throws Exception {
        String wrongLoginJSON = "{\n" +
                "    \"username\":\"xiaowang\",\n" +
                "    \"password\":\"11113333\"\n" +
                "}";

        MvcResult result1 = mockMvc.perform(
                        MockMvcRequestBuilders.
                                request(HttpMethod.POST, "/auth/login")
                                .contentType("application/json")
                                .content(wrongLoginJSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").
                        value("fail"))
                .andDo(print())
                .andReturn();

        result1.getResponse().setCharacterEncoding("UTF-8");
        log.info(result1.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "xiaoliu", password = "123456")
    public void logoutSuccessTest() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.
                                request(HttpMethod.GET, "/auth")
                                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").
                        value("用户已经登录"))
                .andDo(print())
                .andReturn();

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.
                                request(HttpMethod.GET, "/auth/logout"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status")
                        .value("ok"))
                .andDo(print())
                .andReturn();

        result.getResponse().setCharacterEncoding("UTF-8");
        log.info(result.getResponse().getContentAsString());
    }

    @Test
    public void logoutFailureTest() throws Exception {
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.
                                request(HttpMethod.GET, "/auth/logout"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").
                        value("fail"))
                .andDo(print())
                .andReturn();

        result.getResponse().setCharacterEncoding("UTF-8");
        log.info(result.getResponse().getContentAsString());
    }
}
