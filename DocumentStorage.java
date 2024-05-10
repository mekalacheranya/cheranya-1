package com.oosd.vstudent.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.File;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Table(name = "document_storage")
@Entity
public class DocumentStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    int id;


    @Column(name = "file")
    @Lob
    private byte[] file;

//    @OneToOne(mappedBy = "documentStorage")
//    private Document document;
//    uni directional mapping so excluding the above two lines

    public DocumentStorage(){}

    public DocumentStorage(byte[] file) {
        this.file = file;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "DocumentStorage{" +
                "id=" + id +
                ", file=" + file +
                '}';
    }
}
