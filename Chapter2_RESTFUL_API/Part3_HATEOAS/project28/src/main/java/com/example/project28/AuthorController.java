package com.example.project28;

import java.util.Map;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @GetMapping("/{username}")
    public EntityModel<Map<String, String>> getAuthor(
            @PathVariable String username) {

        Map<String, String> author = Map.of(
                "username", username,
                "displayName", username
        );

        return EntityModel.of(
                author,
                linkTo(methodOn(AuthorController.class)
                        .getAuthor(username))
                        .withSelfRel()
        );
    }
}