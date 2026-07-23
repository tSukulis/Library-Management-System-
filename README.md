# Library Management System

## Overview
A console-based Library Management System written in Java. It allows users to manage books, members, and loans, with JSON persistence using Jackson.

## Features
- Add and delete books
- Add and delete members
- Borrow and return books
- Search books by title
- Search members by name
- Display active, returned, and overdue loans
- Sort books and members
- Display library statistics
- Automatic JSON loading and saving

## Technologies
- Java 21
- Maven
- Jackson Databind
- JUnit 5

## Project Structure
- Book
- Member
- Loan
- BookManager
- MemberManager
- LoanManager
- DataLoader
- DataStore
- DataDisplay
- Menu

## Requirements
- Java 21
- Maven

## Running the project

```bash
git clone <repository-url>
cd LibraryManagementSystem
mvn clean compile
mvn exec:java
```

## Running the tests

```bash
mvn test
```

## Example

```
########## Library Menu ##########
1. Display books
2. Display members
3. Display loans
...
20. Exit
```

![Java](https://img.shields.io/badge/Java-21-blue)
![Maven](https://img.shields.io/badge/Maven-Build-red)
![JUnit](https://img.shields.io/badge/Tests-JUnit%205-green)
