package com.oosd.vstudent.controllers;

import com.oosd.vstudent.errors.InvalidEndpointException;
import com.oosd.vstudent.errors.SuccessResponse;
import com.oosd.vstudent.models.Comment;
import com.oosd.vstudent.models.Post;
import com.oosd.vstudent.models.Student;
import com.oosd.vstudent.services.DatabaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "Comment endpoints")
@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private DatabaseService databaseService;

    @ApiOperation("Return list of comments of the post")
    @GetMapping("/{postId}")
    public List<Comment> retrieveCommentsByPost(@PathVariable("postId") int postId)
    {
        Post post = databaseService.getPostRepository().getById(postId);
        return post.getComments();
    }

    @ApiOperation("Return list of students who liked a post")
    @GetMapping("/{id}/students")
    public List<Student> retrieveStudentsByLiked(@PathVariable int id)
    {
        if (!databaseService.getCommentRepository().existsById(id)) {
            throw new InvalidEndpointException("id not found");
        }
        return databaseService.getCommentRepository().getById(id).getLikedBy();
    }

    @ApiOperation("add a new comment")
    @PostMapping("/{postId}")
    public Comment addComment(@RequestParam("author") String author,
                              @RequestParam("timestamp") String timestamp,
                              @RequestParam("content") String content,
                              @PathVariable("postId") int postId)
    {
        Student student = databaseService.getStudentRepository().findByUsername(author).get();
        Post post = databaseService.getPostRepository().getById(postId);
        Comment comment = new Comment(content, post, student, timestamp);
        comment.setLikedBy(new ArrayList<>());
        databaseService.getCommentRepository().save(comment);
        return comment;
    }


    @ApiOperation("edit a comment given its id")
    @PutMapping("/{id}")
    public Comment editComment(@PathVariable("id") int id, @RequestBody Comment comment)
    {
        if (!databaseService.getCommentRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }
        comment.setId(id);
        databaseService.getCommentRepository().save(comment);
        return comment;
    }

    @ApiOperation("Delete a comment given its id")
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteComment(@PathVariable int id)
    {
        if (!databaseService.getCommentRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }
        databaseService.getCommentRepository().deleteById(id);

        SuccessResponse sr = new SuccessResponse();
        sr.setStatus(HttpStatus.OK.value());
        sr.setMessage("comment deleted");

        return new ResponseEntity<SuccessResponse>(sr, HttpStatus.OK);
    }
}
