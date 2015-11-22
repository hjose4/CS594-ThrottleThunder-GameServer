create database if not exists DD_DB;

use DD_DB;

drop table if exists friendships;
drop table if exists remaining_parts;
drop table if exists games_history;
drop table if exists vehicles;
drop table if exists players;



create table players(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(16),
	user_password VARCHAR(16),
	isConnected BOOLEAN
);


create table games_history(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	game_type INT,
	map_name varchar(16),
	winner_id INT NULL
);

-- parts related to a player only, when user uses one part: 
-- it improves the vehicule caracteristic that is concerned from the player_id
create table remaining_parts(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	player_id INT,

	name VARCHAR(20),
	part_power INT,
	part_weight INT,
	part_armor INT,
	part_control INT,
	part_speed INT,
	part_acceleration INT,

	FOREIGN KEY (player_id) REFERENCES players(id)
);

create table vehicles(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	player_id INT,
	type_name varchar(16),
	description varchar(300),
	power INT,
	weight INT,
	armor INT,
	control INT,
	speed INT,
	acceleration INT,

	FOREIGN KEY (player_id) REFERENCES players(id)
);


create table friendships (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	uid1 int,
	uid2 int,

	FOREIGN KEY (uid1) REFERENCES players(id),
	FOREIGN KEY (uid2) REFERENCES players(id)
);


