# Smart Wardrobe

Smart Wardrobe is a Java web application for managing clothing, tracking laundry, and planning outfits.

## Technologies and tools

- Java Servlets and JSP, MySQL with JDBC
- HTML, CSS, and JavaScript
- Apache Tomcat, Apache Ant, NetBeans

## Features

- User registration, login, logout, and session handling
- Wardrobe, laundry, and outfit management
- Admin dashboard and category management

## Installation and execution

Requirements: JDK 8+, MySQL, Apache Tomcat, and Apache Ant.

1. Import `database.sql` into MySQL: `mysql -u root < database.sql`
2. Build: `ant -f build.xml dist`
3. Copy `dist\SmartWardrobe.war` to Tomcat's `webapps` folder and start Tomcat.
4. Open `http://localhost:8080/SmartWardrobe/`.

Database: `smart_wardrobe` on `localhost:3306` (default user `root`, empty password). Configure another database with `SMARTWARDROBE_DB_URL`, `SMARTWARDROBE_DB_USER`, and `SMARTWARDROBE_DB_PASSWORD`.

## Links

- GitHub: [keyur-111/SmartWardrobe](https://github.com/keyur-111/SmartWardrobe)
- Live application: [Smart Wardrobe](https://smartwardrobe-production.up.railway.app/SmartWardrobe/login.jsp)

## Evaluation account

- Email: `demouser@gmail.com`
- Password: `WardrobeDemo2026!`

This is a shared regular-user account with sample wardrobe data. It is not an administrator account, and its data may be changed by visitors.