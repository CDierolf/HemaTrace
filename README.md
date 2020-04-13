# HemaTrace

The HemaTrace application is an application designed for use by Emergency Medical Service providers (specifically those that carry blood products) to aid in documentation compliance in regard to the use and transportation of blood products in the emergency setting.

# Setup

This project utilizes Maven as the build tool and the provided pom.xml will provide the required dependencies to run the application. 

# Versions
HemaTrace utilizes Java 13 and JavaFX 11

# Database
A MSSQL database was used in the creation of this project. You may use the provided SQL scripts to build the HemaTrace schema or use the ERD to create it with a different database. Modify the database.props.properties file with the connection string, username, password, and port to connect. 
The application utilizes JDBC to communicate with the database.


