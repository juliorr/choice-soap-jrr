CREATE TABLE IF NOT EXISTS `amenities` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(100)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO amenities (name)
values
    ('Internet'),
    ('Buffet');