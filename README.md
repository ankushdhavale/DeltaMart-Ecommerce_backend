Project: DeltaMart – E-Commerce Backend

DeltaMart is a Spring Boot-based e-commerce backend designed to manage users, products, categories, carts, favorites, and orders. It provides a full set of REST APIs for CRUD operations, cart management, favorite products, and order placement, making it ready for integration with any frontend application.

Key Features:
* User Management: Register, login, update, and delete users.
* Product Management: Add, update, search, and manage products.
* Category Management: Organize products into categories with full CRUD.
* Cart Management: Add, update, and remove products from the cart. Automatically updates total price and restores product stock when removed.
* Favorites Management: Users can add or remove favorite products.
* Order Management: Place orders using the cart with selected payment method. Updates stock and clears the cart after checkout.
* Transactional Safety: Critical operations like cart updates and order placement are transactional to maintain data consistency.
* Error Handling: Custom ResourceNotFoundException ensures meaningful responses when entities are missing.

Technology Stack:

* Java, Spring Boot
* Spring Data JPA (Hibernate)
* MySQL Database
* RESTful APIs
* Lombok
* Swagger
* Maven

Why This Project is Useful:
* Demonstrates real-world e-commerce backend functionality.
* Showcases use of DTOs, transactional operations, and proper API design.
* Provides complete backend logic for cart, favorites, orders, and user/product management.

How it Works:
1. Users can register and login.
2. Products are added to the system and organized by categories.
3. Users can add products to their cart or favorites.
4. Cart total updates automatically with quantity changes.
5. Users can place orders from their cart, which updates stock and clears the cart.

DeltaMart is perfect as a portfolio project to demonstrate skills in Java, Spring Boot, REST API development, and e-commerce backend logic.

# DeltaMart API Structure

## Cart Controller

* PUT /carts/public/updateitems/cartitemid/{cartItemId}/productId/{productId}/quantity/{quantity}
* POST /carts/public/carts/{cartId}/products/{productId}/quantity/{quantity}
* GET /carts/public/{emailId}
* GET /carts/public/carts
* GET /carts/public/cartitems/{cartId}
* GET /carts/public/cartId
* DELETE /carts/public/cartid/{cartId}/productid/{productId}

## User Controller

* GET /users
* POST /users
* POST /users/login
* DELETE /users/{uid}

## Product Controller

* POST /products/addtofav/{bid}/{uid}
* POST /products/add
* GET /products
* GET /products/{bid}
* GET /products/productname/{bname}
* GET /products/getfevs/{userId}

## Order Controller

* POST /orders/users/{emailId}/carts/{cartId}/{paymentMethod}

## Category Controller

* GET /category
* POST /category
* GET /category/{catId}
* DELETE /category/{catId}
