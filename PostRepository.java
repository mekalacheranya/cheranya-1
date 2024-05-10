package com.oosd.vstudent.repositories;

import com.oosd.vstudent.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Integer> {
    public List<Post> getPostsByType(@Param("type")Post.PostType postType);
}
