CREATE TABLE IF NOT EXISTS `hotel` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(100),
    `address` varchar(50),
    `rating` int

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;