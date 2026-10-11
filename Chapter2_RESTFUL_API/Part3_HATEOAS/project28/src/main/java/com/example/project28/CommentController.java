package com.example.project28;

import java.util.List;
import java.util.Map;

import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final BlogPostService postService;

    public CommentController(BlogPostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public CollectionModel<Map<String, Object>> getComments(
            @PathVariable Long postId) {

        postService.findById(postId).orElseThrow(
                () -> new BlogPostNotFoundException(
                        "Blog post not found: " + postId
                )
        );

        CollectionModel<Map<String, Object>> result =
                CollectionModel.of(List.of());

        result.add(
                linkTo(methodOn(CommentController.class)
                        .getComments(postId))
                        .withSelfRel()
        );

        result.add(
                linkTo(methodOn(BlogPostController.class)
                        .getPost(postId))
                        .withRel("post")
        );

        return result;
    }
}