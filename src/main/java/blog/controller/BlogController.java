package blog.controller;

import blog.entities.AjaxResponse;
import blog.entities.Blog;
import blog.service.BlogService;
import blog.service.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class BlogController {
    @Autowired
    BlogService blogService;

    @Autowired
    MyUserDetailsService userService;

    @GetMapping("/blog")
    public AjaxResponse getBlogList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                    @RequestParam(required = false) String userId,
                                    @RequestParam(required = false) Boolean atIndex) {
        return blogService.getBlogList(page, userId, atIndex);
    }

    @GetMapping("/blog/{id}")
    public AjaxResponse getBlogByBlogId(@PathVariable("id") String id) {
        return blogService.getBlogByBlogId(id);
    }

    @PostMapping("/blog")
    public AjaxResponse createNewBlog(@RequestBody @NotNull Map<String, String> requestBody) {
        log.info("requestBody" + requestBody);
        Blog blog = getBlogFromParams(requestBody);
        return userService.checkLoginState()
                .map(user -> blogService.createNewBlog(blog, user))
                .orElse(AjaxResponse.failure("登陆后才能操作！"));
    }

    private Blog getBlogFromParams(Map<String, String> requestBody) {
        String title = requestBody.get("title");
        String content = requestBody.get("content");
        String description = requestBody.get("description");
        Boolean atIndex = Boolean.valueOf(requestBody.get("atIndex"));

        if (StringUtils.isBlank(description)) {
            description = content.substring(0, Math.min(content.length(), 10)) + "...";
        }

        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setDescription(description);
        blog.setAtIndex(atIndex);
        return blog;
    }

    @DeleteMapping("/blog/{id}")
    public AjaxResponse deleteBlog(@PathVariable("id") Integer id) {
        return userService.checkLoginState()
                .map(user -> blogService.deleteBlog(id))
                .orElse(AjaxResponse.failure("登陆后才能操作！"));
    }

    @PatchMapping("/blog/{id}")
    public AjaxResponse reviseBlog(@PathVariable("id") Integer id,
                                   @RequestBody Map<String, String> requestBody) {
        return blogService.reviseBlog(requestBody, id);
    }
}
