package ru.leverx.blog.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.leverx.blog.util.View;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Article")
@Data
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.UI.class)
    private Integer id;

    @Column(name = "title")
    @JsonView(View.UI.class)
    private String title;

    @Column(name = "text")
    @JsonView(View.UI.class)
    private String text;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @JsonView(View.UI.class)
    private Status status;

    @Column(name = "created_at")
    @JsonView(View.UI.class)
    private Date createdAt;

    @Column(name = "updated_at")
    @JsonView(View.UI.class)
    private Date updatedAt;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "article_tag",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @JsonView(View.REST.class)
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    @JsonView(View.REST.class)
    private Set<Comment> comment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @JsonView(View.REST.class)
    private User user;

    public Article() {
    }

    public Article(String title, String text, Status status, Date createdAt, Date updatedAt) {
        this.title = title;
        this.text = text;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Article(String title, String text, Status status, Date createdAt, Date updatedAt, User user, List<Tag> tags) {
        this.title = title;
        this.text = text;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.tags = tags;
    }

    public Article(String title, String text, Status status, Date createdAt, Date updatedAt, User user) {
        this.title = title;
        this.text = text;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
    }
}
