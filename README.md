# Civic Connect — Smart Municipal & Community Engagement Platform

A production-grade Civic Complaint Management and Governance platform built for final-year project demonstration, municipal administration, and citizen engagement.

---

## 🚀 Tech Stack Overview

| Layer | Technology | Usage & Purpose |
| :--- | :--- | :--- |
| **Frontend** | HTML5, CSS3, Bootstrap 5 | Responsive UI design, modals, cards, badges |
| **Frontend Map** | Leaflet.js + OpenStreetMap | Interactive GIS map view with geotagged status pins |
| **Frontend Charts** | Chart.js | Executive Admin KPI analytics (Category breakdown bar chart, status pie chart) |
| **Frontend Logic** | Vanilla JavaScript (ES Modules) | Client-side interactions, form validation, timeline rendering |
| **Frontend API** | Axios | REST HTTP Client & Dual-mode local state adapter |
| **Backend** | Java 17 + Spring Boot 3.3.4 | REST API Backend framework |
| **Security** | Spring Security + JWT (JJWT) + BCrypt | Token-based auth, role-based access control (RBAC) |
| **Database** | MySQL (or H2 in-memory) | Data persistence for Users, Departments, Wards, Complaints, Timelines |
| **ORM** | Spring Data JPA + Hibernate | Relational entity mappings & custom query execution |

---

## 🌟 Implemented Features

### 🟢 1. Core Must-Have Features
1. **User Registration & Login**: Account creation for **Citizen**, **Officer**, and **Admin** personas.
2. **JWT Authentication & Security**: Secure token authorization and password hashing using BCrypt.
3. **Role-Based Access Control (RBAC)**: Distinct dashboards and permissions for each role.
4. **Geotagged Complaint Submission**: Pin exact latitude/longitude coordinates using Leaflet map picker, select category, add description, upload photo proof.
5. **Auto Department & Ward Assignment**: Complaints automatically route to appropriate municipal department (Roads, Sanitation, Electrical, Water) and ward.
6. **SLA Deadlines & Countdown**: Category-based resolution targets (e.g. Water leak = 24h, Pothole = 48h, Streetlight = 72h).
7. **Officer Workstation Queue**: Officers accept assigned tasks, update progress status (`ASSIGNED` $\rightarrow$ `IN_PROGRESS` $\rightarrow$ `RESOLVED`), and log progress notes.
8. **Citizen Verification & Reopen Workflow**: Citizens can click "Confirm Resolution" to close an issue or "Reopen" if work was inadequate.
9. **Admin Department Overrides**: Reassign departments or officers and monitor overdue SLA escalations.

### 🟡 2. Impressive Visual Features
1. **Leaflet GIS Map**: Interactive GIS map displaying complaints with color-coded status pins:
   - 🔘 Grey: Submitted
   - 🔵 Cyan: Assigned
   - 🟡 Yellow: In Progress
   - 🟢 Green: Resolved / Closed
2. **Visual Step-by-Step Timeline (Feature 2B)**: Complete accountability history with timestamps, status nodes, updater names, and comments:
   ```
   ● SUBMITTED (Citizen John Doe) - 10 Sept, 10:30 AM
   ● ASSIGNED (Admin Super) - 10 Sept, 10:35 AM
   ● IN_PROGRESS (Officer Alex Smith) - 10 Sept, 12:00 PM
   ● RESOLVED (Officer Alex Smith) - 11 Sept, 03:30 PM
   ```
3. **Admin KPI Dashboard & Chart.js Analytics (Feature 2C)**: 4 stat KPI cards (Total, Pending, In Progress, Resolved) and interactive visual charts (Category Distribution Bar Chart, Status Doughnut Chart).

---

## 🏃 How to Run the Application

### Option A: Standalone Live Demo Mode (Instant Zero-Config Setup)
1. Open `D:\civic-connect\src\main\resources\static\index.html` directly in any web browser.
2. Click **"Quick Demo Login"** on the home page:
   - **Citizen**: `citizen_john` / `password123`
   - **Officer**: `officer_smith` / `password123`
   - **Admin**: `admin_super` / `password123`
3. All feature workflows (submit complaints, pick GPS pins, view timeline, mark resolutions, view Chart.js charts) work interactively out of the box!

---

### Option B: Full Spring Boot 3.3.4 + MySQL Backend Setup

#### 1. MySQL Database Setup
Open **MySQL Workbench** or MySQL CLI and create the database schema:
```sql
CREATE DATABASE civic_connect_db;
```

#### 2. Configure `application.properties`
Verify credentials in `D:\civic-connect\src\main\resources\application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/civic_connect_db?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
```

#### 3. Build & Run Maven Project
Open terminal inside `D:\civic-connect` and execute:
```bash
mvn clean package
java -jar target/civic-connect-1.0.0-SNAPSHOT.jar
```
The REST API server will launch at **`http://localhost:8080`**.
