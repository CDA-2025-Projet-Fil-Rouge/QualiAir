# 🌍 QualiAir – Air Quality & Environmental Data Platform

**QualiAir** is a full-stack Spring Boot application designed to collect, process, and serve environmental and meteorological data (air quality, forecasts, population statistics, etc.) for French communes, departments, and regions.  
It integrates several APIs (Atmo-France, OpenWeather) and exposes a set of REST endpoints to visualize, analyze, and export environmental information.

---

##  Overview

QualiAir provides a unified back-end for environmental monitoring and forecasting:
- Aggregates **air quality** and **meteorological** data from external sources (Atmo France & OpenWeather APIs)
- Stores and serves **population statistics** and **geographical entities**
- Offers **historical exports** (CSV)
- Supports **user favorites**, **forum discussions**, and **alerts**
- Implements **scheduling**, **data caching**, and **JWT-based authentication**

It is intended for use as the backend of a data visualization dashboard or open environmental portal.

---

## ️ Tech Stack

| Layer | Technology |
|-------|-------------|
| **Backend** | Spring Boot 3 (Java 21) |
| **Database** | JPA / Hibernate (configurable) |
| **Security** | Spring Security with JWT authentication |
| **API Integration** | OpenWeather API, Atmo France API |
| **Data Import** | Custom CSV parser (`RecensementParserServiceImpl`) |
| **Build Tool** | Maven |
| **Testing** | JUnit 5, Mockito |
| **Documentation** | Swagger / OpenAPI |
| **Deployment** | Packaged as a standalone Spring Boot JAR |

---

## 🗂 Project Structure (simplified)
```
src
├── main
│ ├── java/fr/diginamic/qualiair
│ │ ├── annotation # Custom annotations
│ │ ├── builder # CSV export builders
│ │ ├── config # Spring configuration (CORS, Swagger, etc.)
│ │ ├── controller # REST controllers (API endpoints)
│ │ ├── dao # DAO classes for low-level persistence
│ │ ├── dto # Data Transfer Objects (Atmo, OpenWeather, etc.)
│ │ ├── entity # JPA entities
│ │ ├── enumeration # Enums (pollutants, scopes, etc.)
│ │ ├── exception # Custom exception hierarchy
│ │ ├── factory # Entity factories
│ │ ├── mapper # DTO <-> Entity mappers
│ │ ├── parser # CSV parser utilities
│ │ ├── repository # Spring Data JPA repositories
│ │ ├── scheduler # Scheduled API jobs (OpenWeather, Atmo France)
│ │ ├── security # JWT Auth & Spring Security setup
│ │ ├── service # Business services and logic
│ │ ├── utils # Helper utilities (dates, strings, etc.)
│ │ └── validator # Input & entity validators
│ └── resources
│ ├── application.properties # Spring Boot configuration
│ ├── *.properties # API keys, mail config, etc.
│ ├── *.sql # Sample data scripts
│ ├── static # Static web resources (if any)
│ └── templates # Thymeleaf or mail templates
└── test
└── java/fr/diginamic/qualiair
├── entity # Entity unit tests
├── service # Service layer tests
└── utils # Utility tests
``` 
---

##  Main Functional Areas

###  API Layers
- **Atmo France API Integration**  
  Retrieves official air quality indices and pollutants data.

- **OpenWeather API Integration**  
  Collects meteorological forecasts (5-day, 16-day, and current weather).

### Data Management
- Entities: `Commune`, `Departement`, `Region`, `Coordonnee`
- Population and forecast data saved as `MesurePopulation`, `MesureAir`, and `MesurePrevision`
- Import CSV data via `RecensementParserServiceImpl`

### Caching
- Custom cache system (`CacheServiceImpl`) to prevent redundant lookups and inserts (used in `findOrCreate` methods).

### 📈 Historization & Export
- Generate historical air, population, and forecast datasets
- Export as CSV via `CsvServiceImpl`

### User Features
- Favorite communes
- Alerts & notifications
- Forum discussions
- Authentication with JWT

---

## Testing

The project uses **JUnit 5** and **Mockito**.  
Tests are organized by layer:
- `src/test/java/fr/diginamic/qualiair/service` → service-level logic
- `src/test/java/fr/diginamic/qualiair/utils` → utility and parsing tests
- `src/test/java/fr/diginamic/qualiair/entity` → JPA entity validation

To run tests:
```bash
mvn clean test
```

## Running the Application
### Prerequisites

- Java 21+

- Maven 3.9+

- A configured database (e.g. PostgreSQL or H2 for dev)

- Optional: API credentials in openweather.properties and atmo.properties

### Start the app
`mvn spring-boot:run`

### Then access:
`http://localhost:8080/swagger-ui/index.html`

## Quick Start (Developer Setup)

* Clone the repository
```bash
git clone https://github.com/yourusername/QualiAir.git
cd QualiAir
```
* Build the project

`mvn clean package`
* run the application

`export APPLICATION_PROFILE=dev` 

`mvn sprint-boot:run -Dspring-boot.run.profile=${APPLICATION_PROFILE}`
* Access Swagger UI

`http://localhost:8080/swagger-ui/index.html`


## CI/CD Pipeline (Jenkins Integration)

QualiAir uses a **continuous integration pipeline** powered by **Jenkins**, automatically triggered whenever new code is merged into the `main` branch.

### Workflow Overview

1. **Merge to `main`**
    - A developer merges a pull request into the `main` branch.
    - This push event triggers a **GitHub webhook** configured to notify Jenkins.

2. **Webhook Notification**
    - GitHub sends a JSON payload to Jenkins at:
      ```
      https://<jenkins-server>/github-webhook/
      ```
    - Jenkins receives the event and automatically triggers the corresponding pipeline job.

3. **Jenkins Pipeline Execution**
   The pipeline defined in the Jenkinsfile (or configured via UI) performs the following stages:

   | Stage | Description |
      |--------|-------------|
   |  **Checkout** | Fetches the latest code from the `main` branch. |
   | ️**Build** | Runs `mvn clean package` to compile the Spring Boot project. |
   |  **Test** | Executes all unit and integration tests (`mvn test`). |
   |  **Quality Gate** | Optionally integrates with **SonarQube** for code quality and coverage validation. |
   |  **Artifact Archiving** | Packages the application JAR and stores it in Jenkins or uploads to a repository. |
   |  **Deployment** *(optional)* | Deploys to a staging or production environment (e.g., via SSH, Docker, or Kubernetes). |

4. **Feedback Loop**
    - Jenkins reports build status back to GitHub:
        -  **Success** → merge is validated.
        -  **Failure** → GitHub PR or commit shows a red build status.
    - Developers can review console output and reports directly in Jenkins.