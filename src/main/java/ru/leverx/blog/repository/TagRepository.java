package ru.leverx.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.leverx.blog.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    Tag findByName(String name);

    @Query(value = "SELECT count(*) FROM tag t inner join article_tag a on t.id = a.tag_id WHERE t.id = ?1 GROUP BY t.name", nativeQuery = true)
    long countTag(Integer id);


}
