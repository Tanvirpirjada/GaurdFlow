# API Abuse Prevention Service

This is a **production-ready Spring Boot backend service** that implements **API abuse prevention** using **Redis** and **Lua scripts** for atomic rate limiting. The project is Docker-ready and designed for scalability.

---

## Features Implemented So Far

- **Redis-backed rate limiting**  
  - Time window support (TTL)  
  - HTTP 429 response when rate limit exceeded  
  - `Retry-After` header for client-friendly retry guidance

- **Dockerized Redis**  
  - Runs Redis in a container for development/testing

- **Clean, production-ready code**  
  - RedisTemplate configured with JSON serialization  
  - Spring Boot service layer handling rate limiting logic  
  - Filter to enforce rate limiting across APIs

---

## Technology Stack

- Java 21 / Spring Boot 4.x  
- Redis (Docker container)  
- Maven for project management  
- IDE-friendly configurations: IntelliJ, STS, VS Code

---

## Project Structure

