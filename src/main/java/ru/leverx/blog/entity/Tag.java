package ru.leverx.blog.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.leverx.blog.util.View;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Tag")
@Data
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.UI.class)
    private Integer id;

    @Column(name = "name", unique = true)
    @JsonView(View.UI.class)
    private String name;

    @ManyToMany(mappedBy = "tags")
    @JsonView(View.REST.class)
    private List<Article> articles = new ArrayList<>();

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }
}
