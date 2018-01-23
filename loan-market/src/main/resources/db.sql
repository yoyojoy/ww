CREATE TABLE `wx_menu` (
  `ID` varchar(32) NOT NULL,
  `menu_key` varchar(32) DEFAULT NULL,
  `media_type` varchar(64) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `orders` varchar(11) DEFAULT NULL,
  `type` varchar(64) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `appid` varchar(128) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `account_id` varchar(255) DEFAULT NULL,
  `comment` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`ID`)
);

CREATE TABLE `wx_plat_user` (
  `ID` varchar(32) NOT NULL,
  `nick_name` varchar(64) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `openid` varchar(128) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
);