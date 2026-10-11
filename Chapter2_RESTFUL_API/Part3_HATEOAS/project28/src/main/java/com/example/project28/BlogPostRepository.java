package com.example.project28;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository
        extends JpaRepository<BlogPost, Long> {
}