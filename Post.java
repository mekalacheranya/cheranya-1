package com.oosd.vstudent.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
public class Post {

    public enum PostType {
        BLOG, FORUM;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int id;

    @Enumerated
    @Column(name = "post_type",
        columnDefinition = "smallint")
    private PostType type;

    @Column(name = "title")
    private String title;

    @Column(name = "content", length = 30000)
    private String content;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "student_id")
    private Student author;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @Column(name = "timestamp")
    private String timestamp;

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> likedBy;

    //constructors
    public Post() { }

    public Post(PostType type, String title, String content, Student author, List<Tag> tags, String timestamp) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.author = author;
        this.tags = tags;
        this.timestamp = timestamp;

        this.comments = new ArrayList<>();
        this.likedBy = new ArrayList<>();
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PostType getType() {
        return type;
    }

    public void setType(PostType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Student getAuthor() {
        return author;
    }

    public void setAuthor(Student author) {
        this.author = author;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Student> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(List<Student> likedBy) {
        this.likedBy = likedBy;
    }

    //toString


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author=" + author +
                ", tags=" + tags +
                ", timestamp='" + timestamp + '\'' +
                ", comments=" + comments +
                ", likedBy=" + likedBy +
                '}';
    }
}
