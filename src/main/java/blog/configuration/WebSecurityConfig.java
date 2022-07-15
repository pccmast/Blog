package blog.configuration;

import blog.component.CustomUsernamePasswordAuthenticationFilter;
import blog.component.LoginFailureHandler;
import blog.component.LoginSuccessHandler;
import blog.service.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;


@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Resource
    LoginSuccessHandler loginSuccessHandler;

    @Resource
    LoginFailureHandler loginFailureHandler;

    @Resource
    MyUserDetailsService myUserDetailsService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**", "/auth/**").permitAll()
                .and()
                .formLogin()
                .loginProcessingUrl("/auth/login")
//                .successHandler(loginSuccessHandler)
//                .failureHandler(loginFailureHandler)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .addFilter(customUsernamePasswordAuthenticationFilter());
        return http.build();
    }

    //    @Bean
//    public AuthenticationManager authenticationManager() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
//        return new ProviderManager(daoAuthenticationProvider);
//    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        return daoAuthenticationProvider;
    }
    // 当我们需要加载用户信息进行登录验证的时候，我们需要实现UserDetailsService接口，
    // 重写loadUserByUsername方法，参数是用户输入的用户名。返回值是UserDetails。
    // 密码编码器不指定的话默认使用PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    public CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter() {
        CustomUsernamePasswordAuthenticationFilter filter = new CustomUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(loginSuccessHandler);
        filter.setAuthenticationFailureHandler(loginFailureHandler);
        filter.setFilterProcessesUrl("/auth/login");
        filter.setAuthenticationManager(new ProviderManager(daoAuthenticationProvider()));
        return filter;
    }
}