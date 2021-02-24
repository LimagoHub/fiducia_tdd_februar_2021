drop table if exists `tblpersonen` ;
CREATE TABLE `tblpersonen` (
  `id` varchar(36) NOT NULL,
  `nachname` varchar(30) NOT NULL,
  `vorname` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
);