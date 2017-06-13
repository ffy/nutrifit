# User schema

# --- !Ups
create table `users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `email` VARCHAR(255) NOT NULL UNIQUE ,
  `password` TEXT NOT NULL,
  `weight` SMALLINT NOT NULL,
  `height` SMALLINT NOT NULL,
  `gender` VARCHAR(45) NOT NULL
)

# --- !Downs
drop table `users`