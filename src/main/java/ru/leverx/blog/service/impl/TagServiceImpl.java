package ru.leverx.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.leverx.blog.entity.Tag;
import ru.leverx.blog.repository.TagRepository;
import ru.leverx.blog.service.TagService;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public void save(Tag tag) {
        tagRepository.save(tag);
    }

    @Override
    public Tag findById(Integer id) {
        return tagRepository.getOne(id);
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public long getCountTagById(Integer id) {
        return tagRepository.countTag(id);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }
}
