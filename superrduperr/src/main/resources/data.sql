DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_reminder;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS carts;
 
CREATE TABLE products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  quantity VARCHAR(250) NOT NULL,
  price DECIMAL(20, 2) NOT NULL DEFAULT 0.00,
  available BOOLEAN DEFAULT TRUE
);
 
INSERT INTO products (name, quantity, price) VALUES
  ('Pen', '100', '1.00'),
  ('Bag', '10', '20.00'),
  ('NoteBook', '50', '6.00'),
  ('Soap', '100', '7.00'),
  ('Tooth Paste', '20', '12.00'),
  ('Bottle', '50', '8.00');
  
create table users (
	id INT AUTO_INCREMENT PRIMARY KEY,
	first_name VARCHAR(250) NOT NULL,
	last_name VARCHAR(250) NOT NULL
);

INSERT INTO users (first_name, last_name) VALUES 
	('Default', 'User');
	
CREATE TABLE user_reminder (
  id INT AUTO_INCREMENT PRIMARY KEY,
  product_id INT NOT NULL,
  user_id INT NOT NULL,
  name VARCHAR(250) NOT NULL,
  foreign key (user_id) references users(id)
);
CREATE TABLE carts (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  name VARCHAR(250) NOT NULL
);

CREATE TABLE items (
  id INT AUTO_INCREMENT PRIMARY KEY,
  cart_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity INT NOT NULL,
  version INT NOT NULL,
  tag VARCHAR(250) DEFAULT NULL,
  foreign key (cart_id) references carts(id)
);
