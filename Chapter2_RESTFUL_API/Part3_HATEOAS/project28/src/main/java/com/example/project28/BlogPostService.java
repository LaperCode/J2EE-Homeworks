package com.example.project28;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BlogPostService {

    private final BlogPostRepository repository;

    public BlogPostService(BlogPostRepository repository) {
        this.repository = repository;
    }

    public Page<BlogPost> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<BlogPost> findById(Long id) {
        return repository.findById(id);
    }

    private BlogPost requirePost(Long id) {
        return findById(id).orElseThrow(
                () -> new BlogPostNotFoundException(
                        "Blog post not found: " + id
                )
        );
    }

    public BlogPost create(BlogPostRequest request) {
        validateRequest(request);

        BlogPost post = new BlogPost();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(request.getAuthor());
        post.setStatus(PostStatus.DRAFT);
        post.setCreatedAt(LocalDateTime.now());

        return repository.save(post);
    }

    public BlogPost update(Long id, BlogPostRequest request) {
        BlogPost post = requirePost(id);
        validateRequest(request);

        if (post.getStatus() == PostStatus.ARCHIVED) {
            throw new InvalidPostStateException(
                    "Archived posts cannot be updated."
            );
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(request.getAuthor());

        return repository.save(post);
    }

    public BlogPost publish(Long id) {
        BlogPost post = requirePost(id);

        if (post.getStatus() != PostStatus.DRAFT) {
            throw new InvalidPostStateException(
                    "Only draft posts can be published."
            );
        }

        post.setStatus(PostStatus.PUBLISHED);
        post.setPublishedAt(LocalDateTime.now());

        return repository.save(post);
    }

    public BlogPost archive(Long id) {
        BlogPost post = requirePost(id);

        if (post.getStatus() != PostStatus.PUBLISHED) {
            throw new InvalidPostStateException(
                    "Only published posts can be archived."
            );
        }

        post.setStatus(PostStatus.ARCHIVED);

        return repository.save(post);
    }

    public BlogPost republish(Long id) {
        BlogPost post = requirePost(id);

        if (post.getStatus() != PostStatus.ARCHIVED) {
            throw new InvalidPostStateException(
                    "Only archived posts can be republished."
            );
        }

        post.setStatus(PostStatus.PUBLISHED);
        post.setPublishedAt(LocalDateTime.now());

        return repository.save(post);
    }

    public void delete(Long id) {
        BlogPost post = requirePost(id);

        if (post.getStatus() != PostStatus.DRAFT) {
            throw new InvalidPostStateException(
                    "Only draft posts can be deleted."
            );
        }

        repository.delete(post);
    }

    private void validateRequest(BlogPostRequest request) {
        if (request == null
                || isBlank(request.getTitle())
                || isBlank(request.getContent())
                || isBlank(request.getAuthor())) {

            throw new IllegalArgumentException(
                    "Title, content and author are required."
            );
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}