# 🎓 Education Center Project
Test project for Kaizen internship pool. A simple CRUD-based education management system built with **Java ☕️** and **Spring Boot 🌱**,
handling the administration of **Students**, **Teachers**, and **Groups**.

---

## 🛠 Technologies Used

- ☕ Java 17+
- 🧱 Spring Boot
- 📦 Spring Data JPA
- 🔐 Spring Security (with JWT)
- 🧬 Lombok
- 📝 MapStruct
- 📡 PostgreSQL (with JDBC driver)
- 🕵️‍♂️ JPA Auditing
- 🔁 Cascade operations

> ✅ **Extras**:  
> 🔐 `JWT` for stateless authentication  
> 🧩 `MapStruct` for DTO mapping

---

## 📋 Project Requirements

Management of **students**, **teachers**, and **groups** within an education center. 

### 🎯 Functional Highlights

- **Admin** can:
  - Add/edit/delete/view **teachers**, **students**, **groups**
  - Assign teachers to groups
  - Add students to **any** group

- **Teacher** can:
  - Add students only to **their own** groups
  - View **only their** assigned groups

- **Student** can:
  - View **their own** group(s)

---

## 📦 Entity Overview

Entities created:
- 👩‍🏫 `Teacher`
- 👨‍🎓 `Student`
- 🧑‍🤝‍🧑 `Group`
- 👤 `User` (for authentication & roles)

> 📊 Entity relationship diagram included:  
> 👉 [chart 🖼](src/main/resources/entity-chart.png)

> ⛓️Some api samples as http format to try out:  
> 👉 [http 📂](src/main/resources/http/)

---

## ✅ Completed Tasks

| # | Task |
|--|------|
| 1 | ✅ Implement Security (JWT) |
| 2 | ✅ Draw Entity Chart |
| 3 | ✅ Set up JPA Auditing |
| 4 | ✅ Data seeder for default admin |
| 5 | ✅ Admin adds teacher |
| 6 | ✅ Global exception handling (`@ControllerAdvice`) |
| 7 | ✅ Admin adds group ➕ assigns teacher |
| 8 | ✅ Admin adds student ➕ assigns to **any group** |
| 9 | ✅ Teacher adds student ➕ assigns to **own group** |
| 10 | ✅ Admin views all teachers |
| 11 | ✅ Admin views all students |
| 12 | ✅ Admin views all groups |
| 13 | ✅ Teacher views **their** groups |
| 14 | ✅ Student views **own** groups |
| 15 | ✅ Student CRUD |
| 16 | ✅ Teacher CRUD |
| 17 | ✅ Group CRUD |
| 18 | 🚧 Write `README.md` |

---

## 🚀 Getting Started

1. Clone the repository
2. Set up your database (PostgreSQL recommended)
3. Configure DB credentials in `application.properties`
4. Run the project using your IDE or `./mvnw spring-boot:run`
5. Import HTTP request collection from `/resources/http/` for testing

---

## ✍️ Author
Created by [Akbar](https://github.com/MuhammadAkbar007).
