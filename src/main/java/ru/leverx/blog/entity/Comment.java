package ru.leverx.blog.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.leverx.blog.util.View;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Comment")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.UI.class)
    private Integer id;

    @Column(name = "text")
    @JsonView(View.UI.class)
    private String text;

    @Column(name = "created_at")
    @JsonView(View.UI.class)
    private Date createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @JsonView(View.UI.class)
    private User commentUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    @JsonView(View.UI.class)
    private Article article;

    public Comment() {
    }

    public Comment(String text, Date createdAt, User commentUser, Article article) {
        this.text = text;
        this.createdAt = createdAt;
        this.commentUser = commentUser;
        this.article = article;
    }
}
