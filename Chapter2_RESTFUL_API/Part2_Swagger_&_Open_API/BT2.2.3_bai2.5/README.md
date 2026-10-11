# Project 2.5 - Spring Boot + OpenAPI 3.0

Dự án này triển khai lại bài thực hành **"Documenting a Spring REST API Using OpenAPI 3.0"** theo cấu trúc đơn giản để dễ chạy bằng IntelliJ IDEA.

## 1. Mục tiêu

- Tạo REST API quản lý User.
- Tích hợp OpenAPI 3.0 với Spring Boot.
- Hiển thị API trên Swagger UI.
- Dùng annotation `@Operation`, `@ApiResponse`, `@Tag`, `@Schema` để mô tả endpoint.
- Gom nhóm các endpoint `/users/**` thành nhóm **User API**.

## 2. Công nghệ

- Java 17
- Spring Boot 3.5.6
- Spring Web
- Springdoc OpenAPI
- Swagger UI
- Maven

> Repo gốc trong đề đang dùng Spring Boot 2.5.4, Java 8 và `springdoc-openapi-ui` 1.5.10. Bản bài làm này giữ nguyên ý tưởng của bài viết nhưng nâng stack lên Spring Boot 3.5.6 + Java 17 để phù hợp môi trường JDK mới.

## 3. Chạy project

Mở đúng thư mục có `pom.xml` bằng IntelliJ IDEA.

Sau khi Maven tải dependency xong, chạy:

```bash
mvn spring-boot:run
```

hoặc chạy class:

```text
com.Application
```

Server:

```text
http://localhost:8080
```

## 4. Swagger UI / OpenAPI

Swagger UI:

```text
http://localhost:8080/apidoc
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

## 5. Các endpoint

### POST /users?name=Nguyen%20Van%20An

Đăng ký user mới.

Ví dụ:

```http
POST http://localhost:8080/users?name=Nguyen%20Van%20An
```

Response 200:

```json
{
  "id": 3,
  "name": "Nguyen Van An"
}
```

### GET /users

Lấy danh sách tất cả user.

### GET /users/{id}

Lấy user theo ID. Không tồn tại trả `404`.

### PUT /users/{id}?name=NewName

Cập nhật tên user. Không tồn tại trả `404`.

### DELETE /users/{id}

Xóa user. Thành công trả `204`.

## 6. Dữ liệu mẫu

Khi khởi động ứng dụng, repository tạo sẵn:

```text
1 - John Doe
2 - Alice Nguyen
```

Đây là dữ liệu in-memory nên sẽ reset khi restart ứng dụng.

## 7. Kiến trúc thư mục

```text
src/main/java/com
├── Application.java
├── config
│   └── SwaggerConfig.java
├── controller
│   └── UserController.java
├── model
│   └── User.java
└── repository
    └── UserRepository.java
```

## 8. Điểm cần trình bày khi demo

1. Spring Boot tạo REST API bằng `@RestController`.
2. `UserController` cung cấp các endpoint `/users/**`.
3. `SwaggerConfig` tạo nhóm **User API** bằng `GroupedOpenApi`.
4. `@Operation` và `@ApiResponse` giúp mô tả endpoint trên Swagger.
5. `springdoc.swagger-ui.path=/apidoc` đổi đường dẫn Swagger UI thành `/apidoc`.
