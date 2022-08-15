package blog;

import blog.entities.AjaxResponse;
import blog.mapper.UserMapper;
import blog.service.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
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

    @InjectMocks
    private MyUserDetailsService userService;

    @Mock
    private UserMapper userMapper;

    @Test
    public void registrySuccess() {
        AjaxResponse ajaxResponse = userService.registry("xiaowang", "11112222");
        Assertions.assertEquals(ajaxResponse.getStatus(), "ok");
        log.info("response: " + ajaxResponse);
    }

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
