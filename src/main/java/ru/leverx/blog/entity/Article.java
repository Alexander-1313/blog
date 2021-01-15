package ru.leverx.blog.entity;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.Objects;

public class Article {

    private Integer id;
    private String title;
    private Text text;
    private Status status;
    private Integer authorId;
    private Date createdAt;
    private Date updatedAt;

    public Article() {
    }

    public Article(Integer id, String title, Text text, Status status, Integer authorId, Date createdAt, Date updatedAt) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.status = status;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id) && Objects.equals(title, article.title) && Objects.equals(text, article.text) && status == article.status && Objects.equals(authorId, article.authorId) && Objects.equals(createdAt, article.createdAt) && Objects.equals(updatedAt, article.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, text, status, authorId, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Article{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", text=").append(text);
        sb.append(", status=").append(status);
        sb.append(", authorId=").append(authorId);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append('}');
        return sb.toString();
    }
}
