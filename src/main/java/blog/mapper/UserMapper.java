package blog.mapper;

import blog.entities.User;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository // 这个注解防止idea在其他地方对userMapper的注入红色下划线报错
public interface UserMapper {
    User findUserById(String id);

    User findUserByUserName(String name);

    Integer registerNewUser(User user);
}
