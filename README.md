# TaskManager - version 1.2

This project is completed using the following technologies: Java 8, Spring Boot, Spring Data, Apache Maven, MongoDb. There are 2 entities. User has the following fields: id, username, email and password. Task has such fields as id, title, description, lastModifiedDate, author and list of users, who also have access to this task.
The following security type is JWT-based. 

## The following API:
- Register in a system;
- Login into the system;
- Add new task;
- Edit task;
- Share the task with another user in the system
- See list of tasks 
- Delete task;


## How to run this project?
1) Download the project
2) Import it in your favourite IDE
3) Run a class TaskManager
4) Import TaskManager_v_2.postman_collection.json to Postman
5) Test it

At the start of the application the database is empty. So it is recommended to register at least 2 users and test project in offered order.
