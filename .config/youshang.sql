SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `youshang` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `youshang` ;

-- -----------------------------------------------------
-- Table `youshang`.`member_profile`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `youshang`.`member_profile` ;

CREATE  TABLE IF NOT EXISTS `youshang`.`member_profile` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `username` VARCHAR(64) NULL COMMENT '用户名称' ,
  `mobile` VARCHAR(32) NULL COMMENT '移动号码' ,
  `weibo` VARCHAR(128) NULL COMMENT 'Sina微博号' ,
  `score` INT(11) NULL DEFAULT 0 COMMENT '用户等级' ,
  `name` VARCHAR(128) NULL COMMENT '用户真实姓名' ,
  `last_login_time` DATETIME NULL COMMENT '最近登陆时间' ,
  `avatar_path` VARCHAR(128) NULL COMMENT '头像路径' ,
  `create_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ,
  `update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `MEMBER_PROFILE_WEIBO` (`weibo` ASC) ,
  UNIQUE INDEX `MEMBER_PFORILE_MOBILE` (`mobile` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `youshang`.`task_profile`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `youshang`.`task_profile` ;

CREATE  TABLE IF NOT EXISTS `youshang`.`task_profile` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `sponsor_id` INT NULL COMMENT '任务发起人ID' ,
  `executor_id` INT NULL COMMENT '任务执行者ID' ,
  `reward` DECIMAL(10,2) NULL COMMENT '酬金，RMB' ,
  `end_time` DATETIME NULL COMMENT '任务完成期限' ,
  `description` VARCHAR(2048) NULL COMMENT '任务描述' ,
  `status` TINYINT UNSIGNED NULL COMMENT '状态' ,
  `location` VARCHAR(256) NULL COMMENT '任务地理位置' ,
  `photo_path` VARCHAR(256) NULL COMMENT '照片存放路径' ,
  `record_path` VARCHAR(256) NULL COMMENT '录音存放路径' ,
  `create_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
  `update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `youshang`.`task_acceptance`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `youshang`.`task_acceptance` ;

CREATE  TABLE IF NOT EXISTS `youshang`.`task_acceptance` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `task_id` INT NULL COMMENT '任务ID' ,
  `acceptor_id` INT NULL COMMENT '接受者ID' ,
  `create_time` DATETIME NULL ,
  `update_time` DATETIME NULL ,
  `deleted` BIT NULL COMMENT '是否删除' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `youshang`.`comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `youshang`.`comment` ;

CREATE  TABLE IF NOT EXISTS `youshang`.`comment` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `commenter_id` INT NULL COMMENT '评价者ID' ,
  `assessee_id` INT NULL COMMENT '被评价者ID' ,
  `task_id` INT NULL COMMENT '关联任务ID' ,
  `degree` TINYINT NULL COMMENT '评价等级' ,
  `contents` VARCHAR(2048) NULL COMMENT '描述内容' ,
  `create_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

USE `youshang` ;

-- -----------------------------------------------------
-- Placeholder table for view `youshang`.`task_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `youshang`.`task_detail` (`id` INT, `sponsor_id` INT, `executor_id` INT, `reward` INT, `end_time` INT, `description` INT, `status` INT, `location` INT, `photo_path` INT, `record_path` INT, `create_time` INT, `update_time` INT, `sponsor_name` INT, `sponsor_mobile` INT, `sponsor_avatar_path` INT, `executor_name` INT, `executor_mobile` INT, `executor_avatar_path` INT);

-- -----------------------------------------------------
-- View `youshang`.`task_detail`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `youshang`.`task_detail` ;
DROP TABLE IF EXISTS `youshang`.`task_detail`;
USE `youshang`;
CREATE  OR REPLACE VIEW `youshang`.`task_detail` AS 
select t.*,s.username as sponsor_name,s.mobile as sponsor_mobile,s.avatar_path as sponsor_avatar_path,
	e.username as executor_name,e.mobile as executor_mobile,e.avatar_path as executor_avatar_path
from task_profile t left outer join member_profile s on t.sponsorId=s.id 
left outer join member_profile e on t.executorId=e.id

;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
