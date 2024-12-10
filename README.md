# Literalura 📚

A Spring Boot application for managing books and authors, with additional functionalities such as querying data via APIs and displaying relevant information.

---

## Features
- Manage and query books and authors.
- Fetch book data from external APIs.
- Handle user inputs dynamically.
- Search books by language, title, and other parameters.
- Menu-driven console interface for easy navigation.

---

## Getting Started

### Prerequisites
Ensure you have the following installed:
- [Java 17+](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [Spring Boot](https://spring.io/projects/spring-boot)

### Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/fjarar/literalura.git
    ```
2. Navigate to the project directory:
    ```bash
    cd literalura
    ```
3. Build the project:
    ```bash
    mvn clean install
    ```
4. Run the application:
    ```bash
    mvn spring-boot:run
    ```

---

## Usage

The application is menu-driven. After starting the app, follow the prompts in the console to:
- Search for books by title or language.
- List authors based on specific criteria.
- Fetch book information via API.
- Exit the application gracefully.

---

## API Integration

This project integrates with an external API to fetch book information. The base URL is hardcoded in the `Principal` class:

```java
private final String URL_BASE = "https://gutendex.com/books/";
```
---

## Project Structure

literalura/ 
├── src/ 
│├── main/ 
│ │ ├── java/com/flaviojara/literalura/ 
│ │ │ ├── controller/ # Controllers for handling requests 
│ │ │ ├── model/ # Models for Author, Book, etc. 
│ │ │ ├── repository/ # JPA Repositories 
│ │ │ ├── service/ # Business logic services 
│ │ │ └── principal/ # Main application logic 
│ │ ├── resources/ # Application properties and static files 
│ └── test/ # Test files 
├── pom.xml # Maven configuration 
└── README.md # Project documentation


---

## Key Classes

- **`LiteraluraApplication`**: The main entry point for the application.
- **`Principal`**: Handles the core application logic and user interactions.
- **`LibroRepository`**: Manages database operations for books.
- **`AutorRepository`**: Manages database operations for authors.

---

## Error Handling

### Common Issues
1. **NullPointerException**:
   - Ensure all required fields are properly initialized.
   - Verify the API response structure matches your model.

2. **Invalid API Response**:
   - Ensure the API URL is correct in `application.properties`.
   - Check your internet connection.

3. **Invalid Input**:
   - The application gracefully handles invalid inputs and returns to the main menu.

---

## Contributing

Contributions are welcome! To contribute:
1. Fork the repository.
2. Create a new branch for your feature or bugfix:
    ```bash
    git checkout -b feature/your-feature-name
    ```
3. Commit your changes and push:
    ```bash
    git commit -m "Your commit message"
    git push origin feature/your-feature-name
    ```
4. Open a pull request.

---

## Author

**Flavio Jara Rebolledo**

Connect with me on [LinkedIn](https://linkedin.com/in/flaviojara) or check out my [GitHub profile](https://github.com/fjarar).

---

## Acknowledgments

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Hibernate ORM](https://hibernate.org/)
- [https://openlibrary.org/developers/api](https://gutendex.com/)
