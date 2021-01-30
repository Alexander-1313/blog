package ru.leverx.blog.entity;

import lombok.Data;

@Data
public class TagCount {

    private String tagName;
    private Long postCount;

    public TagCount(String tagName, Long postCount) {
        this.tagName = tagName;
        this.postCount = postCount;
    }
}
