package ru.leverx.blog.entity;

import org.w3c.dom.Text;

import java.util.Date;

public class Comment {

    private Integer id;
    private Text text;
    private Integer postId;
    private Integer authorId;
    private Date createdAt;

}
