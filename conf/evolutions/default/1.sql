# User schema

# --- !Ups
create table `users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `email` TEXT NOT NULL,
  `password` TEXT NOT NULL,
  `weight` TINYINT NOT NULL,
  `height` TINYINT NOT NULL,
  `gender` VARCHAR(45)
)

# --- !Downs
drop table `user`