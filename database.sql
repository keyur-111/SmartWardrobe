-- Smart Wardrobe database setup + demo data

CREATE DATABASE IF NOT EXISTS smart_wardrobe CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE smart_wardrobe;

CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS categories (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS clothing_items (
    clothing_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    category_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    color VARCHAR(50),
    fabric VARCHAR(50),
    season VARCHAR(50),
    occasion VARCHAR(50),
    image VARCHAR(255),
    status VARCHAR(30) DEFAULT 'Clean',
    usage_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY(category_id) REFERENCES categories(category_id)
);

CREATE TABLE IF NOT EXISTS laundry (
    laundry_id INT PRIMARY KEY AUTO_INCREMENT,
    clothing_id INT NOT NULL,
    laundry_date DATE NOT NULL,
    return_date DATE,
    status VARCHAR(30) DEFAULT 'In Laundry',
    FOREIGN KEY(clothing_id) REFERENCES clothing_items(clothing_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS outfits (
    outfit_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    outfit_name VARCHAR(100),
    occasion VARCHAR(50),
    season VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS outfit_items (
    outfit_item_id INT PRIMARY KEY AUTO_INCREMENT,
    outfit_id INT NOT NULL,
    clothing_id INT NOT NULL,
    FOREIGN KEY(outfit_id) REFERENCES outfits(outfit_id) ON DELETE CASCADE,
    FOREIGN KEY(clothing_id) REFERENCES clothing_items(clothing_id) ON DELETE CASCADE
);

INSERT INTO users(user_id,name,email,password,role) VALUES
(1,'Smart Wardrobe Admin','admin@smartwardrobe.com','a2ebfb46dd26809c444e50585d07315485482e256f6f74e4a4690b9112a7567d','ADMIN'),
(2,'Demo User','demouser@gmail.com','3dbc1903fbcc50ec1624c986ebb68dc251fd982df5a6a560b76156987653cb73','USER')
ON DUPLICATE KEY UPDATE name=VALUES(name), email=VALUES(email), password=VALUES(password), role=VALUES(role);

INSERT INTO categories(category_id,category_name,description) VALUES
(1,'Tops','T-shirts and shirts'),
(2,'Bottoms','Jeans and trousers'),
(3,'Footwear','Sneakers and formal shoes'),
(4,'Outerwear','Jackets and hoodies'),
(5,'Accessories','Belts and caps')
ON DUPLICATE KEY UPDATE description=VALUES(description);

-- Evaluation wardrobe: all images are included in web/uploads/
INSERT INTO clothing_items(clothing_id,user_id,category_id,name,color,fabric,season,occasion,image,status,usage_count) VALUES
(1,2,1,'White T-Shirt','White','Cotton','All','Any','white-tshirt.png','Clean',2),
(2,2,1,'Black Shirt','Black','Cotton','All','Formal','black-shirt.png','Clean',1),
(3,2,1,'Blue Shirt','Blue','Linen','Summer','Casual','blue-shirt.png','Clean',3),
(4,2,2,'Blue Jeans','Blue','Denim','All','Any','blue-jeans.png','Clean',4),
(5,2,2,'Black Trousers','Black','Cotton','All','Formal','black-trousers.png','Clean',2),
(6,2,2,'Beige Chinos','Beige','Cotton','Summer','Casual','beige-chinos.png','Clean',1),
(7,2,3,'White Sneakers','White','Synthetic','All','Any','white-sneakers.png','Clean',5),
(8,2,3,'Black Formal Shoes','Black','Leather','All','Formal','black-formal-shoes.png','Clean',2),
(9,2,4,'Denim Jacket','Blue','Denim','Winter','Casual','denim-jacket.png','Clean',1),
(10,2,4,'Black Hoodie','Black','Fleece','Winter','Casual','black-hoodie.png','In Laundry',3),
(11,2,5,'Black Belt','Black','Leather','All','Formal','black-belt.png','Clean',1),
(12,2,5,'Black Cap','Black','Cotton','Summer','Casual','black-cap.png','Clean',2)
ON DUPLICATE KEY UPDATE
name=VALUES(name),color=VALUES(color),fabric=VALUES(fabric),season=VALUES(season),occasion=VALUES(occasion),image=VALUES(image),status=VALUES(status),usage_count=VALUES(usage_count);

INSERT INTO laundry(laundry_id,clothing_id,laundry_date,return_date,status) VALUES
(1,10,CURDATE(),NULL,'In Laundry'),
(2,1,DATE_SUB(CURDATE(),INTERVAL 7 DAY),DATE_SUB(CURDATE(),INTERVAL 5 DAY),'Completed')
ON DUPLICATE KEY UPDATE status=VALUES(status),return_date=VALUES(return_date);

INSERT INTO outfits(outfit_id,user_id,outfit_name,occasion,season) VALUES
(1,2,'Casual Summer Look','Casual','Summer'),
(2,2,'Formal Office Look','Formal','All'),
(3,2,'Winter Casual Look','Casual','Winter')
ON DUPLICATE KEY UPDATE outfit_name=VALUES(outfit_name),occasion=VALUES(occasion),season=VALUES(season);

INSERT IGNORE INTO outfit_items(outfit_item_id,outfit_id,clothing_id) VALUES
(1,1,3),(2,1,6),(3,1,7),
(4,2,2),(5,2,5),(6,2,8),(7,2,11),
(8,3,1),(9,3,4),(10,3,7),(11,3,9);