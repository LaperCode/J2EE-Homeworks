package com.example.project31;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductRequest {

    @NotBlank(
            message = "Product name cannot be blank",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Size(
            min = 2,
            max = 100,
            message = "Product name must be between 2 and 100 characters",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String name;

    @NotNull(
            message = "Price cannot be null",
            groups = OnCreate.class
    )
    @Positive(
            message = "Price must be positive",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private Double price;

    @NotNull(
            message = "Category ID is required",
            groups = OnCreate.class
    )
    @Positive(
            message = "Category ID must be positive",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private Long categoryId;

    @Valid
    @Size(
            max = 5,
            message = "You can add up to 5 tags",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private List<
            @NotBlank(
                    message = "Tag cannot be blank",
                    groups = {OnCreate.class, OnUpdate.class}
            )
                    String
            > tags;

    @Email(
            message = "Invalid email format for warranty",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String emailForWarranty;

    @Min(
            value = 0,
            message = "Discount cannot be negative",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Max(
            value = 80,
            message = "Discount cannot exceed 80%",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private Integer discountPercentage;

    @Future(
            message = "Availability date must be in the future",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private LocalDate availabilityDate;

    private String sku;

    public ProductRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getEmailForWarranty() {
        return emailForWarranty;
    }

    public void setEmailForWarranty(String emailForWarranty) {
        this.emailForWarranty = emailForWarranty;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public LocalDate getAvailabilityDate() {
        return availabilityDate;
    }

    public void setAvailabilityDate(LocalDate availabilityDate) {
        this.availabilityDate = availabilityDate;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}