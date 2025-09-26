# 🍿 PopCorn – Movie Ticket Booking System (Microservices)

**PopCorn** is a microservice-based online movie ticket booking platform. Users can browse movies, select theaters, book seats, make payments, and receive e-tickets via email—all in a secure and scalable architecture.

Built by:  
- Sayedur Rahman Rifaf (210042118)  
- Md. Hasibur Rahman Alif (210042107)  
- Mohtasim Dipto (210042175)
  


---

## 🌟 Features

### For Regular Users:
- Browse current & upcoming movies
- View detailed movie info (cast, plot, release date)
- Select city and theater
- Book and pay for tickets securely
- Receive e-ticket via email after payment
- Write movie reviews (requires account)

### For Admins:
- Add/edit movies, actors, and directors
- Manage system access via role-based permissions

---

## 🏗️ System Architecture

PopCorn is built using **5 independent microservices**, each with a dedicated responsibility:

| Service         | Role | Tech Used |
|-----------------|------|-----------|
| **Eureka Server** | Service registry & discovery (the "phone book") | Spring Cloud Netflix Eureka |
| **API Gateway**   | Single entry point; routes requests to services | Spring Cloud Gateway |
| **User Service**  | Handles user registration, login, roles, and JWT auth | MongoDB, Spring Security, JWT |
| **Movie Service** | Manages movies, theaters, bookings, and payments | PostgreSQL (Neon), WebFlux, Kafka |
| **Email Service** | Sends ticket confirmation emails | Kafka, FreeMarker, JavaMailSender |

> ✅ All services are built with **Spring Boot (Java 17)** and communicate via **REST (WebFlux)** and **Apache Kafka**.

---

## 🧰 Technology Stack

| Layer      | Technologies |
|------------|--------------|
| **Backend** | Java 17, Spring Boot, Spring Cloud, Spring WebFlux, Spring Security, JWT |
| **Databases** | MongoDB (User Service), PostgreSQL/Neon (Movie Service) |
| **Messaging** | Apache Kafka |
| **Frontend** | React, JavaScript, Bootstrap |
| **DevOps** | Eureka (Service Discovery), API Gateway (Routing & Load Balancing) |

---

## 🚀 How It Works (User Flow)

1. User visits the **React frontend**.
2. All requests go through the **API Gateway**.
3. To book a ticket:
   - Gateway routes to **Movie Service**.
   - Movie Service validates user via **User Service** (using JWT).
   - On successful payment, Movie Service publishes a **Kafka message**.
   - **Email Service** consumes the message and sends a formatted ticket email.
4. Admins can log in and manage content via protected endpoints.

---


---

## ⚙️ How to Run Locally

> **Prerequisites**: Java 17, Maven, Docker (for Kafka & databases), Node.js (for frontend)

### 1. Start Infrastructure
```bash

# Start PostgreSQL (Neon or local) and MongoDB
# Ensure connection strings match application.yml in each service

###. Launch Microservices (in order)

# Terminal 1
cd eureka-server && mvn spring-boot:run

# Terminal 2
cd api-gateway && mvn spring-boot:run

# Terminal 3
cd user-service && mvn spring-boot:run

# Terminal 4
cd movie-service && mvn spring-boot:run

# Terminal 5
cd email-service && mvn spring-boot:run

3. Run Frontend

cd popcorn-frontend
npm install
npm start
