package blog.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.Instant;

@Data
public class User {
    Integer id;
    String name;
    @JsonIgnore
    String encryptedPassword;
    Instant createdAt;
    Instant updatedAt;
    String avatar;
}
