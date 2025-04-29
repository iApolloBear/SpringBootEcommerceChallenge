# 🛒 Order Management API

A simple e-commerce order management API built with **Spring Boot**, **PostgreSQL**, and **Docker**. This application
manages `Products`, `Orders`, and `Order Items`, including full CRUD operations and business logic for handling
quantities, price calculations, and order totals.

## 📦 Features

- CRUD operations for:
  - **Products**
  - **Orders**
  - **Order Items**
- Total order price calculation handled automatically
- Business rules:
  - Changing quantity updates the total
  - Quantity `<= 0` deletes the item
  - Updating order/product IDs triggers automatic recalculations
- OpenAPI (Swagger) documentation
- Full integration tests and unit tests

---

## 🧱 Technologies Used

- ☕️ **Java 21**
- 🍃 **Spring Boot 3**
- 🍃 **Spring Data JPA**
- 🐘 **PostgreSQL**
- 📚 **Swagger / OpenAPI**
- 🧪 **JUnit & Mockito**
- 🐳 **Docker & Docker Compose**

---

## 🚀 Getting Started

### 🔧 Prerequisites

- Docker & Docker Compose
- Java 21+
- PostgreSQL

### 📂 Clone the Repository

```bash
git clone https://github.com/iApolloBear/SpringBootEcommerceChallenge.git
cd ecommerce-challenge
```

### 🐳 Run with Docker (Recommended)

> Make sure you have Docker and Docker Compose installed.

⚙️ (Optional) Change your app or db port in `docker-compose.yml`
```yaml
services:
  db:
    ports:
      - "{your-db-port}:5432"
  app:
    ports: 
      - "{your-app-port}:8080"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:{your-db-port}/ecommerce
```
`src/main/resources/application.yml`
```yaml
spring:
  datasource:
    url: jdbc:postgresql://db:{your-db-port}/ecommerce
```

▶️ 1. Run the following command:

```bash
docker compose up -d
```

The app will be available at:

Swagger UI:
- http://localhost:8080/swagger-ui/index.html
- http://localhost:{your-app-port}/swagger-ui/index.html

API Root:
- http://localhost:8080
- http://localhost:{your-app-port}

🛑 Stop the app: Run the following command
```bash
docker compose down
```

### 🛠️ Running the Project Locally with PostgreSQL

⚙️ 1. PostgreSQL Setup

Make sure PostgreSQL is running

🧾 2. Edit application.yml

Create or update the file at:
src/main/resources/application.yml

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/{your-database-name}
    username: {your-postgresql-username}
    password: {your-postgresql-password}
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: none
    defer-datasource-initialization: true
  sql:
    init:
      mode: always
```

> ⚠️ Adjust username, password, and url to match your PostgreSQL config.

⚙️ (Optional) Modify database schema

Create or update the file at:
src/main/resources/schema.sql

```sql
CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(80) NOT NULL,
    description TEXT,
    price NUMERIC(15, 2) NOT NULL CHECK (price >= 0)
);

CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    total NUMERIC(15, 2) DEFAULT 0 NOT NULL CHECK (total >= 0)
);

CREATE TABLE IF NOT EXISTS order_items (
    id SERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    product_id INTEGER NOT NULL REFERENCES products(id),
    quantity INTEGER NOT NULL CHECK (quantity >= 0),
    price NUMERIC(15, 2) NOT NULL CHECK (price >= 0)
);
```

▶️ 3. Run the Application
```bash
./mvnw spring-boot:run
```
The app will be available at:

Swagger UI: http://localhost:8080/swagger-ui/index.html

API Root: http://localhost:8080

🐳 4. (Optional) Build your docker image
```bash
docker build . -t "your-tag:version" 
# Example: docker build . -t ecommerce:1.0
```
🐳 5. (Optional) Run your docker image
```bash
docker run -p {your-port}:8080 -d "your-tag:version"
# Example: docker run -p 8080:8080 -d ecommerce:1.0
```
You can then visit:

Swagger UI:
- http://localhost:8080/swagger-ui/index.html
- http://localhost:{your-port}/swagger-ui/index.html

API Root:
- http://localhost:8080
- http://localhost:{your-port}

### 🧪 Testing

▶️ 1. Run the following command:
```bash
./mvnw test
```
🧑‍💻 2. You can write your own tests in the following locations
- `src/test/java/com/aldo/ecommerce_challenge/orderItems`
- `src/test/java/com/aldo/ecommerce_challenge/orders`
- `src/test/java/com/aldo/ecommerce_challenge/products`

📝 3. Modify or add data to test repositories in `src/test/resources/import.sql`
```sql
INSERT INTO products 
(name, description, price) 
VALUES 
('GUTS', 'Olivia Rodrigo Album', 938), 
('F-1 Trillion', 'Post Malone Album', 1499);

INSERT INTO orders 
(total) 
VALUES 
(938), 
(2437);

INSERT INTO order_items 
(order_id, product_id, quantity, price) 
VALUES 
(1, 1, 1, 938), 
(2, 2, 1, 1499), 
(2, 1, 1, 938);
```

✍️ 4.- Modify or add data to tests services and controllers in
- `src/test/java/com/aldo/ecommerce_challenge/products/ProductsData.java`
- `src/test/java/com/aldo/ecommerce_challenge/orders/OrdersData.java`
- `src/test/java/com/aldo/ecommerce_challenge/orderItems/OrderItemsData.java`

```java
public class ProductsData {
  public static Optional<Product> createProductOne() {
    Product product = new Product(1L, "GUTS", "Olivia Rodrigo Album", new BigDecimal("938"));
    return Optional.of(product);
  }
}
```
```java
public class OrderItemsData {
  public static OrderItemDTO createOrderItemDTOOne() {
    return new OrderItemDTO(1L, 1L, 1L, 1, new BigDecimal("938"));
  }

  public static OrderItemCreateDTO createOrderItemCreateDTO() {
    return new OrderItemCreateDTO(1L, 2L, 2);
  }
}
```
```java
public class OrdersData {
  public static Optional<OrderDTO> createOrderDTOOne() {
    return Optional.of(
        new OrderDTO(
            1L,
            new BigDecimal("2437"),
            List.of(
                OrderItemsData.createOrderItemDTOOne(), OrderItemsData.createOrderItemDTOTwo())));
  }

  public static Optional<Order> createOrderOne() {
    Order order = new Order();
    order.setId(1L);
    OrderItem orderItem1 =
        new OrderItem(1L, order, ProductsData.createProductOne().orElseThrow(), 1);
    OrderItem orderItem2 =
        new OrderItem(2L, order, ProductsData.createProductTwo().orElseThrow(), 1);
    order.setOrderItems(List.of(orderItem1, orderItem2));
    order.setTotal(new BigDecimal("2437"));
    return Optional.of(order);
  }
}
```

### 🧱 Build Your App

You can build the application using Maven.

### ▶️ 1. Run the following command:

```bash
./mvnw clean package
```
This will:
- Clean any previous build artifacts
- Run all tests
- Package your application as a JAR file

⚠️ Skip Tests (Not Recommended)
If you want to build the project without running tests (e.g., during quick iterations), run:
```bash
./mvnw clean package -DskipTests
```
> ⚠️ Warning: Skipping tests may result in building a broken or unstable application. Use this only when you’re certain the code is stable.

### 📦 Run the Packaged Application

After a successful build, the JAR file will be available in the `target/` directory:

```bash
target/ecommerce-challenge-0.0.1-SNAPSHOT.jar
```
You can run it using the following command:
```bash
java -jar target/ecommerce-challenge-0.0.1-SNAPSHOT.jar
```
This will start the Spring Boot application on http://localhost:8080.