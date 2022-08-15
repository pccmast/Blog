package blog.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;

@Data
public class BlogItem {
    Integer id;
    String title;
    String description;
    UserInfo userInfo;
    @JsonFormat(timezone = "GMT+8")
    Instant createdAt;
    @JsonFormat(timezone = "GMT+8")
    Instant updatedAt;
}
