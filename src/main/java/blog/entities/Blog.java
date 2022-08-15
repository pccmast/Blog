package blog.entities;

import lombok.Data;

@Data
public class Blog {
    Integer id;
    String title;
    String description;
    String content;
    Integer userId;
    Boolean atIndex;
}
