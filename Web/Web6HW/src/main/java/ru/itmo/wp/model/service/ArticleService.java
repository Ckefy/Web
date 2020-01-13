package ru.itmo.wp.model.service;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

/** @noinspection UnstableApiUsage*/
public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<Article> findAll(long userId) {
        return articleRepository.findAll(userId);
    }

    public void createArticle(User user, String title, String text) throws ValidationException {
        if (title.isEmpty()){
            throw new ValidationException("Title is empty");
        }
        if (text.length() > 255){
            throw new ValidationException("Text cannot be longer than 255 symbols");
        }
        if (text.isEmpty()){
            throw new ValidationException("Text is empty");
        }
        if (title.length() > 255){
            throw new ValidationException("Title cannot be longer than 255 symbols");
        }
        articleRepository.save(user.getId(), title, text);
    }

    public void update(long articalId, long type) {
            articleRepository.update(articalId, type);
        }
}
