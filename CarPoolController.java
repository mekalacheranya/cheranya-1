package com.oosd.vstudent.controllers;

import com.oosd.vstudent.errors.InvalidEndpointException;
import com.oosd.vstudent.errors.SuccessResponse;
import com.oosd.vstudent.models.CarPool;
import com.oosd.vstudent.models.Student;
import com.oosd.vstudent.services.DatabaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "carpool endpoints")
@RestController
@RequestMapping("/carpool")
@CrossOrigin
public class CarPoolController {

    @Autowired
    DatabaseService databaseService;

    //get all carpools

    @ApiOperation("return list of all carpools")
    @GetMapping("/")
    public List<CarPool> retrieveAllCarPools()
    {
        return databaseService.getCarPoolRepository().findAll();
    }

    @ApiOperation("add a new carpool")
    @PostMapping("/")
    public CarPool addCarPool(@RequestParam("source") String source,
                              @RequestParam("destination") String destination,
                              @RequestParam("date") String date,
                              @RequestParam("timestamp") String timestamp,
                              @RequestParam("host") String host)
    {
        Student s = databaseService.getStudentRepository().findByUsername(host).get();
        CarPool carPool = new CarPool(s, timestamp, source, destination, date);
        databaseService.getCarPoolRepository().save(carPool);
        return carPool;
    }

    @ApiOperation("edit an existing carpool")
    @PutMapping("/{id}")
    public CarPool editCarPool(@PathVariable int id, @RequestBody CarPool carPool)
    {
        if (!databaseService.getCarPoolRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }
        carPool.setId(id);
        databaseService.getCarPoolRepository().save(carPool);
        return carPool;
    }

    @ApiOperation("Delete a carpool given its id")
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteCarPool(@PathVariable int id)
    {
        if (!databaseService.getCarPoolRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }

        SuccessResponse sr = new SuccessResponse();
        sr.setStatus(HttpStatus.OK.value());
        sr.setMessage("car pool deleted");

        return new ResponseEntity<SuccessResponse>(sr, HttpStatus.OK);
    }
}
