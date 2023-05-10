CREATE TABLE IF NOT EXISTS `users` (
 `id` integer PRIMARY KEY AUTO_INCREMENT,
 `email` varchar(255),
 `name` varchar(255),
 `password` varchar(255),
 `created_at` timestamp,
 `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `rentals` (
   `id` integer PRIMARY KEY AUTO_INCREMENT,
   `name` varchar(255),
   `surface` numeric,
   `price` numeric,
   `picture` varchar(255),
   `description` varchar(2000),
   `owner_id` integer NOT NULL,
   `created_at` timestamp,
   `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `messages` (
    `id` integer PRIMARY KEY AUTO_INCREMENT,
    `rental_id` integer,
    `user_id` integer,
    `message` varchar(2000),
    `created_at` timestamp,
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX `users_index` ON `users` (`email`);

ALTER TABLE `rentals` ADD CONSTRAINT FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`);

ALTER TABLE `messages` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `messages` ADD FOREIGN KEY (`rental_id`) REFERENCES `rentals` (`id`);
