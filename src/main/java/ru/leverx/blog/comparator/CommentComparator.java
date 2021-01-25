package ru.leverx.blog.comparator;

import ru.leverx.blog.entity.Comment;

import java.util.Comparator;

public enum CommentComparator implements Comparator<Comment> {
    MESSAGE{
        @Override
        public int compare(Comment o1, Comment o2) {
            return o1.getText().compareTo(o2.getText());
        }
    },
    CREATED_AT{
        @Override
        public int compare(Comment o1, Comment o2) {
            return o1.getCreatedAt().compareTo(o2.getCreatedAt());
        }
    }
}
