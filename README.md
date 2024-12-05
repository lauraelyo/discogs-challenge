# Discogs API Integration Challenge

## Overview

This application integrates with the **Discogs API** to fetch information about artists, save their details, and perform comparisons between artists. The application is built using **Spring Boot** and uses **PostgreSQL** as the database for storing the relevant information.

The endpoints allow users to:
- Search for artists with Discogs API.
- Save artist information to the database.
- Retrieve detailed artist information.
- Compare multiple artists based on specific criteria.

## Requirements

1. **Java 17+**: Ensure that you have Java 17 or a higher version installed.
2. **Maven**: For managing dependencies.
3. **PostgreSQL**: The database is PostgreSQL. Make sure it is installed and running.
4. **Discogs API Token**: An API token from Discogs is required for making requests to their API.

## Setting Up the Application

### 1. Install PostgreSQL
If you haven't already, install PostgreSQL and set up a database for this application.

1. Install PostgreSQL: [PostgreSQL Download](https://www.postgresql.org/download/)
2. Create a new database for the application:

```sql
   CREATE DATABASE discogs_db;
```

### 2. Set up Discogs API Token
   To interact with the Discogs API, you need to create an account and obtain an API token.

1. Go to Discogs API Developer Portal and log in.
2. Create a new application to get your API token.
3. Copy your API Token.

### 3. Configure Application Properties
   In the src/main/resources/application.properties file, configure the PostgreSQL connection and the Discogs API Token:

```properties
spring.application.name=discogs-client
discogs.apiToken=your_discogs_api_token

#database configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/discogs_db
spring.datasource.username=your-db-username
spring.datasource.password=your-db-password
```
Replace `your_db_username`, `your_db_password`, and `your_discogs_api_token` with your actual values.

### 4. Database Schema

The schema is automatically managed by Spring Data JPA based on the entities you have in the application.

## Running the Application
### 1. Build the Application
   You can build the project with:

```bash
mvn clean install
```
### 2. Run the Application
   You can run the application using the following command:

```bash
mvn spring-boot:run
```

After starting the application, it will be accessible at `http://localhost:8080`.

## Endpoints
### 1. Search for Artist

   * Endpoint: `/api/artist/search`
   * Method: `GET`
   * Parameters:
     * `artistName` (required): The name of the artist to search for.

Example request:

```
GET http://localhost:8080/api/artist/search?artistName=Nirvana
```

Returns a list of `ArtistDTO` objects.

### 2. Save Artist and Related Information
   * Endpoint: /api/artist/save
   * Method: POST 
   * Parameters:
     * id (required): The ID of the artist to save. 
     * page (optional, default: 1): The page number for paginated results. 
     * per_page (optional, default: 20): Number of items per page.

   Example request:

```
POST http://localhost:8080/api/artist/save?id=12345&page=1&per_page=20
```

This will save the artist and related information (such as releases and other metadata) to the database. The use of pagination is to avoid the error `too many requests`

### 3. Get Artist with Related Information
   * Endpoint: /api/artist/details 
   * Method: GET 
   * Parameters:
     * artistId (required): The ID of the artist to retrieve.

   Example request:

```
GET http://localhost:8080/api/artist/details?artistId=1
```

This will return the Artist object with related information like releases retrieving this info from database.

### 4. Compare Artists
   * Endpoint: /api/artist/compare 
   * Method: POST 
   * Body:
     * A list of artistIds (required) to compare.

   Example request body:

```json
[1, 2]
```

Example request:

```
POST http://localhost:8080/api/artist/compare
```

This will compare the artists based on their releases, genres, styles, and other available attributes.

