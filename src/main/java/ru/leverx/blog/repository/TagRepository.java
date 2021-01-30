package ru.leverx.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.leverx.blog.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    Tag findByName(String name);
}
