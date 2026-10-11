package com.example.project28;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BlogPostDataInitializer implements CommandLineRunner {

    private final BlogPostRepository repository;

    public BlogPostDataInitializer(BlogPostRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        BlogPost draft = new BlogPost();
        draft.setTitle("Getting Started with HATEOAS");
        draft.setContent("HATEOAS is a constraint of REST.");
        draft.setAuthor("john.doe");
        draft.setStatus(PostStatus.DRAFT);
        draft.setCreatedAt(now.minusDays(1));

        BlogPost published = new BlogPost();
        published.setTitle("Advanced HATEOAS Patterns");
        published.setContent("Exploring advanced HATEOAS patterns.");
        published.setAuthor("jane.smith");
        published.setStatus(PostStatus.PUBLISHED);
        published.setCreatedAt(now.minusDays(2));
        published.setPublishedAt(now.minusDays(1));

        repository.saveAll(List.of(draft, published));
    }
}