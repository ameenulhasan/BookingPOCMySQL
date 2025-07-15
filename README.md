# 🏨 Booking System – Spring Boot + MySQL POC

This is a Proof of Concept (POC) project implementing a basic **Booking Management System** using **Spring Boot** with **MySQL**.

---

## 🚀 Features

- RESTful CRUD APIs for bookings
- Store booking info in MySQL
- Clean layered architecture: Controller → Service → Repository
- Uses Spring JPA for database operations

---

## 🔧 Technologies Used

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Lombok
- Maven

---

## 🗂️ Folder Structure

BookingPOCMySQL/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com.ameen.bookingTicket/
│ │ │ ├── controller/
│ │ │ ├── dto/
│ │ │ ├── model/
│ │ │ ├── repository/
│ │ │ ├── response/
│ │ │ ├── service/
│ │ │ └── serviceImpl
│ │ └── resources/
│ │ └── application.properties
│ └── test/
├── pom.xml
└── README.md

---

## ⚙️ MySQL Configuration

Set this in your `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookingdb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

🔌 Sample API Endpoints

➕ Create Booking
POST /create
{
  "bookingNo": 102570043,
  "sendPlace": "Chennai",
  "receivePlace": "Europe",
  "userId": 90007235,
  "flightId": 20014890
}

📄 Get Download Users Details Status
GET / downloadUsers

📄 Get Download Flights Details Status
GET /downloadFlights

📄 Get Download Booking Details Status
GET / downloadBooking

📝 Upload Details Booking
POST /upload

▶️ Running the App
    1.Clone the repository
    2.Configure MySQL DB credentials
    3.Run using:
        ./mvnw spring-boot:run
    4.Test API using Postman

📫 Contact
Maintained by Ameenul Hasan
Ping me for feedback, suggestions or improvements ✌️

---
