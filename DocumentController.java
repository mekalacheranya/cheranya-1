package com.oosd.vstudent.controllers;

import com.oosd.vstudent.errors.InvalidEndpointException;
import com.oosd.vstudent.errors.SuccessResponse;
import com.oosd.vstudent.models.Document;
import com.oosd.vstudent.models.DocumentStorage;
import com.oosd.vstudent.models.Student;
import com.oosd.vstudent.models.Tag;
import com.oosd.vstudent.services.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/document")
@CrossOrigin
public class DocumentController {

    @Autowired
    DatabaseService databaseService;

    //get a document
    @GetMapping("/{id}")
    public Document retrieveDocumentById(@RequestParam int id)
    {
        if (!databaseService.getDocumentRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }
        return databaseService.getDocumentRepository().getById(id);
    }

    //get all similar matching names
    @GetMapping("/{name}")
    public List<Document> retrieveDocumentsBySimilarName(@RequestParam String name)
    {
        List<Document> docs = databaseService.getDocumentRepository().getDocumentsByDocumentNameIsLike("%" + name);
        return docs;
    }

    //get all docs
    @GetMapping("/")
    public List<Document> retrieveAllDocuments()
    {
        return databaseService.getDocumentRepository().findAll();
    }

    @GetMapping("/{id}/storage")
    public byte[] retrieveStorageByDocument(@PathVariable int id)
    {
        return databaseService.getDocumentRepository().getById(id).getDocumentStorage().getFile();
    }

    //add a doc
    @PostMapping("/")
    public Document addDocument(@RequestParam("author") String author,
                                @RequestParam("documentName") String name,
                                @RequestParam("documentType") String type,
                                @RequestParam("documentId") String docId,
                                @RequestParam("timestamp") String timestamp)
    {
        Student student = databaseService.getStudentRepository().findByUsername(author).get();
        Document.DocumentType documentType = Document.DocumentType.valueOf(type);
        DocumentStorage documentStorage = databaseService.getDocumentStorageRepository().getById(Integer.valueOf(docId));
        Document document = new Document(documentType, name, documentStorage, timestamp, student);
        return databaseService.getDocumentRepository().save(document);
    }

    //edit a doc
    @PutMapping("/{id}")
    public Document editDocument(@PathVariable int id, @RequestBody Document document)
    {
        if (!databaseService.getDocumentRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }
        document.setId(id);
        databaseService.getDocumentRepository().save(document);
        return document;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteDocument(@PathVariable int id)
    {
        if (!databaseService.getDocumentRepository().existsById(id))
        {
            throw new InvalidEndpointException("id not found");
        }
        databaseService.getDocumentRepository().deleteById(id);
        SuccessResponse sr = new SuccessResponse();
        sr.setMessage("document deleted");
        sr.setStatus(HttpStatus.OK.value());

        return new ResponseEntity<SuccessResponse>(sr, HttpStatus.OK);
    }
}
