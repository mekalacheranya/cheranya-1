package com.oosd.vstudent.controllers;

import com.oosd.vstudent.errors.ErrorResponse;
import com.oosd.vstudent.errors.InvalidEndpointException;
import com.oosd.vstudent.errors.SuccessResponse;
import com.oosd.vstudent.models.Role;
import com.oosd.vstudent.models.Student;
import com.oosd.vstudent.services.DatabaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "role endpoints")
@RestController
@CrossOrigin
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private DatabaseService databaseService;

    @ApiOperation("get role information given an id")
    @GetMapping("/{id}")
    public Role retrieveRole(@PathVariable int id)
    {
        if (!databaseService.getRoleRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }
        return databaseService.getRoleRepository().getById(id);

    }

    @ApiOperation("get a list of all roles")
    @GetMapping("/")
    public List<Role> retrieveRoles()
    {
        return databaseService.getRoleRepository().findAll();
    }

    @PostMapping("/")
    public Role addRole(@RequestBody Role role)
    {
        databaseService.getRoleRepository().save(role);
        return role;
    }

    @ApiOperation("Returns a list of  students given a role id")
    @GetMapping("/{id}/students")
    public List<Student> retrieveStudentsByRole(@PathVariable int id)
    {
        if (!databaseService.getRoleRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }
        return databaseService.getRoleRepository().getById(id).getStudents();
    }

    @ApiOperation("edit a role given its id")
    @PutMapping("/{id}")
    public Role updateRole(@PathVariable("id") int id, @RequestBody Role role)
    {
        role.setId(id);
        if (!databaseService.getRoleRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }
        databaseService.getRoleRepository().save(role);
        return role;
    }

    @ApiOperation("Delete a role given its id")
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteRole(@PathVariable("id") int id)
    {
        databaseService.getRoleRepository().deleteById(id);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage("role deleted");
        successResponse.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<SuccessResponse>(successResponse, HttpStatus.OK);
    }
}
