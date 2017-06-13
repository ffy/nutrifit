# Foods schema

# --- !Ups
create table `foods` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) NOT NULL UNIQUE ,
  `calories` FLOAT NOT NULL,
  `proteins` FLOAT NOT NULL,
  `carbohydrates` FLOAT NOT NULL,
  `lipids` FLOAT NOT NULL
)

# --- !Downs
drop table `foods`