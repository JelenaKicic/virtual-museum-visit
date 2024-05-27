# Virtual Museum Visits Web Application

This repository is implementation of web application for virtual visits to museums around the world, built as a faculty project. The application allows users to explore various museums virtually, providing information such as museum name, address, phone number, city, country, geolocation displayed on Google Maps, and museum type. 

The project utilizes several technologies:

- **Frontend**: Angular framework is used for the frontend development, providing a responsive and interactive user interface. 
- **Backend**: Spring framework is employed for the backend development, facilitating the creation of RESTful services to handle data management and user authentication.
- **External APIs**: External APIs are integrated into the application, including Google Maps API for displaying museum locations, OpenWeatherMap API for weather forecasts, and RSS feeds from [HuffPost Arts](https://www.huffpost.com/section/arts/feed) for displaying cultural news.
- **Payment Service**: A pseudo-banking service named bank is implemented to simulate card payments for museum visits. This service is accessed via RESTful endpoints and validates user payment information against the provided criteria.

### How to Run the Application?

1. **Backend (Spring Boot)**:
   - Clone the repository and navigate to the backend directory.
   - Configure the PostgreSQL database settings in the application.properties file.
   - Run the Spring Boot application using your preferred method (e.g., `mvn spring-boot:run` or running the main class).

2. **Frontend (Angular)**:
   - Ensure the backend Spring Boot application is up and running.
   - Clone the repository and navigate to the frontend directory.
   - Install the required dependencies by running `npm install`.
   - Start the Angular application with `ng serve`.

3. **Virtual Bank (JSP M2 Application)**:
   - Ensure the backend Spring Boot application is running.
   - Clone the repository and navigate to the bank directory.
   - Deploy the bank application to a servlet container like Apache Tomcat.
   - Access the application through the specified URL with the appropriate authorization token.

4. **Administrator CRUD (JSP Application)**:
   - Repeat the previous process for administratorCrud

### Project Structure and Functionality

The application is structured to accommodate three types of users: guests, registered users, and administrators. Each user group has specific privileges and access levels within the system:

- **Guests**: Can only access the homepage and the registration form.
- **Registered Users**: Have access to the list of available museums, search functionality, active virtual visits, and cultural news. They can also create user accounts, purchase tickets for virtual museum visits, and receive tickets via email.
- **Administrators**: Have access to the administrative section of the application, where they can manage user accounts, approve registrations, block users, reset passwords, add new museums, and schedule virtual museum visits. They can also view logs and download them in PDF format for auditing purposes.

The application follows best practices in web development, including MVC principles, client-server validation, responsive design, and dynamic interface elements using AJAX technology. It also emphasizes security measures such as token-based authorization, server-side and client-side validation of user input, and secure communication with external APIs.