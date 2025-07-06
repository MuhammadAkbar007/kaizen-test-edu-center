# Education center project
## What technologies should be used in the project?
 * Java programming language,
 * any relational database,
 * Spring Boot library. 

### Also, Spring Boot requires at least the use of 
 * Spring Data JPA,
 * Lombok,
 * DB-Driver,
 * Security,
 * Auditing,
 * Cascade concepts.
#### I added:
 * jwt
 * mapstruct

## What is the project requirement? 
Management of students, teachers and groups of students of the education center.

## What will happen in the project? 
Groups Teachers and Students.
Admin adds teachers and also adds groups. When a group is added, a teacher is assigned to it. 
Adding a student is done by Admin or teacher, when adding a student, he is assigned to a group.
Admin can add a student to any group, but a teacher can only add students to groups to which he is assigned.
Admin can see all teachers, students and groups. Teachers can only see their own groups. Students should only see their own groups.

## What are the conditions? 
Create the group teacher and student entities and do full CRUD of them.
Write the appropriate fields for the group teacher and student entities.

## Tasks
 - [x] implement Security
 - [x] draw entity chart
 - [x] implement JpaAuditing
 - [-] data seeder for admin
 - [ ] add swagger & its cofig
 - [ ] write README.md
  - include chart.png reference

