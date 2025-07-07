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
 5. - [-] admin add teacher
 6. - [ ] admin add group `with assigning a teacher`
 7. - [ ] admin add student `with assigning to a group` *any group*
 8. - [ ] teacher add student `with assigning to a group` *his/her group*
 9. - [ ] admin see all teachers
 10. - [ ] admin see all students
 11. - [ ] admin see all groups
 12. - [ ] teacher see their groups
 13. - [ ] student see his/her groups
 14. - [ ] student CRUD
 15. - [ ] teacher CRUD
 16. - [ ] group CRUD
 17. - [ ] add swagger & its cofig
 18. - [ ] write README.md
  - include chart.png reference

