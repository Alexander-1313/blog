package ru.leverx.blog.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.leverx.blog.util.View;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "User")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.UI.class)
    private Integer id;
    @Column(name = "first_name")
    @JsonView(View.UI.class)
    private String firstName;
    @Column(name = "last_name")
    @JsonView(View.UI.class)
    private String lastName;
    @Column(name = "password")
    @JsonView(View.UI.class)
    private String password;
    @Column(name = "email")
    @JsonView(View.UI.class)
    private String email;
    @Column(name = "created_at")
    @JsonView(View.UI.class)
    private Date createdAt;

    @JsonView(View.REST.class)
    @OneToOne(mappedBy = "commentUser")
    private Comment comment;

    @JsonView(View.REST.class)
    @OneToOne(mappedBy = "user")
    private Article article;

    public User() {
    }

    public User(String email, String password) {
        this.password = password;
        this.email = email;
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
