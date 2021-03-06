DROP TABLE IF EXISTS user;
CREATE TABLE user(
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     user_id varchar(36),
                     user_name varchar(36),
                     password varchar(36),
                     avatar varchar(36)
);

INSERT INTO user ( user_id, user_name,password,avatar) VALUES
('1', '夏侯恩', '123456', 'Industrialist'),
('2', '马超', '123456', 'Tech Entrepreneur'),
('3', '颜良', '123456', 'Oil Magnate');

DROP TABLE IF EXISTS message;
CREATE TABLE message(
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     from_user_id varchar(36),
                     to_user_id varchar(36),
                     status INT,
                     content varchar(1000),
                     create_date DATE,
                     update_date DATE
);
