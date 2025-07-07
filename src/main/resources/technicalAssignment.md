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
 1. - [x] implement Security
 2. - [x] draw entity chart
 3. - [x] implement JpaAuditing
 4. - [x] data seeder for admin
 5. - [x] admin add teacher
 6. - [x] global exception handler - controller advice
 7. - [x] admin add group `with assigning a teacher`
 8. - [x] admin add student `with assigning to a group` *any group*
 9. - [x] teacher add student `with assigning to a group` *his/her group*
 10. - [x] admin see all teachers
 11. - [x] admin see all students
 12. - [x] admin see all groups
 13. - [x] teacher see their groups
 14. - [x] student see his/her groups
 15. - [x] student CRUD
 16. - [x] teacher CRUD
 17. - [x] group CRUD
 18. - [ ] write README.md
  - include chart.png reference
  - include resources/http folder reference

