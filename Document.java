package com.oosd.vstudent.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.File;

@Entity
@Table(name = "document")
public class Document {

    public enum DocumentType{
        DA, QP, RES; //digital assingments, question papers , resources
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private int id;

    @Enumerated
    @Column(name = "document_type",
        columnDefinition = "smallint")
    private DocumentType documentType;

    @Column(name = "document_name")
    private String documentName;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "file_id")
    private DocumentStorage documentStorage;

    @Column(name = "timestamp")
    private String timestamp;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "student_id")
    private Student author;

    //constructor

    public Document() { }

    public Document(DocumentType documentType, String documentName, DocumentStorage documentStorage, String timestamp, Student author) {
        this.documentType = documentType;
        this.documentName = documentName;
        this.documentStorage = documentStorage;
        this.timestamp = timestamp;
        this.author = author;
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Student getAuthor() {
        return author;
    }

    public void setAuthor(Student author) {
        this.author = author;
    }

    public DocumentStorage getDocumentStorage() {
        return documentStorage;
    }

    public void setDocumentStorage(DocumentStorage documentStorage) {
        this.documentStorage = documentStorage;
    }

    //to string


    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", documentType=" + documentType +
                ", documentName='" + documentName + '\'' +
                ", documentStorage=" + documentStorage +
                ", timestamp='" + timestamp + '\'' +
                ", author=" + author +
                '}';
    }
}
