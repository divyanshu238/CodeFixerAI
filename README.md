# CodeFixer AI – Intelligent Java Code Analyzer (Desktop Application)

CodeFixer AI is a Java-based desktop application that analyzes Java source code using a rule-driven inspection engine. It detects common programming mistakes, performance issues, bad practices, code smells, and structural weaknesses. The system provides detailed issue reports, severity classification, and exports results for documentation. A built-in history tracker stores analysis results using MySQL through JDBC.

This project has been developed for academic evaluation and demonstrates key concepts including Object-Oriented Programming, Collections, Generics, Multithreading, Synchronization, GUI development using Swing, and Database Connectivity.

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

### 🔹 Severity Scoring
- HIGH / MEDIUM / LOW classification
- Professional summary formatting

### 🔹 Multithreading + Synchronization
- Analysis runs in background thread
- GUI remains responsive
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
| GUI Toolkit | Swing |
| Database | MySQL |
| Connectivity | JDBC |
| Architecture | OOP + DAO |
| Concurrency | Threads + Synchronization |
| Collections | Lists with Generics |

---

## ✅ Project Objectives (Academic)

- Implement OOP principles (abstraction, inheritance, polymorphism, interfaces)
- Build a GUI-based Java application
- Apply static code analysis rules
- Demonstrate use of collections and generics
- Implement multithreading in a real context
- Apply synchronization for thread safety
- Integrate MySQL using JDBC
- Store and retrieve structured data

---

## ✅ Learning Outcomes

✅ Understanding of Java-based desktop application development  
✅ Practical use of Swing for UI  
✅ Real-world threading and synchronization  
✅ Ability to design maintainable rule engines  
✅ Hands-on JDBC database interaction  
✅ Improved analytical thinking about code quality

---

## ✅ How to Run the Project

### 1. Install Requirements
- Java JDK 17 (or compatible)
- IntelliJ IDEA (recommended)
- MySQL Server

### 2: Download or Clone the Repository
Option A — Clone using Git
git clone https://github.com/divyanshu238/CodeFixerAI

Option B — Download ZIP
Click Code → Download ZIP
Extract the folder

### 2: Open the Project 
 - Open Project
 - Choose the CodeFixerAI folder
 - Project SDK: JDK 8 or later
 - Project Language Level: 8 or above

### 3. Create Database
CREATE DATABASE codefixerai;

### 4: Create the History Table
Run this inside the database:

USE codefixerai;

CREATE TABLE analysis_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    analyzed_at DATETIME NOT NULL,
    issue_count INT NOT NULL
);

### 5: Configure DB Credentials
Open:
src/com/codefixerai/db/DBConnectionManager.java
Update if needed:

private static final String URL = "jdbc:mysql://localhost:3306/codefixerai";
private static final String USER = "root";
private static final String PASSWORD = "yourpassword";


### 6: Run the Application
Navigate to:
src/com/codefixerai/main/App.java

---

## ✅ Author & Academic Details

**Name:** Divyanshu Upadhyay  
**Course:** B.Tech  
**Branch:** Computer Science & Engineering  
**University:** Galgotias University  
**Project Title:** CodeFixer AI – Intelligent Java Code Analyzer  
**Project Type:** GUI + OOP + Multithreading + JDBC  
**Year:** 2025  

## ✅ Declaration

I hereby declare that this project titled “CodeFixer AI – Java Code Analyzer” has been developed by me as part of my academic submission. All implementation work, source code structure, and feature integrations have been carried out independently for learning and evaluation purposes.
