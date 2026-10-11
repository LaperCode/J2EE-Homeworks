package com.example.project28;

public class BlogPostNotFoundException
        extends RuntimeException {

    public BlogPostNotFoundException(String message) {
        super(message);
    }
}