package blog.controller;

import blog.entities.AjaxResponse;
import blog.service.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class FindUserController {
    private final MyUserDetailsService userService;

    public FindUserController(MyUserDetailsService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/auth/register")
    public AjaxResponse findUserById(@RequestBody @NotNull Map<String, String> requestBody) {
        String username = requestBody.get("username");
        String password = requestBody.get("password");
        return userService.registry(username, password);
    }

}
