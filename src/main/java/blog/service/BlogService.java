package blog.service;

import blog.entities.AjaxResponse;
import blog.entities.Blog;
import blog.entities.BlogInfoItem;
import blog.entities.BlogItem;
import blog.entities.User;
import blog.mapper.BlogMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class BlogService {
    @Resource
    BlogMapper blogMapper;

    Integer blogEachPage = 10;

    @JsonProperty("data")
    List<BlogItem> blogItemList;

    public AjaxResponse getBlogList(Integer page, String userId, Boolean atIndex) {
        try {
            Integer pageStartIndex = (page - 1) * blogEachPage;
            blogItemList = blogMapper.getBlogList(pageStartIndex, userId, atIndex);

            Integer blogNum = blogMapper.countBlog();

            AjaxResponse ajaxResponse = AjaxResponse.getBlogListSuccess(blogItemList);

            Integer totalPages = (blogNum % 10 == 0) ? (blogNum / 10) : (blogNum / 10 + 1);

            ajaxResponse.setTotalPage(totalPages);
            ajaxResponse.setPage(page);
            ajaxResponse.setTotal(blogNum);

            return ajaxResponse;
        } catch (Exception e) {
            return AjaxResponse.failure("系统异常", e.getMessage());
        }
    }

    public AjaxResponse getBlogByBlogId(String blogId) {
        try {
            Integer idNum = Integer.parseInt(blogId);
            BlogInfoItem blogInfo = blogMapper.getBlogInfoByBlogId(idNum);
            return AjaxResponse.getBlogListSuccess(blogInfo);
        } catch (Exception e) {
            return AjaxResponse.failure("系统异常", e.getMessage());
        }
    }

    public AjaxResponse createNewBlog(Blog blog, User user) {
        try {
            blog.setUserId(user.getId());
            blog.setAtIndex(true);
            blogMapper.createNewBlog(blog);
            log.info("blogId: " + blog.getId());
            return AjaxResponse.getBlogListSuccess(blog);
        } catch (Exception e) {
            return AjaxResponse.failure("系统异常", e.getMessage());
        }
    }

    public AjaxResponse deleteBlog(Integer blogId) {
        try {
            BlogInfoItem blogInfo = blogMapper.getBlogInfoByBlogId(blogId);

            if (blogInfo == null) {
                return AjaxResponse.failure("博客不存在");
            }
            if (!Objects.equals(blogId, blogInfo.getId())) {
                return AjaxResponse.failure("无法修改别人的博客");
            }

            blogMapper.deleteBlog(blogId);
            return AjaxResponse.deleteBlogSuccess();
        } catch (Exception e) {
            return AjaxResponse.failure("系统异常", e.getMessage());
        }
    }

    public AjaxResponse reviseBlog(Map<String, String> requestBody, Integer blogId) {
        try {
            BlogInfoItem blogInfo = blogMapper.getBlogInfoByBlogId(blogId);

            if (blogInfo == null) {
                return AjaxResponse.failure("博客不存在");
            }
            if (!Objects.equals(blogId, blogInfo.getId())) {
                return AjaxResponse.failure("无法修改别人的博客");
            }

            Blog blog = new Blog();
            blog.setId(blogId);
            blog.setTitle(requestBody.get("title"));
            blog.setContent(requestBody.get("content"));
            blog.setDescription(requestBody.get("description"));
            blog.setAtIndex(Boolean.getBoolean(requestBody.get("atIndex")));
            blogMapper.reviseBlog(blog);
            blogInfo = blogMapper.getBlogInfoByBlogId(blogId);
            return AjaxResponse.getBlogListSuccess(blogInfo);
        } catch (Exception e) {
            return AjaxResponse.failure("系统异常", e.getMessage());
        }
    }
}
