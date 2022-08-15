package blog.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;

@Data
public class BlogInfoItem {
    Integer id;
    String title;
    String description;
    String content;
    UserInfo user;
    @JsonFormat(timezone = "GMT+8")
    Instant createdAt;
    @JsonFormat(timezone = "GMT+8")
    Instant updatedAt;
}
