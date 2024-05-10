package com.oosd.vstudent.repositories;

import com.oosd.vstudent.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    public Tag getTagByTagName(@Param("tagName") String tagName);
    public boolean existsTagByTagName(@Param("tagName") String tagName);
}
