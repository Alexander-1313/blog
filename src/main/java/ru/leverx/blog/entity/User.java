package ru.leverx.blog.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.leverx.blog.util.View;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

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
    @NotNull
    private String password;

    @Column(name = "email")
    @JsonView(View.UI.class)
    @NotNull
    @Email(message = "mail has invalid message!")
    private String email;

    @Column(name = "created_at")
    @JsonView(View.UI.class)
    private Date createdAt;

    @Column(name = "enabled")
    @JsonView(View.UI.class)
    private boolean enabled;

    @JsonView(View.REST.class)
    @OneToMany(mappedBy = "commentUser")
    private Set<Comment> comment;

    @JsonView(View.REST.class)
    @OneToMany(mappedBy = "user")
    private Set<Article> article;

    public User() {
    }

    public User(String email, String password) {
        this.password = password;
        this.email = email;   }

    public User(String firstName, String lastName, String password, String email, Date createdAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }

    public User(Integer id, String firstName, String lastName, String password, String email, Date createdAt, Set<Comment> comment, Set<Article> article) {
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
