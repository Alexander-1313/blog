package ru.leverx.blog.comparator;

import ru.leverx.blog.entity.Article;

import java.util.Comparator;

public enum ArticleComparator implements Comparator<Article> {
    CREATED_AT{
        @Override
        public int compare(Article o1, Article o2) {
            return o1.getCreatedAt().compareTo(o2.getCreatedAt());
        }
    },
    STATUS{
        @Override
        public int compare(Article o1, Article o2) {
            return o1.getStatus().compareTo(o2.getStatus());
        }
    },
    TEXT{
        @Override
        public int compare(Article o1, Article o2) {
            return o1.getText().compareTo(o2.getText());
        }
    },
    TITLE{
        @Override
        public int compare(Article o1, Article o2) {
            return o1.getTitle().compareTo(o2.getTitle());
        }
    },
    UPDATED_AT{
        @Override
        public int compare(Article o1, Article o2) {
            return o1.getUpdatedAt().compareTo(o2.getUpdatedAt());
        }
    }
}
