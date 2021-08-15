

CREATE TABLE `web`.`weibo`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `content` varchar(45) NOT NULL,
    `userId`  int(11) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `web`.`comment`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `content` varchar(45) NOT NULL,
    `weiboId` int(11) NOT NULL,
    `userId`  int(11) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;


CREATE
DATABASE `web` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `web`.`message`
(
    `author`  varchar(100) NOT NULL,
    `content` varchar(100) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE `web`.`session`
(
    `username`  varchar(100) NOT NULL,
    `sessionId` varchar(100) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE `web`.`todo`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `content` varchar(45) NOT NULL,
    `userId`  int(11) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `web`.`user`
(
    `id`       int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(300) DEFAULT NULL,
    `password` varchar(300) DEFAULT NULL,
    `role`     varchar(300) DEFAULT NULL,
    `salt`     varchar(300) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;
