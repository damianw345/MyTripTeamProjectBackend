SET SQL_MODE='ALLOW_INVALID_DATES';

CREATE TABLE `trip` (
	`trip_id` varchar(36) NOT NULL PRIMARY KEY,
	`name` varchar(255) NOT NULL,
	`description` TEXT,
	`start` TIMESTAMP NOT NULL,
	`end` TIMESTAMP NOT NULL,
	`poster` varchar(1000) UNIQUE,
	`presentation` varchar(1000) UNIQUE,
	`cached_map` varchar(1000) UNIQUE
);

CREATE TABLE `waypoint` (
	`waypoint_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`trip_id` varchar(36) NOT NULL,
	`latitude` FLOAT NOT NULL,
	`longitude` FLOAT NOT NULL,
	`date` TIMESTAMP NOT NULL,
	KEY `fk_trip_idx` (`trip_id`),
	CONSTRAINT `fk_waypoint_trip` FOREIGN KEY (`trip_id`) REFERENCES `trip`(`trip_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `photo` (
	`photo_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`waypoint_id` BIGINT NOT NULL,
	`date` TIMESTAMP,
	`url` varchar(1000) NOT NULL UNIQUE,
	`thumbnail_url` varchar(1000) NOT NULL UNIQUE,
	FOREIGN KEY (`waypoint_id`) REFERENCES `waypoint`(`waypoint_id`) ON DELETE CASCADE
);

CREATE TABLE `video` (
	`video_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`waypoint_id` BIGINT NOT NULL,
	`date` TIMESTAMP,
	`url` varchar(1000) NOT NULL UNIQUE,
	FOREIGN KEY (`waypoint_id`) REFERENCES `waypoint`(`waypoint_id`) ON DELETE CASCADE
);