-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema securitydemodb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `securitydemodb` ;

-- -----------------------------------------------------
-- Schema securitydemodb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `securitydemodb` DEFAULT CHARACTER SET utf8mb4 ;
USE `securitydemodb` ;

-- -----------------------------------------------------
-- Table `securitydemodb`.`api_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `securitydemodb`.`api_user` ;

CREATE TABLE IF NOT EXISTS `securitydemodb`.`api_user` (
  `api_user_id` BINARY(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
  `email` VARCHAR(50) NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`api_user_id`),
  UNIQUE INDEX `api_user_id_UNIQUE` (`api_user_id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `securitydemodb`.`api_user`
-- -----------------------------------------------------
START TRANSACTION;
USE `securitydemodb`;
INSERT INTO `securitydemodb`.`api_user` (`api_user_id`, `email`, `username`, `password`) VALUES (DEFAULT, 'admin@domain.com', 'admin_user', '$2a$10$C9K17O0muzKLpFtNxOMbVOuNxoA9S7TubSDuKEbzhekTzlHZrYaC6');

COMMIT;

