# Smart Wardrobe - AJT Mini Project

## Stack
- Java Servlets + JSP
- JDBC + MySQL
- Apache Tomcat
- Bootstrap, HTML5, CSS3, JavaScript
- MVC/layered DAO + service structure

## Demo accounts
- Admin: admin@smartwardrobe.com / admin123
- User: demo@smartwardrobe.com / user123

## Database
Import `database.sql` into MySQL/phpMyAdmin. It creates the `smart_wardrobe` database, six related tables, demo accounts, categories, demo wardrobe items, laundry history and saved outfits.

## Demo images
Twelve sample clothing images are included in `web/uploads/` and linked to the demo user's wardrobe.

## Running
1. Start MySQL in XAMPP.
2. Import `database.sql` into phpMyAdmin.
3. Open the project in NetBeans.
4. Run it on Apache Tomcat.
5. Open `/SmartWardrobe/`.

## Outfit Generator
Choose an occasion and season. The Servlet queries clean wardrobe items through JDBC, selects a combination from Tops + Bottoms + Footwear, and optionally adds Outerwear. Items in laundry/dry-cleaning are excluded.

## Image upload
Add Clothing now supports JPG/JPEG/PNG/WEBP uploads. Uploaded files are stored in `web/uploads/` at runtime and the filename is saved in MySQL.
