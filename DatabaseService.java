package com.oosd.vstudent.services;

import com.oosd.vstudent.models.Comment;
import com.oosd.vstudent.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CarPoolRepository carPoolRepository;

    @Autowired
    private DocumentStorageRepository documentStorageRepository;

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

    public TagRepository getTagRepository() {
        return tagRepository;
    }

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    public PostRepository getPostRepository() {
        return postRepository;
    }

    public DocumentRepository getDocumentRepository() {
        return documentRepository;
    }

    public CommentRepository getCommentRepository() {
        return commentRepository;
    }

    public CarPoolRepository getCarPoolRepository() {
        return carPoolRepository;
    }

    public DocumentStorageRepository getDocumentStorageRepository() {
        return documentStorageRepository;
    }
}
