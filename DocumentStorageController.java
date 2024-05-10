package com.oosd.vstudent.controllers;

import com.oosd.vstudent.errors.InvalidEndpointException;
import com.oosd.vstudent.errors.SuccessResponse;
import com.oosd.vstudent.models.DocumentStorage;
import com.oosd.vstudent.services.DatabaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(value = "Storage endpoints")
@RestController
@RequestMapping("/file")
@CrossOrigin
public class DocumentStorageController {

    @Autowired
    private DatabaseService databaseService;

    @ApiOperation("return a document info given its id")
    @GetMapping("/{id}")
    public DocumentStorage retrieveFile(@PathVariable int id)
    {
        if (!databaseService.getDocumentStorageRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }
        return databaseService.getDocumentStorageRepository().getById(id);
    }

    @ApiOperation("add a new document info")
    @PostMapping("/")
    public int addFile(@RequestParam MultipartFile file) throws IOException {
        DocumentStorage documentStorage = new DocumentStorage();
        documentStorage.setFile(file.getBytes());
        databaseService.getDocumentStorageRepository().save(documentStorage);
        return documentStorage.getId();
    }

    @ApiOperation("Edit an existing document given its id")
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateFile(@PathVariable int id, @RequestBody DocumentStorage documentStorage)
    {
        if (!databaseService.getDocumentStorageRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }
        documentStorage.setId(id);
        databaseService.getDocumentStorageRepository().save(documentStorage);

        SuccessResponse sr = new SuccessResponse();
        sr.setStatus(HttpStatus.OK.value());
        sr.setMessage("file replaced");

        return new ResponseEntity<SuccessResponse>(sr, HttpStatus.OK);
    }

    @ApiOperation("delete a document given its id")
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteFile(@PathVariable int id)
    {
        if (!databaseService.getDocumentStorageRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }
        databaseService.getDocumentStorageRepository().deleteById(id);
        SuccessResponse sr = new SuccessResponse();
        sr.setMessage("file deleted");
        sr.setStatus(HttpStatus.OK.value());

        return new ResponseEntity<SuccessResponse>(sr, HttpStatus.OK);
    }
}
