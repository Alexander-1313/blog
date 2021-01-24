package ru.leverx.blog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "User")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "created_at")
    private Date createdAt;

    @OneToOne(mappedBy = "commentUser")
    private Comment comment;

    @OneToOne(mappedBy = "user")
    private Article article;

    public User() {
    }

    public User(String firstName, String lastName, String password, String email, Date createdAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }

    public User(Integer id, String firstName, String lastName, String password, String email, Date createdAt, Comment comment, Article article) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
        this.comment = comment;
        this.article = article;
    }
}
