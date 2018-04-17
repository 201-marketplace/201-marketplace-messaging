CREATE TABLE `messagedata` (
  `messageID` int(11) NOT NULL AUTO_INCREMENT,
  `messageText` varchar(512) DEFAULT NULL,
  `messageTime` datetime DEFAULT NULL,
  `senderID` varchar(30) DEFAULT NULL,
  `receiverID` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`messageID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

