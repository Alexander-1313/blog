package ru.leverx.blog.service;

import ru.leverx.blog.entity.Tag;

import java.util.List;

public interface TagService {

    void save(Tag tag);

    Tag findById(Integer id);

    Tag findByName(String name);

    long getCountTagById(Integer id);

    List<Tag> findAll();
}
