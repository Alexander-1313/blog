package ru.leverx.blog.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Tag;
import ru.leverx.blog.entity.TagCount;
import ru.leverx.blog.service.ArticleService;
import ru.leverx.blog.service.TagService;
import ru.leverx.blog.util.View;

import java.util.*;

@RestController
public class TagController {

    private TagService tagService;
    private ArticleService articleService;

    @Autowired
    public TagController(TagService tagService, ArticleService articleService) {
        this.tagService = tagService;
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    @JsonView(View.UI.class)
    public List<Article> findArticlesByTags(@RequestParam("tags") List<String> tags){

        List<Article> articleResult = new ArrayList<>();

        for(String tagName: tags){
            Tag tag = tagService.findByName(tagName);
            if(tag != null) {
                List<Article> articles = articleService.findByTagId(tag.getId());
                for (Article a : articles) {
                    if (!articleResult.contains(a)){
                        articleResult.add(a);
                    }
                }
            }
        }

        return articleResult;
    }

    @GetMapping("/tags-cloud")
    public List<TagCount> getTagNameAndCount(){
        List<Tag> tags = tagService.findAll();
        List<TagCount> tagCounts = new ArrayList<>();

        for(Tag t: tags){
            long countTagById = tagService.getCountTagById(t.getId());
            TagCount tagCount = new TagCount(t.getName(), countTagById);
            tagCounts.add(tagCount);
        }

        return tagCounts;
    }
}
