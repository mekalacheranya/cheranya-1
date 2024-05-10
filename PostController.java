package com.oosd.vstudent.controllers;

import com.oosd.vstudent.errors.InvalidEndpointException;
import com.oosd.vstudent.errors.SuccessResponse;
import com.oosd.vstudent.models.Post;
import com.oosd.vstudent.models.Student;
import com.oosd.vstudent.models.Tag;
import com.oosd.vstudent.services.DatabaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "Post endpoints")
@RestController
@CrossOrigin
@RequestMapping("/post")
public class PostController {
    @Autowired
    private DatabaseService databaseService;

    @ApiOperation("return list of all blog posts")
    @GetMapping("/blog/")
    public List<Post> getAllBlogPosts()
    {
        return databaseService.getPostRepository().getPostsByType(Post.PostType.BLOG);
    }

    @ApiOperation("return list of all forum posts")
    @GetMapping("/forum/")
    public List<Post> getAllForumPosts()
    {
        return databaseService.getPostRepository().getPostsByType(Post.PostType.FORUM);
    }

    @ApiOperation("Return a post given its id")
    @GetMapping("/{id}")
    public Post retrievePost(@PathVariable int id)
    {
        if (!databaseService.getPostRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }
        return databaseService.getPostRepository().getById(id);
    }

    @ApiOperation("Add a new post")
    @PostMapping("/")
    public ResponseEntity<SuccessResponse> addPost(@RequestParam("author") String author,
                                                   @RequestParam("timestamp") String timestamp,
                                                   @RequestParam("tags") String tags,
                                                   @RequestParam("content") String content,
                                                   @RequestParam("title") String title,
                                                   @RequestParam("type") String type)
    {
        Student student = databaseService.getStudentRepository().findByUsername(author).get();
        ArrayList<Tag> tagList = new ArrayList<>();
        for (String s : tags.split(" "))
        {
            Tag t = null;
            if (!databaseService.getTagRepository().existsTagByTagName(s))
            {
                t = new Tag(s);
            }
            else
            {
                t = databaseService.getTagRepository().getTagByTagName(s);
            }
            if (!tagList.contains(t))
            {
                tagList.add(t);
            }
        }
        Post post = new Post(Post.PostType.valueOf(type), title, content, student, tagList, timestamp);
        databaseService.getPostRepository().save(post);

        SuccessResponse sr = new SuccessResponse();
        sr.setMessage("post added");
        sr.setStatus(HttpStatus.OK.value());

        return new ResponseEntity<SuccessResponse>(sr, HttpStatus.OK);
    }

    @ApiOperation("edit a post given its id")
    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse> editPost(@PathVariable int id, @RequestBody Post post)
    {
        if (!databaseService.getPostRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }

        Post p = databaseService.getPostRepository().getById(id);
        if (post.getTitle() != null) { p.setTitle(post.getTitle()); }
        if (post.getContent() != null) { p.setContent(post.getContent()); }
        if (post.getTags() != null) { p.setTags(post.getTags()); }

        databaseService.getPostRepository().save(p);
        SuccessResponse sr = new SuccessResponse();
        sr.setStatus(HttpStatus.OK.value());
        sr.setMessage("post edited");

        return new ResponseEntity<SuccessResponse>(sr, HttpStatus.OK);
    }

    @ApiOperation("delete a post given its id")
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deletePost(@PathVariable int id)
    {
        if (!databaseService.getPostRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }

        SuccessResponse sr = new SuccessResponse();
        sr.setStatus(HttpStatus.OK.value());
        sr.setMessage("post deleted");

        return new ResponseEntity<SuccessResponse>(sr, HttpStatus.OK);
    }
}