# 📘 README.md – E-Commerce Microservice

## 1. Overview

This is a Spring Boot-based **E-commerce Microservice** that handles **product management** and **order management**.

It provides APIs for:

* Managing products (CRUD + image upload)
* Placing orders and fetching orders
* Searching products
* Handling stock validations

Technologies used:

* Java 17
* Spring Boot 3.x
* Spring Data JPA
* Lombok
* H2/MySQL (DB)
* Postman for API testing

---

## 2. Database Tables

### 2.1 `product_schema.product`

| Column Name       | Type          | Constraints        |
| ----------------- | ------------- | ------------------ |
| id                | BIGINT        | PK, auto increment |
| name              | VARCHAR       | NOT NULL           |
| description       | TEXT          |                    |
| brand             | VARCHAR       |                    |
| price             | DECIMAL(10,2) |                    |
| category          | VARCHAR       |                    |
| release_date      | DATE          |                    |
| product_available | BOOLEAN       |                    |
| stock_quantity    | INT           |                    |
| image_name        | VARCHAR       |                    |
| image_type        | VARCHAR       |                    |
| image_data        | LONGBLOB      |                    |

---

### 2.2 `orders`

| Column Name   | Type    | Constraints        |
| ------------- | ------- | ------------------ |
| id            | BIGINT  | PK, auto increment |
| order_id      | VARCHAR | UNIQUE             |
| customer_name | VARCHAR |                    |
| email         | VARCHAR |                    |
| status        | VARCHAR |                    |
| order_date    | DATE    |                    |

---

### 2.3 `order_items`

| Column Name | Type    | Constraints        |
| ----------- | ------- | ------------------ |
| id          | BIGINT  | PK, auto increment |
| product_id  | BIGINT  | FK → product.id    |
| order_id    | BIGINT  | FK → orders.id     |
| quantity    | INT     |                    |
| total_price | DECIMAL |                    |

---

## 3. Entity Relationships

```text
Products 1 <---> * OrderItems * <---> 1 Orders
```

* One product can appear in multiple order items.
* One order can have multiple order items.

**Visual Diagram:**

```text
+-------------------+        +-------------------+        +-------------------+
|     Products      |        |     OrderItems    |        |      Orders       |
+-------------------+        +-------------------+        +-------------------+
| id (PK)           |<--1  * | id (PK)           | *--> 1 | id (PK)           |
| name              |        | product_id (FK)   |        | order_id (Unique) |
| description       |        | order_id (FK)     |        | customer_name     |
| brand             |        | quantity          |        | email             |
| price             |        | total_price       |        | status            |
| category          |        +-------------------+        | order_date        |
| release_date      |                                      +-------------------+
| product_available |
| stock_quantity    |
| image_name        |
| image_type        |
| image_data        |
+-------------------+
```

---

## 4. API Endpoints Overview

### 4.1 Products

| Method | Endpoint                      | Description                          |
| ------ | ----------------------------- | ------------------------------------ |
| GET    | /api/products                 | Fetch all products                   |
| GET    | /api/products/{id}            | Fetch single product by ID           |
| POST   | /api/product                  | Add a product (with image)           |
| PUT    | /api/product/{id}             | Update product (with optional image) |
| DELETE | /api/product/{id}             | Delete product                       |
| GET    | /api/products/search?keyword= | Search products by keyword           |
| GET    | /api/product/{id}/image       | Fetch product image                  |

---

### 4.2 Orders

| Method | Endpoint          | Description      |
| ------ | ----------------- | ---------------- |
| POST   | /api/orders/place | Place an order   |
| GET    | /api/orders       | Fetch all orders |

---

## 5. Flow Diagrams

### 5.1 High-Level Request Flow

```text
Frontend/Client
      |
      v
  ProductController / OrderController
      |
      v
  ProductService / OrderService
      |
      v
 ProductRepository / OrderRepository
      |
      v
       MySQL Database
```

---

### 5.2 Order Placement Flow

```text
Customer places order
      |
      v
OrderController.placeOrder()
      |
      v
OrderService.placeOrder()
      |-- Validates stock
      |-- Reduces product stock
      |-- Creates Orders + OrderItems
      |
      v
OrderRepository.save(order)
      |
      v
Returns OrderResponseDTO
```

---

### 5.3 Product Image Upload Flow

```text
Frontend sends multipart request
      |
      v
ProductController.addProduct()
      |
      v
ProductService.addProduct()
      |-- Saves product details
      |-- Saves image bytes in DB
      |
      v
ProductRepository.save(product)
      |
      v
Returns saved product
```

---

## 6. Postman Collection Overview

### Example Request/Response Format (for Notes.txt)

**Api Method**: POST
**End Point**: /api/product
**Request Body (multipart JSON + file)**:

```json
{
  "name": "iPhone 14",
  "description": "Latest iPhone",
  "brand": "Apple",
  "price": 120000,
  "category": "Mobile",
  "releaseDate": "24-01-2026",
  "productAvailable": true,
  "stockQuantity": 50
}
```

**Sample Response 201 Created**:

```json
{
  "id": 1,
  "name": "iPhone 14",
  "description": "Latest iPhone",
  "brand": "Apple",
  "price": 120000,
  "category": "Mobile",
  "releaseDate": "2026-01-24",
  "productAvailable": true,
  "stockQuantity": 50
}
```

**Sample Response 400 Bad Request**:

```json
{
  "Status": "Failed",
  "Message": "Validation failed or missing fields"
}
```

**Sample Response 500 Internal Server Error**:

```json
{
  "Status": "Error",
  "Message": "API is not working as expected: <error details>"
}
```

---

### 6.1 Order Placement Example

**Api Method**: POST
**End Point**: /api/orders/place
**Request Body**:

```json
{
  "customerName": "Ratnakar Kolhatkar",
  "email": "ratnakar@example.com",
  "items": [
    { "productId": 1, "quantity": 2 },
    { "productId": 2, "quantity": 1 }
  ]
}
```

**Sample Response 201 Created**:

```json
{
  "orderId": "ORDA1B2C3D4",
  "customerName": "Ratnakar Kolhatkar",
  "email": "ratnakar@example.com",
  "status": "PLACED",
  "orderDate": "2026-01-24",
  "items": [
    { "productName": "iPhone 14", "quantity": 2, "totalPrice": 240000 },
    { "productName": "Samsung Galaxy", "quantity": 1, "totalPrice": 70000 }
  ]
}
```

**Sample Response 400 Bad Request** (Insufficient Stock):

```json
{
  "timestamp": "2026-01-24T10:15:30",
  "message": "Insufficient stock for product: iPhone 14",
  "status": 400
}
```

**Sample Response 404 Not Found**:

```json
{
  "timestamp": "2026-01-24T10:15:30",
  "message": "Product not found: 999",
  "status": 404
}
```

---

### 6.2 Notes.txt

Create a separate **Notes.txt** for all APIs with this format:

```
API Method : POST
End point: /api/product
Request Body:
{
  "name": "iPhone 14",
  "description": "Latest iPhone",
  ...
}
Sample Response: 201 Created
Response Body:
{
  ...
}
Sample Response: 400 Bad Request
Response Body:
{
  ...
}
```
