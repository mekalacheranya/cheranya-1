package com.oosd.vstudent.repositories;

import com.oosd.vstudent.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface DocumentRepository extends JpaRepository<Document, Integer> {
    public List<Document> getDocumentsByDocumentNameIsLike(@Param("name") String name);
}
