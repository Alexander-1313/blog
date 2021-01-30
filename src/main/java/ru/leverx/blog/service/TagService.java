package ru.leverx.blog.service;

import ru.leverx.blog.entity.Tag;

public interface TagService {

    void save(Tag tag);

    Tag findById(Integer id);

    Tag findByName(String name);
}
