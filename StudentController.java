package com.oosd.vstudent.controllers;

import com.oosd.vstudent.errors.InvalidEndpointException;
import com.oosd.vstudent.errors.SuccessResponse;
import com.oosd.vstudent.models.*;
import com.oosd.vstudent.security.CustomUserDetails;
import com.oosd.vstudent.services.DatabaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Api(value = "Student endpoints")
@RestController
@CrossOrigin
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DatabaseService databaseService;

    @ApiOperation(value = "Return List of all students")
    @GetMapping("/")
    public List<Student> retreiveAllStudents()
    {
        return databaseService.getStudentRepository().findAll();
    }

    @ApiOperation(value = "Return user given his username")
    @GetMapping("/user/{username}")
    @CrossOrigin
    public Student retrieveStudentFromUsername(@PathVariable("username") String username) {
        Optional<Student> student = databaseService.getStudentRepository().findByUsername(username);
        student.orElseThrow(() -> new UsernameNotFoundException("Not found user : " + username));
        return student.get();
    }

    @ApiOperation(value = "Return a particular student with the given id")
    @GetMapping("/{id}")
    public Student retrieveStudent(@PathVariable int id)
    {
        if (!databaseService.getStudentRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }
        return databaseService.getStudentRepository().getById(id);
    }

    @ApiOperation("Return list of posts made by the user")
    @GetMapping("/{id}/posts")
    public List<Post> retrieveUsersPosts(@PathVariable int id)
    {
        return databaseService.getStudentRepository().getById(id).getPosts();
    }

    @ApiOperation("Return list of liked posts made by the user")
    @GetMapping("/{id}/likedPosts")
    public List<Post> retrieveUsersLikedPosts(@PathVariable int id)
    {
        return databaseService.getStudentRepository().getById(id).getLikedPosts();
    }

    @ApiOperation("Return list of liked comments made by the user")
    @GetMapping("/{id}/likedComments")
    public List<Comment> retrieveLikedComments(@PathVariable int id)
    {
        return databaseService.getStudentRepository().getById(id).getLikedComments();
    }

    @ApiOperation("Return list of documents of the student")
    @GetMapping("/{id}/documents")
    public List<Document> retrieveDocumentsByUser(@PathVariable int id)
    {
        return databaseService.getStudentRepository().getById(id).getDocuments();
    }

    @ApiOperation("Return list of carpools by the user")
    @GetMapping("/{id}/carpools")
    public List<CarPool> retrieveCarpoolsByUser(@PathVariable int id)
    {
        return databaseService.getStudentRepository().getById(id).getCarPools();
    }

    @ApiOperation("Adds a new student to the database")
    @PostMapping("/")
    public Student addStudent(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("email") String email,
                              @RequestParam("role") String role,
                              @RequestParam("tags") String tags,
                              @RequestParam("pfp") MultipartFile file) throws IOException {
        System.out.println("works so far");

        Role r = databaseService.getRoleRepository().findRoleByRole(role);
        Student student = new Student(username, password, email, null, new ArrayList<Tag>());
        if (r != null)
        {
            student.setRole(r);
        }
        else
        {
            r = new Role(role);
            student.setRole(r);
        }
        student.setPfp(file.getBytes());
        student.setPassword(passwordEncoder.encode(student.getPassword()));

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
            if (!student.getTags().contains(t))
            {
                student.getTags().add(t);
            }
        }

        return databaseService.getStudentRepository().save(student);
    }

    @ApiOperation("Edit an existing student with the given id")
    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse> editStudent(@PathVariable int id, @RequestBody Student student)
    {
        if (!databaseService.getStudentRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }
        Student s = databaseService.getStudentRepository().getById(id);
        if (student.getEmail() != null) { s.setEmail(student.getEmail()); }
        if (student.getUsername() != null) { s.setUsername(student.getUsername()); }
        if (student.getPassword() != null) { s.setPassword(student.getPassword()); }

        databaseService.getStudentRepository().save(s);

        SuccessResponse sr = new SuccessResponse();
        sr.setMessage("fields edited");
        sr.setStatus(HttpStatus.OK.value());

        return new ResponseEntity<SuccessResponse>(sr, HttpStatus.OK);
    }

    @ApiOperation("delete a student entry with given id")
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteStudent(@PathVariable int id)
    {
        if (!databaseService.getStudentRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }

        SuccessResponse sr = new SuccessResponse();
        sr.setMessage("student deleted");
        sr.setStatus(HttpStatus.OK.value());

        return new ResponseEntity<SuccessResponse>(sr, HttpStatus.OK);
    }
}
