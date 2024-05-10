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

import java.util.List;

@Api(value = "tag endpoints")
@RestController
@CrossOrigin
@RequestMapping("/tag")
public class TagController {

    @Autowired
    DatabaseService databaseService;

    @ApiOperation("Returns list of all tags")
    @GetMapping("/")
    public List<Tag> retrieveTags()
    {
        return databaseService.getTagRepository().findAll();
    }

    @ApiOperation("Return a tag given its id")
    @GetMapping("/{id}")
    public Tag retrieveTag(@PathVariable int id)
    {
        if (!databaseService.getTagRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }
        return databaseService.getTagRepository().getById(id);
    }

    @ApiOperation("Return list of students that have this tag")
    @GetMapping("/{id}/students")
    public List<Student> retrieveStudentsByTag(@PathVariable int id)
    {
        if (!databaseService.getTagRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }
        return databaseService.getTagRepository().getById(id).getStudents();
    }

    @ApiOperation("Return list of Posts that have this tag")
    @GetMapping("/{id}/posts")
    public List<Post> retrievePostsByTag(@PathVariable int id)
    {
        if (!databaseService.getTagRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }
        return databaseService.getTagRepository().getById(id).getPosts();
    }

    @ApiOperation("Return a tag given its tag name. This endpoint is made to easily get an id of an existing tag")
    @GetMapping("/{tagName}")
    public Tag retrieveTagByName(@PathVariable String tagName)
    {
        return databaseService.getTagRepository().getTagByTagName(tagName);
    }

    @ApiOperation("Add a tag")
    @PostMapping("/")
    public Tag addTag(@RequestBody Tag tag)
    {
        databaseService.getTagRepository().save(tag);
        return tag;
    }

    @ApiOperation("Edit a tag")
    @PutMapping("/{id}")
    public Tag editTag(@PathVariable int id, @RequestBody Tag tag)
    {
        tag.setId(id);
        if (!databaseService.getTagRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }
        databaseService.getTagRepository().save(tag);
        return tag;
    }

    @ApiOperation("Delete a tag")
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteTag(@PathVariable int id)
    {
        databaseService.getTagRepository().deleteById(id);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setStatus(HttpStatus.OK.value());
        successResponse.setMessage("tag deleted");
        return new ResponseEntity<SuccessResponse>(successResponse, HttpStatus.OK);
    }
}
