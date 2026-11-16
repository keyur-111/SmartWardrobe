# Smart Wardrobe

A Java web application for managing clothing, tracking laundry, and planning outfits.

## Technologies

- Java Servlets and JSP
- MySQL with JDBC
- HTML, CSS, and JavaScript
- Apache Tomcat, Ant, and NetBeans

## Features

- User registration, login, logout, and session handling
- Wardrobe, laundry, and outfit management
- Admin dashboard and category management

## Run locally

Requirements: JDK 8 or later, MySQL, Apache Tomcat, and Ant.

1. Import the database: `mysql -u root < database.sql`
2. Build the application: `ant -f build.xml dist`
3. Copy `dist\SmartWardrobe.war` into Tomcat's `webapps` folder and start Tomcat.
4. Open `http://localhost:8080/SmartWardrobe/`.

The default database is `smart_wardrobe` on `localhost:3306` using `root` with an empty password. Set `SMARTWARDROBE_DB_URL`, `SMARTWARDROBE_DB_USER`, and `SMARTWARDROBE_DB_PASSWORD` to use another database.

## Live application

[Open Smart Wardrobe](https://smartwardrobe-production.up.railway.app/SmartWardrobe/login.jsp)

Evaluation login credentials are shared separately. Do not publish production passwords in this public repository.

Screenshots are available in the [`screenshots`](screenshots/) folder.
