# 🛒 Order & Inventory Microservice System

This project demonstrates a Spring Boot-based microservice architecture with synchronous communication using Feign clients.

---

## 📦 Microservices

### 1️⃣ Inventory Service (`inventory-service`)
- Manages product stock levels
- Exposes `/inventory/check` to validate availability

### 2️⃣ Order Service (`demo`)
- Handles item creation and retrieval
- Uses Feign to call Inventory Service before saving an item

---

## 🔄 Communication Flow

Order Service (Feign Client) │ └───▶ Inventory Service /inventory/check?product=fridge&qty=2

# Architecture Diagram

+-------------------+        Feign Client        +----------------------+
|                   |  ───────────────────────▶ |                      |
|  Order Service    |                           |  Inventory Service   |
|  (demo)           | ◀───────────────────────  |  (inventory-service) |
|                   |        Stock Response     |                      |
+-------------------+                           +----------------------+

User → /items (POST) → Order MS → checks stock → Inventory MS
     ← 400 if out of stock, else saves item


---

## 🚀 How to Run

```bash
# Inventory Service
cd inventory-service
mvn spring-boot:run -DskipTests

# Order Service
cd demo
mvn spring-boot:run -DskipTests

🧠 Tech Stack
Spring Boot

Spring Cloud OpenFeign

REST APIs

Maven

