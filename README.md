# Bookstore Management System

This project is a Java Spring REST API for managing bookstores, books, and sales. It consists of three main entities: Book, Bookstore, and Sale.
This bookstore management system ensures that it will be possible to track what books are available in certain bookstores and what books are for sale. 
This system will allow managers to perform various analyses, such as which books sell best, which bookstores have the highest sales, etc.

## Features

- **Book Management**: CRUD operations for books.
- **Bookstore Management**: CRUD operations for bookstores.
- **Sales Management**: CRUD operations for sales.

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Lombok
- My SQL Database
- H2 Database (for testing purposes)
- Maven

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

## Getting Started

1. **Clone the repository**

    ```bash
    git clone https://github.com/mariusmisulaitis/bookstore-api.git
    cd bookstore-api
    ```

2. **Build the project**

    ```bash
    mvn clean install
    ```

3. **Run the application**

    ```bash
    mvn spring-boot:run
    ```

4. **Access the API documentation**

    The application will be running at `http://localhost:8080`. You can access the API documentation (e.g., Swagger) at `http://localhost:8080/swagger-ui/index.html#/`.

## API Endpoints

### Book Endpoints

- `GET /books` - Get all books
- `GET /books/{bookId}` - Get a book by ID
- `POST /books` - Add a new book
- `PUT /books/{bookId}` - Update a book's details
- `DELETE /books/{bookId}` - Delete a book by ID

### Bookstore Endpoints

- `GET /bookstores` - Get all bookstores
- `GET /bookstores/{bookstoreId}` - Get a bookstore by ID
- `POST /bookstores` - Add a new bookstore
- `PUT /bookstores/{bookstoreId}` - Update a bookstore's details
- `PATCH /bookstores/{bookstoreId}/title` - Update a bookstore's name
- `DELETE /bookstores/{bookstoreId}` - Delete a bookstore by ID
- `GET /bookstores/{bookstoreId}/books` - Get all books in a specific bookstore
- `POST /bookstores/{bookstoreId}/books` - Add a book to a specific bookstore
- `DELETE /bookstores/{bookstoreId}/books/{bookId}` - Delete a book from a specific bookstore

### Sale Endpoints

- `GET /sales` - Get all sales
- `GET /sales/{saleId}` - Get a sale by ID
- `POST /sales` - Add a new sale
- `PUT /sales/{saleId}` - Update a sale's details
- `DELETE /sales/{saleId}` - Delete a sale by ID

## Example Request

### Add a new Book


```bash
curl -X POST "http://localhost:8080/books" -H "Content-Type: application/json" -d '{
  "title": "Example Book",
  "author": "Author Name",
  "isbn": "1234567890",
  "price": 29.99,
  "bookstoreId": 1
}'



Error Handling
The application handles errors gracefully and returns appropriate HTTP status codes and error messages for various error scenarios, including validation errors and not found errors.



Endpoint protection based on roles:

BookController:
POST /books - Only ADMIN and MANAGER.
GET /books - All (USER, ADMIN, MANAGER).
GET /books/{bookId} - All (USER, ADMIN, MANAGER).
PUT /books/{bookId} - Only ADMIN and MANAGER.
DELETE /books/{bookId} - Only ADMIN and MANAGER.

BookstoreController:
POST /bookstores - Only ADMIN and MANAGER.
GET /bookstores - All (USER, ADMIN, MANAGER).
GET /bookstores/{bookstoreId} - All (USER, ADMIN, MANAGER).
PATCH /bookstores/{bookstoreId}/title - Only ADMIN and MANAGER.
PUT /bookstores/{bookstoreId} - Only ADMIN and MANAGER.
DELETE /bookstores/{bookstoreId} - Only ADMIN and MANAGER.
GET /bookstores/{bookstoreId}/books - All (USER, ADMIN, MANAGER).
POST /bookstores/{bookstoreId}/books - Only ADMIN and MANAGER.
GET /bookstores/{bookstoreId}/books/{bookId} - All (USER, ADMIN, MANAGER).
DELETE /bookstores/{bookstoreId}/books/{bookId} - Only ADMIN ir MANAGER.

SaleController:
POST /sales - Only ADMIN and MANAGER.
GET /sales - All (USER, ADMIN, MANAGER).
GET /sales/{saleId} - All (USER, ADMIN, MANAGER).
PUT /sales/{saleId} - Only ADMIN and MANAGER.
DELETE /sales/{saleId} - Only ADMIN and MANAGER.



Contributing
Fork the repository
Create a new branch (git checkout -b feature-branch)
Make your changes
Commit your changes (git commit -m 'Add some feature')
Push to the branch (git push origin feature-branch)
Open a Pull Request



Contact
If you have any questions or suggestions, feel free to reach out to us at [marius.misulaitis7@gmail.com].
