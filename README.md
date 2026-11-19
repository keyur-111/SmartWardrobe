# Smart Wardrobe

Smart Wardrobe is a Java web application for organizing clothing, tracking laundry, and planning outfit combinations. Users maintain a personal wardrobe and save outfit ideas; administrators manage shared categories and user accounts.

## Features

### User features

- Register, sign in, sign out, and maintain a user session.
- View a dashboard with wardrobe and laundry summaries.
- Add, view, search, filter, edit, and delete clothing items.
- Track clothing status and usage; upload JPG, JPEG, PNG, or WEBP images (up to 5 MB per file).
- Generate outfit suggestions by occasion and season, then save, view, or delete outfits.
- Start laundry cycles and mark them complete.
- View profile details.

### Administrator features

- View user and application summary information.
- Update or delete regular-user accounts.
- Change the signed-in administrator's password.
- Add, view, and delete clothing categories.

## Technology and architecture

- Java Servlets (`javax.servlet`, Servlet 3.1) and JSP
- MySQL with JDBC and parameterized `PreparedStatement` queries
- HTML, CSS, and JavaScript
- Apache Ant, Apache Tomcat 9, and MySQL Connector/J
- Docker configuration for running the application in a Tomcat container

Requests flow from a browser form or link to a Servlet controller, then through a service class and DAO to MySQL. The controller forwards to a JSP view or redirects the browser after the operation. Database access uses JDBC; the project does not use Spring or Hibernate. Because it uses `javax.servlet`, use Tomcat 9 rather than Tomcat 10.

## Project layout

| Path | Purpose |
| --- | --- |
| `src/java/com/smartwardrobe/controller/` | Servlet request handlers |
| `src/java/com/smartwardrobe/service/` | Application operations |
| `src/java/com/smartwardrobe/dao/` | JDBC database access |
| `src/java/com/smartwardrobe/model/` | Application data models |
| `src/java/com/smartwardrobe/util/` | Database, session, password, and web helpers |
| `web/` | JSP pages, shared navigation, CSS, JavaScript, and uploaded/demo images |
| `database.sql` | Schema, administrator/demo users, categories, and sample data |
| `build.xml` | NetBeans/Ant build configuration |
| `Dockerfile` | Multi-stage Tomcat image build |

## Requirements

- JDK 8 or later
- Apache Ant
- MySQL
- Apache Tomcat 9 for a local WAR deployment
- Docker Desktop or Docker Engine (optional)

The project is Java 8 source-compatible. The Docker build uses JDK 21 with `javac --release 8`.

## Local setup and run

### 1. Get the project

```shell
git clone https://github.com/keyur-111/SmartWardrobe.git
cd SmartWardrobe
```

### 2. Create the database

Run `database.sql` using MySQL Workbench, or from Command Prompt:

```shell
mysql -u root -p < database.sql
```

The script creates the `smart_wardrobe` database and its tables, then inserts sample categories, clothing, outfits, and evaluation accounts.

### 3. Configure the database connection

`DBConnection` checks Java system properties first, then environment variables, and finally local-development defaults. Set these before starting Tomcat:

| Environment variable | Java system property | Default |
| --- | --- | --- |
| `SMARTWARDROBE_DB_URL` | `smartwardrobe.db.url` | `jdbc:mysql://localhost:3306/smart_wardrobe?useSSL=false&serverTimezone=Asia/Kolkata&allowPublicKeyRetrieval=true` |
| `SMARTWARDROBE_DB_USER` | `smartwardrobe.db.user` | `root` |
| `SMARTWARDROBE_DB_PASSWORD` | `smartwardrobe.db.password` | Empty |

For example, in PowerShell:

```powershell
$env:SMARTWARDROBE_DB_URL = "jdbc:mysql://localhost:3306/smart_wardrobe?useSSL=false&serverTimezone=Asia/Kolkata&allowPublicKeyRetrieval=true"
$env:SMARTWARDROBE_DB_USER = "root"
$env:SMARTWARDROBE_DB_PASSWORD = "your-local-password"
```

These are development defaults, not recommended production credentials. When running Tomcat as a service, configure the variables or Java properties in that service's environment.

### 4. Build the WAR

From the project directory:

```shell
ant -f build.xml dist
```

The application archive is created at `dist/SmartWardrobe.war`.

### 5. Deploy to Tomcat 9

Copy `dist/SmartWardrobe.war` into Tomcat's `webapps` directory and start Tomcat. On Windows PowerShell, for example:

```powershell
Copy-Item .\dist\SmartWardrobe.war "$env:CATALINA_HOME\webapps\"
& "$env:CATALINA_HOME\bin\startup.bat"
```

Open [http://localhost:8080/SmartWardrobe/](http://localhost:8080/SmartWardrobe/) (or go directly to `/SmartWardrobe/login.jsp`). Tomcat deploys the WAR under the `SmartWardrobe` context path.

## Running with Docker

Build the image from the project root:

```shell
docker build -t smartwardrobe .
```

Run it with a MySQL server reachable from inside the container. For Docker Desktop on Windows, `host.docker.internal` can be used to reach a MySQL server running on the host:

```powershell
docker run --rm -p 8080:8080 `
  -e PORT=8080 `
  -e SMARTWARDROBE_DB_URL="jdbc:mysql://host.docker.internal:3306/smart_wardrobe?useSSL=false&serverTimezone=Asia/Kolkata&allowPublicKeyRetrieval=true" `
  -e SMARTWARDROBE_DB_USER="root" `
  -e SMARTWARDROBE_DB_PASSWORD="your-local-password" `
  smartwardrobe
```

The container exposes port `8080`. Change the database host and credentials to match your environment; `localhost` inside a container refers to that container, not the host machine.

## Using the application

1. Register an account or sign in with the evaluation account below.
2. Use **My Wardrobe** to add clothing details such as category, color, fabric, season, occasion, and status. Search and filter your items from the wardrobe page.
3. Use **Outfit Generator** to choose an occasion and season, review suggested available clothing, and save an outfit you want to keep.
4. Use **Laundry Tracker** to select an item and start a laundry cycle. Mark the cycle complete when it is returned.
5. Use **Profile** to view account details and **Logout** to end the session.
6. Administrator pages are available only to accounts with the administrator role.

The configured session timeout is 30 minutes. The shared evaluation account is a regular user with sample wardrobe data; its data may be changed by other visitors.

**Demo account**

- Email: `demouser@gmail.com`
- Password: `WardrobeDemo2026!`

## Links

- GitHub repository: [keyur-111/SmartWardrobe](https://github.com/keyur-111/SmartWardrobe)
- Deployed application: [Smart Wardrobe](https://smartwardrobe.up.railway.app/SmartWardrobe)

## Troubleshooting

- **Tomcat returns 404:** confirm the WAR is named `SmartWardrobe.war`, is deployed in `webapps`, and Tomcat has finished deploying it.
- **Database connection fails:** confirm MySQL is running, `database.sql` was imported, and the URL, user, and password are available to the Tomcat process. In Docker, use a database hostname reachable from the container.
- **Servlet classes fail to load:** use Tomcat 9; this project uses the `javax.servlet` API.
- **Changes do not appear:** rebuild the WAR and redeploy it, or restart the container after rebuilding the image.
