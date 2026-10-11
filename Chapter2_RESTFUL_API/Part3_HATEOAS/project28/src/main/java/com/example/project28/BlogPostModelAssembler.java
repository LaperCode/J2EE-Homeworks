package com.example.project28;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class BlogPostModelAssembler
        implements RepresentationModelAssembler<BlogPost, BlogPostModel> {

    @Override
    public BlogPostModel toModel(BlogPost post) {
        BlogPostModel model = new BlogPostModel();

        model.setId(post.getId());
        model.setTitle(post.getTitle());
        model.setContent(post.getContent());
        model.setAuthor(post.getAuthor());
        model.setStatus(post.getStatus());
        model.setCreatedAt(post.getCreatedAt());
        model.setPublishedAt(post.getPublishedAt());

        model.add(
                linkTo(methodOn(BlogPostController.class)
                        .getPost(post.getId()))
                        .withSelfRel()
        );

        if (post.getStatus() == PostStatus.DRAFT) {
            model.add(
                    linkTo(methodOn(BlogPostController.class)
                            .publishPost(post.getId()))
                            .withRel("publish")
            );

            model.add(
                    linkTo(methodOn(BlogPostController.class)
                            .updatePost(post.getId(), null))
                            .withRel("update")
            );

            model.add(
                    linkTo(methodOn(BlogPostController.class)
                            .deletePost(post.getId()))
                            .withRel("delete")
            );
        }

        if (post.getStatus() == PostStatus.PUBLISHED) {
            model.add(
                    linkTo(methodOn(BlogPostController.class)
                            .archivePost(post.getId()))
                            .withRel("archive")
            );

            model.add(
                    linkTo(methodOn(BlogPostController.class)
                            .updatePost(post.getId(), null))
                            .withRel("update")
            );
        }

        if (post.getStatus() == PostStatus.ARCHIVED) {
            model.add(
                    linkTo(methodOn(BlogPostController.class)
                            .republishPost(post.getId()))
                            .withRel("republish")
            );
        }

        model.add(
                linkTo(methodOn(AuthorController.class)
                        .getAuthor(post.getAuthor()))
                        .withRel("author")
        );

        model.add(
                linkTo(methodOn(CommentController.class)
                        .getComments(post.getId()))
                        .withRel("comments")
        );

        return model;
    }
}