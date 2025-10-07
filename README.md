
Full-Stack To-Do Application

⦁	This is a full-stack to-do application built with Spring Boot, ReactJS, and MySQL. The entire application stack is containerized using Docker Compose for easy setup and deployment.

Project Architecture

⦁	The application is structured as a multi-container microservice application comprising three services:
⦁	Frontend: A ReactJS application that serves as the user interface.
⦁	Backend: A Spring Boot application that provides RESTful APIs.
⦁	Database: A MySQL database to store to-do tasks.
⦁	The services communicate over a private Docker network.

Prerequisites

⦁	To build and run this project, you need to have the following installed on your machine:

1.	Git: For cloning the repository.
2.	Docker Desktop: Includes Docker Engine and Docker Compose, which are essential for containerizing the application.

Getting Started

⦁	Follow these instructions to get a copy of the project up and running on your local machine.

1.	Clone the Repository
⦁	  First, clone the project from its repository to your local machine:
  
  	git clone <your-repository-url>
  	cd todo-app

2. Run the Application with Docker Compose
⦁	  This is the only command you need to build the images and run all three services (frontend, backend, and database) at once.
  
⦁	  Note: Before running the command, ensure no other services are running on ports 8080 or 9100 on your machine.
  
⦁	  Open your terminal in the root directory of the project and execute the following command:
  
  	docker compose up --build
  
⦁	The first time you run this command, Docker will download the necessary base images for MySQL, OpenJDK, and Node.js. This may take a few minutes.

3. Access the Application
⦁	  Once the command has finished and all containers are running, you can access the application at the following URLs:
  
 	 Frontend: http://localhost:8080
  
  	Backend API: http://localhost:9100
  
⦁	  You can also view the logs of each service in your terminal to monitor their status. To stop the application, press Ctrl + C in the terminal.

4. Stopping the Services
⦁	  To stop and remove all containers, networks, and volumes created by docker compose up, run the following command:
  
  	docker compose down
  
⦁	  This ensures a clean shutdown of your local environment.

Technology Stack:

1.	  Backend: Spring Boot 3 (Java)
2.	  Frontend: ReactJS, TypeScript, Tailwind CSS
3.	  Database: MySQL
4.	  Containerization: Docker, Docker Compose
