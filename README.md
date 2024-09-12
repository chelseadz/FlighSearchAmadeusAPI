# FlighSearchAmadeusAPI
Using Amadeus REST API to implement a fight search app

## Table of Contents
1. [Technologies Used](#technologies-used)
2. [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
3. [Running the Application](#running-the-application)


## Technologies Used

- **Frontend**: [React]
- **Backend**: [Spring Boot]
- **Containerization**: Docker, Docker Compose
- **Package Management**: npm / yarn

## Getting Started

### Prerequisites

1. Make sure you have the following tools installed on your machine:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/install/)

2. In /backend/FlightSearch/src/main/resources/application.properties add you client credentials for Amadeus API

### Installation

1. Clone the repository
2. Build and run the application using Docker Compose:
   ```bash
   docker-compose up --build
   
This will start the application:

* The frontend will be available at http://localhost:3000
* The backend will be available at http://localhost:8080

## Running the Application
Navigate to http://localhost:3000 to access the UI in Google Chrome (doesn't work well in edge).

