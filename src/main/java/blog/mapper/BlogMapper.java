package blog.mapper;

import blog.entities.Blog;
import blog.entities.BlogInfoItem;
import blog.entities.BlogItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogMapper {
    List<BlogItem> getBlogList(Integer page, String userId, Boolean atIndex);

    BlogInfoItem getBlogInfoByBlogId(Integer id);

    Integer createNewBlog(Blog blog);

    void reviseBlog(Blog blog);

    void deleteBlog(Integer id);

    Integer countBlog(String userId, Boolean atIndex);
}
