package blog.service;

import blog.entities.AjaxResponse;
import blog.entities.User;
import blog.mapper.UserMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Collections;
import java.util.Optional;


@Service
@Slf4j
@Data
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private UserMapper userMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public MyUserDetailsService() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public User getUserByUserName(String username) {
        return userMapper.findUserByUserName(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException(username + " 不存在！");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getEncryptedPassword(), Collections.emptyList());
    }

    public AjaxResponse registry(String username, String password) {

        if (username == null || password == null) {
            return AjaxResponse.failure("username/password == null");
        }
        if (username.length() < 1 || username.length() > 15) {
            return AjaxResponse.failure("invalid username");
        }
        if (password.length() < 1 || password.length() > 15) {
            return AjaxResponse.failure("invalid password");
        }

        User userNeedToRegister = UserBuilder(username, password);

        try {
            Integer id = userMapper.registerNewUser(userNeedToRegister);
            userNeedToRegister.setId(id);
            return AjaxResponse.registrySuccess(userNeedToRegister);
        } catch (DuplicateKeyException e) {
            return AjaxResponse.registryFailure();
        }
        // 登录

    }

    private User UserBuilder(String username, String password) {
        User userNeedToRegister = new User();
        userNeedToRegister.setUsername(username);
        userNeedToRegister.setEncryptedPassword(bCryptPasswordEncoder.encode(password));
        userNeedToRegister.setCreatedAt(Instant.now());
        userNeedToRegister.setUpdatedAt(Instant.now());
        return userNeedToRegister;
    }

    public Optional<User> checkLoginState() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(getUserByUserName(authentication == null ? null : authentication.getName()));

    }
}
