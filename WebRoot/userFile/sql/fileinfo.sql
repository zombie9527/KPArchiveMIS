
--fileinfo表的建表sql
CREATE TABLE `fileinfo` (
`fileId`  int NOT NULL AUTO_INCREMENT ,
`fileName`  varchar(138) CHARACTER SET utf8 NOT NULL DEFAULT '' ,
`timeName`  varchar(18) CHARACTER SET utf8 NOT NULL DEFAULT '' ,
`realPath`  varchar(100) CHARACTER SET utf8 NOT NULL DEFAULT '' ,
`timePath`  varchar(38) CHARACTER SET utf8 NOT NULL DEFAULT '' ,
`fileDelete` int CHARACTER SET utf8 NOT NULL DEFAULT '0' ,
`fileTitle` varchar(38) CHARACTER SET utf8 NOT NULL DEFAULT '' ,
`createTime` varchar(38) CHARACTER SET utf8 NOT NULL DEFAULT '' ,
`textContent` TEXT CHARACTER SET utf8 DEFAULT '' ,
`file`  longblob NOT NULL ,
PRIMARY KEY (`fileId`)
)
;