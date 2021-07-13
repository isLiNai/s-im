DROP TABLE IF EXISTS user;
CREATE TABLE user(
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     user_id varchar(32),
                     user_name varchar(32),
                     password varchar(32),
                     avatar varchar(32)
);

INSERT INTO user ( user_id, user_name,password,avatar) VALUES
('1', '夏侯恩', '123456', 'Industrialist'),
('2', '马超', '123456', 'Tech Entrepreneur'),
('3', '颜良', '123456', 'Oil Magnate');