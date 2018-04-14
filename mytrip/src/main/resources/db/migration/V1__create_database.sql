CREATE TABLE `trip` (
	`trip_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`name` varchar(255) NOT NULL,
	`description` TEXT,
	`start` TIMESTAMP NOT NULL DEFAULT '1970-01-01 00:00:01',
	`end` TIMESTAMP NOT NULL DEFAULT '1970-01-01 00:00:01',
	`poster` varchar(1000) UNIQUE,
	`presentation` varchar(1000) UNIQUE
);

CREATE TABLE `waypoint` (
	`waypoint_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`trip_id` BIGINT NOT NULL,
	`latitude` DECIMAL(10,8) NOT NULL,
	`longitude` DECIMAL(11,8) NOT NULL,
	`date` TIMESTAMP NOT NULL DEFAULT '1970-01-01 00:00:01',
	FOREIGN KEY (`trip_id`) REFERENCES `trip`(`trip_id`) ON DELETE CASCADE
);

CREATE TABLE `photo` (
	`photo_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`waypoint_id` BIGINT NOT NULL,
	`date` TIMESTAMP DEFAULT '1970-01-01 00:00:01',
	`url` varchar(1000) NOT NULL UNIQUE,
	`thumbnail_url` varchar(1000) NOT NULL UNIQUE,
	FOREIGN KEY (`waypoint_id`) REFERENCES `waypoint`(`waypoint_id`) ON DELETE CASCADE
);

CREATE TABLE `video` (
	`video_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`waypoint_id` BIGINT NOT NULL,
	`date` TIMESTAMP DEFAULT '1970-01-01 00:00:01',
	`url` varchar(1000) NOT NULL UNIQUE,
	FOREIGN KEY (`waypoint_id`) REFERENCES `waypoint`(`waypoint_id`) ON DELETE CASCADE
);
