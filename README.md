# CodeFixer AI – Intelligent Java Code Analyzer (Desktop & Web Application)

CodeFixer AI is a Java-based application that analyzes Java source code using a rule-driven inspection engine. It detects common programming mistakes, performance issues, bad practices, code smells, and structural weaknesses. The system provides detailed issue reports, severity classification, and exportable results for documentation.

The project is implemented as both:
- a **Desktop GUI application (Java Swing)**, and  
- a **Web-based application using Jakarta Servlets deployed on Apache Tomcat**.

A built-in history tracker stores analysis results using **MySQL through JDBC**.

This project has been developed for academic evaluation and demonstrates key concepts including **Object-Oriented Programming, Collections, Generics, Multithreading, Synchronization, GUI development, Servlet-based web development, and Database Connectivity**.

---

## ✅ Key Features

### 🔹 Code Analysis Engine
- Rule-based static code inspection
- Detects:
  - String comparison using `==`
  - Off-by-one loop errors
  - Resource leaks
  - Empty catch blocks
  - Deep nesting
  - Magic numbers
  - String concatenation in loops
  - Unused variables
  - Missing switch defaults
  - TODO markers

### 🔹 GUI-Based Desktop Application
- Built using **Java Swing**
- Dark mode interface
- Split view (code input + analysis output)
- Buttons for:
  - Analyze Code
  - View History
  - Export Report

### 🔹 Web-Based Application (Servlet Version)
- Implemented using **Jakarta Servlets**
- Deployed on **Apache Tomcat**
- Accepts Java source code via **HTTP POST requests**
- Returns analysis results in **JSON format**
- Simple **HTML-based web interface** for browser interaction
- Reuses the same core analysis engine used by the desktop GUI

### 🔹 Severity Scoring
- HIGH / MEDIUM / LOW classification
- Professional summary formatting

### 🔹 Multithreading + Synchronization
- Analysis runs in a background thread
- GUI remains responsive during processing
- Shared result data protected using synchronized blocks

### 🔹 Database Storage (JDBC + MySQL)
- Stores analysis summaries
- Viewable history table
- DAO pattern implemented

### 🔹 Report Exporting
- Generates `.txt` report files
- Includes summary + detailed issue listing

---

## ✅ Technologies Used

| Component | Technology |
|----------|------------|
| Programming Language | Core Java |
| Desktop GUI | Swing |
| Web Technology | Jakarta Servlet |
| Web Server | Apache Tomcat |
| Database | MySQL |
| Connectivity | JDBC |
| Architecture | OOP + DAO |
| Concurrency | Threads + Synchronization |
| Collections | Lists with Generics |

---

## ✅ Project Objectives (Academic)

- Implement OOP principles (abstraction, inheritance, polymorphism, interfaces)
- Build a GUI-based Java application
- Develop a servlet-based web application
- Apply static code analysis rules
- Demonstrate use of collections and generics
- Implement multithreading in a real context
- Apply synchronization for thread safety
- Integrate MySQL using JDBC
- Store and retrieve structured data
- Demonstrate code reuse across multiple interfaces

---

## ✅ Learning Outcomes

- Understanding of Java-based desktop application development  
- Practical use of Swing for GUI design  
- Implementation of servlet-based web applications  
- Deployment and testing using Apache Tomcat  
- Real-world threading and synchronization  
- Ability to design maintainable rule engines  
- Hands-on JDBC database interaction  
- Improved analytical thinking about code quality  

---

## ✅ How to Run the Project

### 1. Install Requirements
- Java JDK 17 (or compatible)
- IntelliJ IDEA (recommended)
- Apache Tomcat 10+
- MySQL Server

---

### 2. Download or Clone the Repository

**Option A — Clone using Git**
Option B — Download ZIP

Click Code → Download ZIP

Extract the folder

3. Open the Project

Open IntelliJ IDEA

Select Open Project

Choose the CodeFixerAI folder

Project SDK: JDK 8 or later

Project Language Level: 8 or above

4. Create Database
CREATE DATABASE codefixerai;

5. Create the History Table
USE codefixerai;

CREATE TABLE analysis_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    analyzed_at DATETIME NOT NULL,
    issue_count INT NOT NULL
);

6. Configure Database Credentials

Open:

src/com/codefixerai/db/DBConnectionManager.java


Update if required:

private static final String URL = "jdbc:mysql://localhost:3306/codefixerai";
private static final String USER = "root";
private static final String PASSWORD = "yourpassword";

7. Run the Desktop Application

Navigate to:

src/com/codefixerai/main/App.java


Run the main class.

8. Run the Web Application (Servlet)

Build the project

Deploy the compiled classes and web.xml to Apache Tomcat

Place index.html in the web application root

Start Tomcat

Open in browser:

http://localhost:8080/codefixer/

✅ Author & Academic Details

Name: Divyanshu Upadhyay
Course: B.Tech
Branch: Computer Science & Engineering
University: Galgotias University
Project Title: CodeFixer AI – Intelligent Java Code Analyzer
Project Type: GUI + Servlet + OOP + Multithreading + JDBC
Year: 2025

✅ Declaration

I hereby declare that this project titled “CodeFixer AI – Intelligent Java Code Analyzer” has been developed by me as part of my academic submission. All implementation work, source code structure, and feature integrations have been carried out independently for learning and evaluation purposes.
```bash
git clone https://github.com/divyanshu238/CodeFixerAI
