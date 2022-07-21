package blog.controller;

import blog.entities.AjaxResponse;
import blog.entities.User;
import blog.service.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
public class AuthController {
    private final MyUserDetailsService userService;

    public AuthController(MyUserDetailsService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/auth/register")
    public AjaxResponse registry(@RequestBody @NotNull Map<String, String> requestBody) {
        String username = requestBody.get("username");
        String password = requestBody.get("password");
        return userService.registry(username, password);
    }

    @GetMapping(value = "/auth")
    public AjaxResponse checkLogin() {
        Optional<User> user = userService.checkLoginState();
        if (user.isPresent()) {
            return AjaxResponse.haveLogin(user);
        } else {
            return AjaxResponse.haveNotLogin();
        }
    }

}
