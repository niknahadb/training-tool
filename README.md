# Training Tool

An easy-to-use web platform created for CodeLab at UC Davis to organize, deliver, and track educational content more effectively than before.

Website now live at https://toolkit.codelabdavis.com/

## Table of Contents

- [Technology Stack](#technology-stack)
- [System Design](#system-design)
- [Key Features](#key-features)
- [Lessons Learned](#lessons-learned)

## Technology Stack

- **Backend:** Java, Spring Boot, Hibernate, PostgreSQL
- **Frontend:** React, TypeScript
- **File Storage:** AWS S3 and CloudFront for hosting videos and documents
- **Tools:** Postman for API testing, Docker for containerization

## System Design

The application’s database includes tables for cohorts, teams, users, courses, modules, lessons, and progress records.
Key relationships are enforced using Spring Boot and Hibernate constructors, ensuring data integrity when creating or deleting entities. Copy constructors allow easy duplication of course content.

### Authentication & Authorization

- Users log in with JWT tokens that include their role (board member, project manager, or associate).
- Board members can manage all content, project managers assign courses, and associates view and complete lessons.

### Main Pages

1. **Login Page:** Branded sign-in screen with role-based access control
2. **PM Dashboard:** List team members and assign courses; displays progress for each assignment
3. **Board Dashboard:** Create and manage cohorts, teams, and course collections at scale
4. **Course Viewer:** Side menu for navigating modules and lessons; text display or video streaming from S3

## Key Features

- Role-based access control
- Hierarchical course structure with modules and lessons
- Automatic progress tracking for each user
- Video streaming via AWS S3 and CloudFront
- Ability to clone courses, modules, and lessons
- RESTful API with powerful filtering options in Spring Data JPA
   
## Lessons Learned

Building this tool improved my skills in full-stack development, particularly integrating React with Spring Boot and managing database relationships. I also got to learn best practices for collaboration, declaring scope, and content versioning.
