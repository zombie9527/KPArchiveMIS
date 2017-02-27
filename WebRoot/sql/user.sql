DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `passwd` varchar(30) NOT NULL,
  `userName` varchar(30) NOT NULL DEFAULT 'user',
  `grade` varchar(30) NOT NULL DEFAULT 'user',
  `createTime` date NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `userId` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;