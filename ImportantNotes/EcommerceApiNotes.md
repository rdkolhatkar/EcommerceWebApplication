# 📄 Notes.txt – E-Commerce Microservice APIs

---

## **1. Products APIs**

### 1.1 Get All Products

**API Method**: GET
**End Point**: `/api/products`

**Request Body**: None

**Sample Response 200 OK**:

```json
[
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
  },
  {
    "id": 2,
    "name": "Samsung Galaxy",
    "description": "Latest Galaxy",
    "brand": "Samsung",
    "price": 70000,
    "category": "Mobile",
    "releaseDate": "2026-01-20",
    "productAvailable": true,
    "stockQuantity": 30
  }
]
```

---

### 1.2 Get Product By ID

**API Method**: GET
**End Point**: `/api/products/{id}`

**Request Body**: None

**Sample Response 200 OK**:

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

**Sample Response 404 Not Found**:

```json
{
  "timestamp": "2026-01-24T10:15:30",
  "message": "Product not found",
  "status": 404
}
```

---

### 1.3 Add Product (with Image)

**API Method**: POST
**End Point**: `/api/product`
**Request Type**: `multipart/form-data`

**Request Body Example**:

* Part `"product"` (JSON):

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

* Part `"imageFile"`: Upload a JPEG/PNG image

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
  "Message": "Invalid request or missing fields"
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

### 1.4 Update Product (with optional image)

**API Method**: PUT
**End Point**: `/api/product/{id}`
**Request Type**: `multipart/form-data`

**Request Body Example**:

* Part `"product"` (JSON):

```json
{
  "name": "iPhone 14 Pro",
  "description": "Updated version",
  "brand": "Apple",
  "price": 135000,
  "category": "Mobile",
  "releaseDate": "24-01-2026",
  "productAvailable": true,
  "stockQuantity": 45
}
```

* Part `"imageFile"`: Optional, upload new image

**Sample Response 200 OK**:

```json
"Updated Successfully"
```

**Sample Response 404 Not Found**:

```json
"Product not found"
```

**Sample Response 400 Bad Request**:

```json
"Invalid request or update failed"
```

---

### 1.5 Delete Product

**API Method**: DELETE
**End Point**: `/api/product/{id}`

**Sample Response 200 OK**:

```json
"Product Deleted"
```

**Sample Response 404 Not Found**:

```json
"Product not found"
```

---

### 1.6 Search Products

**API Method**: GET
**End Point**: `/api/products/search?keyword={keyword}`

**Sample Response 200 OK**:

```json
[
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
]
```

---

### 1.7 Get Product Image

**API Method**: GET
**End Point**: `/api/product/{id}/image`

**Sample Response 200 OK**: Returns raw image bytes (Content-Type: image/jpeg/png)

**Sample Response 404 Not Found**: If image not uploaded

---

## **2. Orders APIs**

### 2.1 Place Order

**API Method**: POST
**End Point**: `/api/orders/place`

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

**Sample Response 400 Bad Request (Insufficient Stock)**:

```json
{
  "timestamp": "2026-01-24T10:15:30",
  "message": "Insufficient stock for product: iPhone 14",
  "status": 400
}
```

**Sample Response 404 Not Found (Product Not Found)**:

```json
{
  "timestamp": "2026-01-24T10:15:30",
  "message": "Product not found: 999",
  "status": 404
}
```

---

### 2.2 Get All Orders

**API Method**: GET
**End Point**: `/api/orders`

**Sample Response 200 OK**:

```json
[
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
]
```

---

This **Notes.txt** now has **all APIs** fully documented with:

* Method, endpoint
* Request body examples
* Success responses (200, 201)
* Error responses (400, 404, 500)
