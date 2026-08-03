# 🛒 Second-Hand Marketplace

An end-to-end, Java-based desktop marketplace application that enables users to list, discover, buy, and sell pre-owned items within a structured, database-driven platform.

---

## 🛠️ Tech Stack & Dependencies

*   **Language:** Java (JDK 17 or higher recommended)
*   **Database:** MySQL Server
*   **Database Connector:** MySQL Connector/J (`mysql-connector-j-9.6.0.jar`)
*   **Architecture:** Model-View-Controller (MVC) / DAO Pattern
*   **Documentation:** UML Class & Use-Case Diagrams, System Reports

---

## 🚀 Key Features

*   **User Management:** Account creation, login, authentication, and profile tracking.
*   **Item Management:** List second-hand goods with titles, descriptions, categories, and prices.
*   **Marketplace Search & Filter:** Browse active listings by category or key terms.
*   **Transaction Processing:** Simulated purchasing workflow linking buyers, sellers, and listings.
*   **Relational Data Persistence:** Structured MySQL backend ensuring transactional integrity.

---

## 📂 Directory Structure

```text
Second-Hand-Marketplace/
├── database-schema.sql    # SQL initialization script for MySQL tables & constraints
├── diagrams/              # System architecture visual guides
│   ├── class-diagram.png  # Application UML Class Diagram
│   └── use-case.png       # User interaction Use-Case Diagram
├── docs/                  # Detailed documentation
│   └── report.md          # Technical design & development report
├── lib/                   # External Java dependencies & JARs
│   └── mysql-connector-j-9.6.0.jar
└── src/                   # Core Java source files (Models, DAOs, Services)

```

---

## ⚡ Quick Start & Installation

1. Database Setup
- Open your MySQL client or command-line terminal.
- Create and initialize the database schema using the included SQL script:
```bash
mysql -u root -p < database-schema.sql
```


2. Configure Database Credentials
Ensure your database connection string, username, and password in your Java application configuration match your local MySQL settings:
- Host: localhost:3306
- Driver: com.mysql.cj.jdbc.Driver


3. Build & Run
Compile the Java source files while linking the JDBC library in lib/:
- Linux/macOS:
```bash
javac -cp "lib/mysql-connector-j-9.6.0.jar:." -d bin src/**/*.java
java  -cp "lib/mysql-connector-j-9.6.0.jar:bin" Main
```

- Windows (Command Prompt):
```bash
javac -cp "lib/mysql-connector-j-9.6.0.jar;." -d bin src\*.java
java  -cp "lib/mysql-connector-j-9.6.0.jar;bin" Main
```

---

## 📊 System Diagrams & Documentation
For a deeper dive into the system's architectural design and database relationships:

- Check out the visual UML representations in the diagrams/ folder.
- Read the full technical breakdown in docs/report.md.
