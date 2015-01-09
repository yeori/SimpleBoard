-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        5.5.5-10.0.10-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win32
-- HeidiSQL 버전:                  8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 테이블 demoboard의 구조를 덤프합니다. postings
DROP TABLE IF EXISTS `postings`;
CREATE TABLE IF NOT EXISTS `postings` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(256) NOT NULL,
  `content` text NOT NULL,
  `views` int(10) unsigned NOT NULL DEFAULT '0',
  `when_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fk_writer` int(10) unsigned NOT NULL,
  PRIMARY KEY (`seq`),
  KEY `fk_writer_ref_users` (`fk_writer`),
  CONSTRAINT `fk_writer_ref_users` FOREIGN KEY (`fk_writer`) REFERENCES `users` (`seq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='각각의 게시판 글';

-- Dumping data for table demoboard.postings: ~3 rows (대략적)
DELETE FROM `postings`;
/*!40000 ALTER TABLE `postings` DISABLE KEYS */;
INSERT INTO `postings` (`seq`, `title`, `content`, `views`, `when_created`, `fk_writer`) VALUES
	(1, '첫번째글', '이것은 제임스의 첫번째 글입니다.', 3, '2014-11-30 12:21:09', 5000),
	(2, '두번째글', '제임스가 두번째 글을 넣었습니다.', 32, '2014-12-01 01:21:07', 5000),
	(3, '3rd', '김공꽁도 글을 썼음', 12, '2014-12-02 16:37:24', 5001);
/*!40000 ALTER TABLE `postings` ENABLE KEYS */;


-- 테이블 demoboard의 구조를 덤프합니다. users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userid` varchar(24) NOT NULL,
  `nickname` varchar(50) NOT NULL,
  `email` varchar(256) NOT NULL,
  `password` char(40) NOT NULL,
  `when_joined` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=5002 DEFAULT CHARSET=utf8;

-- Dumping data for table demoboard.users: ~2 rows (대략적)
DELETE FROM `users`;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`seq`, `userid`, `nickname`, `email`, `password`, `when_joined`) VALUES
	(5000, 'james', 'james', 'james@naver.com', '', '2014-12-22 13:25:26'),
	(5001, 'kkongkkong', '김꽁꽁', 'kongkong@gmail.com', '', '2014-12-23 09:53:09');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
