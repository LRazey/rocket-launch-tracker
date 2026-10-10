# Rocket Launch Tracker

A full-stack web application for space enthusiasts to browse upcoming rocket
launches and manage a personal watchlist.

## Overview

Users can browse upcoming launches (sourced live from the Launch Library 2 API),
search and filter by agency and date, and save launches to a personal watchlist
with full create/modify/delete support, reminders, and a watchlist report.

## Tech Stack

- **Backend:** Java + Spring Boot (REST API), Spring Data JPA / Hibernate
- **Frontend:** React
- **Database:** PostgreSQL
- **Security:** Spring Security (BCrypt password hashing, JWT authentication)
- **External API:** Launch Library 2 (https://thespacedevs.com/llapi)
- **Hosting:** Railway
- **Version control:** GitLab

## Project Structure

```
SpaceLaunchTracker/
  backend/    Spring Boot REST API
  frontend/   React single-page app
```


## Author

Levi Razey
