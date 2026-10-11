package com.example.project28;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/posts")
public class BlogPostController {

    private final BlogPostService postService;
    private final BlogPostModelAssembler assembler;

    public BlogPostController(
            BlogPostService postService,
            BlogPostModelAssembler assembler) {
        this.postService = postService;
        this.assembler = assembler;
    }

    @GetMapping
    public PagedModel<BlogPostModel> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (page < 0 || size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "Page must be >= 0 and size must be between 1 and 100."
            );
        }

        Page<BlogPost> postsPage =
                postService.findAll(PageRequest.of(page, size));

        List<BlogPostModel> models = postsPage.getContent()
                .stream()
                .map(assembler::toModel)
                .toList();

        PagedModel.PageMetadata metadata =
                new PagedModel.PageMetadata(
                        postsPage.getSize(),
                        postsPage.getTotalElements(),
                        postsPage.getTotalPages(),
                        postsPage.getNumber()
                );

        PagedModel<BlogPostModel> collection =
                PagedModel.of(models, metadata);

        collection.add(
                linkTo(methodOn(BlogPostController.class)
                        .getAllPosts(page, size))
                        .withSelfRel()
        );

        collection.add(
                linkTo(methodOn(BlogPostController.class)
                        .getAllPosts(0, size))
                        .withRel("first")
        );

        int totalPages = postsPage.getTotalPages();

        if (page > 0) {
            collection.add(
                    linkTo(methodOn(BlogPostController.class)
                            .getAllPosts(page - 1, size))
                            .withRel("prev")
            );
        }

        if (page + 1 < totalPages) {
            collection.add(
                    linkTo(methodOn(BlogPostController.class)
                            .getAllPosts(page + 1, size))
                            .withRel("next")
            );
        }

        int lastPage = Math.max(totalPages - 1, 0);

        collection.add(
                linkTo(methodOn(BlogPostController.class)
                        .getAllPosts(lastPage, size))
                        .withRel("last")
        );

        return collection;
    }

    @GetMapping("/{id}")
    public BlogPostModel getPost(@PathVariable Long id) {
        BlogPost post = postService.findById(id)
                .orElseThrow(() ->
                        new BlogPostNotFoundException(
                                "Blog post not found: " + id
                        )
                );

        return assembler.toModel(post);
    }

    @PostMapping
    public ResponseEntity<BlogPostModel> createPost(
            @RequestBody BlogPostRequest request) {

        BlogPost post = postService.create(request);
        BlogPostModel model = assembler.toModel(post);

        URI location = model.getRequiredLink("self").toUri();

        return ResponseEntity.created(location).body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPostModel> updatePost(
            @PathVariable Long id,
            @RequestBody BlogPostRequest request) {

        BlogPost post = postService.update(id, request);

        return ResponseEntity.ok(assembler.toModel(post));
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<BlogPostModel> publishPost(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                assembler.toModel(postService.publish(id))
        );
    }

    @PostMapping("/{id}/archive")
    public ResponseEntity<BlogPostModel> archivePost(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                assembler.toModel(postService.archive(id))
        );
    }

    @PostMapping("/{id}/republish")
    public ResponseEntity<BlogPostModel> republishPost(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                assembler.toModel(postService.republish(id))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id) {

        postService.delete(id);

        return ResponseEntity.noContent().build();
    }
}