-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.3.13-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- writeproject 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `writeproject` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `writeproject`;

-- 테이블 writeproject.testtable 구조 내보내기
CREATE TABLE IF NOT EXISTS `testtable` (
  `num` int(5) DEFAULT NULL,
  `day` datetime DEFAULT current_timestamp(),
  `content` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 writeproject.testtable:~0 rows (대략적) 내보내기
DELETE FROM `testtable`;
/*!40000 ALTER TABLE `testtable` DISABLE KEYS */;
/*!40000 ALTER TABLE `testtable` ENABLE KEYS */;

-- 테이블 writeproject.writer_mem 구조 내보내기
CREATE TABLE IF NOT EXISTS `writer_mem` (
  `mem_idx` int(11) NOT NULL AUTO_INCREMENT,
  `mem_email` varchar(60) NOT NULL,
  `mem_pwd` varchar(200) NOT NULL,
  `mem_nickname` varchar(30) NOT NULL,
  `mem_type` varchar(20) NOT NULL DEFAULT 'local',
  `mem_regdate` datetime NOT NULL DEFAULT current_timestamp(),
  `mem_update` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`mem_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;

-- 테이블 데이터 writeproject.writer_mem:~5 rows (대략적) 내보내기
DELETE FROM `writer_mem`;
/*!40000 ALTER TABLE `writer_mem` DISABLE KEYS */;
INSERT INTO `writer_mem` (`mem_idx`, `mem_email`, `mem_pwd`, `mem_nickname`, `mem_type`, `mem_regdate`, `mem_update`) VALUES
	(9, 'john.lim@hotmail.co.kr', '*A4B6157319038724E3560894F7F932C8886EBFCF', '관우림', 'local', '2019-06-28 15:26:13', '2019-07-02 17:57:24'),
	(10, 'zanki99@nate.com', '*64E794948AA6B2E33A68E82A6AA35C400090D2BD', '엠마톰슨', 'local', '2019-06-28 15:27:03', '2019-07-29 13:01:01'),
	(58, 'zanki99@naver.com', '*A4B6157319038724E3560894F7F932C8886EBFCF', '박해일', 'local', '2019-07-09 13:41:19', '2019-07-29 13:00:49'),
	(62, 'zanki99@naver.com', '*83534A63B3C529A31F1B60281CFA54B17A01468A', '존림', 'kakao', '2019-07-10 01:45:43', '2019-07-10 01:45:43'),
	(63, 'zanki99@hanmail.com', '*A4B6157319038724E3560894F7F932C8886EBFCF', '송강호', 'local', '2019-07-15 16:18:04', '2019-07-29 12:58:50');
/*!40000 ALTER TABLE `writer_mem` ENABLE KEYS */;

-- 테이블 writeproject.writer_post 구조 내보내기
CREATE TABLE IF NOT EXISTS `writer_post` (
  `p_idx_pk` int(11) NOT NULL AUTO_INCREMENT,
  `p_mem_idx_fk` int(11) NOT NULL,
  `p_material_idx_fk` int(11) DEFAULT 0,
  `p_material` varchar(100) NOT NULL,
  `p_content` varchar(5000) NOT NULL,
  `p_regdate` datetime NOT NULL DEFAULT current_timestamp(),
  `p_update` datetime NOT NULL DEFAULT current_timestamp(),
  `p_public` varchar(20) NOT NULL DEFAULT 'y',
  `p_likeit` int(11) NOT NULL DEFAULT 0,
  `p_hit` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`p_idx_pk`),
  KEY `p_user_idx_fk` (`p_mem_idx_fk`),
  KEY `p_material_idx_fk` (`p_material_idx_fk`),
  CONSTRAINT `p_material_idx_fk` FOREIGN KEY (`p_material_idx_fk`) REFERENCES `writer_post_materiallist` (`material_idx_pk`),
  CONSTRAINT `p_user_idx_fk` FOREIGN KEY (`p_mem_idx_fk`) REFERENCES `writer_mem` (`mem_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

-- 테이블 데이터 writeproject.writer_post:~23 rows (대략적) 내보내기
DELETE FROM `writer_post`;
/*!40000 ALTER TABLE `writer_post` DISABLE KEYS */;
INSERT INTO `writer_post` (`p_idx_pk`, `p_mem_idx_fk`, `p_material_idx_fk`, `p_material`, `p_content`, `p_regdate`, `p_update`, `p_public`, `p_likeit`, `p_hit`) VALUES
	(15, 63, 1, '비가 온다', '비가 오려나', '2019-07-16 13:08:57', '2019-07-16 13:08:57', 'y', 0, 0),
	(29, 62, 4, '생일', '오늘은 유정이 생일', '2019-07-18 12:32:37', '2019-07-22 13:39:11', 'y', 0, 0),
	(33, 62, 6, '밤', '집 밖은 위험해!', '2019-07-19 14:24:33', '2019-07-19 14:24:33', 'y', 0, 0),
	(41, 62, 8, '사람', '사람이 먼저다', '2019-07-20 14:03:13', '2019-07-20 14:03:13', 'n', 0, 0),
	(44, 62, 9, '당신이 머무는 곳', '스타벅스다.', '2019-07-22 13:21:22', '2019-07-22 13:45:47', 'y', 0, 0),
	(45, 62, 9, '당신이 머무는 곳', '푹신한 의자', '2019-07-22 14:05:04', '2019-07-22 14:57:31', 'y', 0, 0),
	(46, 62, 11, '빈', '빈 나무 껍데기가 하늘을 기다린다.', '2019-07-23 12:41:57', '2019-07-23 14:49:40', 'y', 0, 0),
	(47, 62, 11, '빈', '빈 도시에 노란 해가 들었다.', '2019-07-23 15:22:23', '2019-07-23 15:22:23', 'y', 0, 0),
	(49, 62, 11, '빈', '접시가 비었다.', '2019-07-23 15:52:40', '2019-07-23 15:52:40', 'y', 0, 0),
	(51, 62, 14, '밥', '또 밥 때가 되었다.', '2019-07-24 13:37:36', '2019-07-24 13:37:36', 'y', 0, 0),
	(52, 62, 14, '밥', '밥', '2019-07-24 16:09:00', '2019-07-24 16:09:00', 'y', 0, 0),
	(53, 62, 15, '삶', '삶은 계란은 깨지지 않는다.', '2019-07-24 17:45:19', '2019-07-24 17:45:19', 'y', 0, 0),
	(54, 62, 15, '삶', '사랑을 잃고 나는 쓰네     잘 있거라, 짧았던 밤들아  창밖을 떠돌던 겨울 안개들아  아무것도 모르던 촛불들아, 잘 있거라  공포를 기다리던 흰 종이들아  망설임을 대신하던 눈물들아  잘 있거라, 더 이상 내 것이 아닌 열망들아     장님처럼 나 이제 더듬거리며 문을 잠그네  가엾은 내 사랑 빈집에 갇혔네사랑을 잃고 나는 쓰네     잘 있거라, 짧았던 밤들아  창밖을 떠돌던 겨울 안개들아  아무것도 모르던 촛불들아, 잘 있거라  공포를 기다리던 흰 종이들아  망설임을 대신하던 눈물들아  잘 있거라, 더 이상 내 것이 아닌 열망들아     장님처럼 나 이제 더듬거리며 문을 잠그네  가엾은 내 사랑 빈집에 갇혔네사랑을 잃고 나는 쓰네     잘 있거라, 짧았던 밤들아  창밖을 떠돌던 겨울 안개들아  아무것도 모르던 촛불들아, 잘 있거라  공포를 기다리던 흰 종이들아  망설임을 대신하던 눈물들아  잘 있거라, 더 이상 내 것이 아닌 열망들아     장님처럼 나 이제 더듬거리며 문을 잠그네  가엾은 내 사랑 빈집에 갇혔네', '2019-07-24 18:20:30', '2019-07-24 18:20:30', 'y', 0, 0),
	(55, 62, 15, '삶', '삶은 가볍지 않지만 무겁지도 않다.', '2019-07-24 19:30:45', '2019-07-24 19:30:45', 'y', 0, 0),
	(56, 62, 18, '숨', '숨을 쉰다.', '2019-07-25 18:09:12', '2019-07-25 18:09:12', 'y', 0, 0),
	(59, 62, 19, '창문', '창문에 비가 앉았다.', '2019-07-26 15:14:09', '2019-07-26 15:14:09', 'y', 0, 0),
	(60, 62, 21, '월요일', '월요일엔 비가 오지 않았으면.', '2019-07-29 13:30:33', '2019-07-29 13:30:33', 'y', 0, 0),
	(61, 9, 21, '월요일', '흑당밀크티', '2019-07-29 16:33:25', '2019-07-29 16:33:25', 'y', 0, 0);
/*!40000 ALTER TABLE `writer_post` ENABLE KEYS */;

-- 테이블 writeproject.writer_post_materiallist 구조 내보내기
CREATE TABLE IF NOT EXISTS `writer_post_materiallist` (
  `material_idx_pk` int(11) NOT NULL AUTO_INCREMENT,
  `material_title` varchar(20) NOT NULL,
  `material_distribute_date` date DEFAULT NULL,
  `material_distribute_time` time DEFAULT NULL,
  `material_regdate` datetime DEFAULT current_timestamp(),
  `material_update` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`material_idx_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- 테이블 데이터 writeproject.writer_post_materiallist:~22 rows (대략적) 내보내기
DELETE FROM `writer_post_materiallist`;
/*!40000 ALTER TABLE `writer_post_materiallist` DISABLE KEYS */;
INSERT INTO `writer_post_materiallist` (`material_idx_pk`, `material_title`, `material_distribute_date`, `material_distribute_time`, `material_regdate`, `material_update`) VALUES
	(1, '비가 온다', '2019-07-17', '11:12:15', '2019-07-15 16:56:31', '2019-07-15 16:56:31'),
	(3, '수요일', '2019-07-17', '18:00:00', '2019-07-17 11:43:53', '2019-07-17 11:43:53'),
	(4, '생일', '2019-07-20', '17:40:00', '2019-07-18 12:30:24', '2019-07-18 12:30:24'),
	(5, '밤', '2019-07-18', '17:45:00', '2019-07-18 16:52:36', '2019-07-18 16:52:36'),
	(6, '휴가', '2019-07-19', '12:00:00', '2019-07-19 12:01:56', '2019-07-19 12:01:56'),
	(7, '커피', '2019-07-19', '18:00:00', '2019-07-19 12:02:17', '2019-07-19 12:02:17'),
	(8, '사람', '2019-07-20', '12:00:00', '2019-07-20 13:15:38', '2019-07-20 13:15:38'),
	(9, '당신이 머무는 곳', '2019-07-22', '12:00:00', '2019-07-22 11:19:48', '2019-07-22 11:19:48'),
	(10, '당신의 이름', '2019-07-22', '18:00:00', '2019-07-22 11:19:48', '2019-07-22 11:19:48'),
	(11, '빈', '2019-07-23', '18:30:00', '2019-07-23 00:59:10', '2019-07-23 00:59:10'),
	(12, '노란', '2019-07-23', '00:30:00', '2019-07-23 00:59:49', '2019-07-23 00:59:49'),
	(13, '반', '2019-07-23', '14:30:00', '2019-07-23 00:59:49', '2019-07-23 00:59:49'),
	(14, '밥', '2019-07-24', '12:00:00', '2019-07-24 13:01:15', '2019-07-24 13:01:15'),
	(15, '삶', '2019-07-24', '18:00:00', '2019-07-24 13:01:36', '2019-07-24 13:01:36'),
	(16, '공작', '2019-07-25', '01:00:00', '2019-07-25 00:02:10', '2019-07-25 00:02:10'),
	(17, '살다', '2019-07-25', '12:00:00', '2019-07-25 00:02:10', '2019-07-25 00:02:10'),
	(18, '숨', '2019-07-25', '18:00:00', '2019-07-25 00:02:10', '2019-07-25 00:02:10'),
	(19, '창문', '2019-07-26', '12:00:00', '2019-07-26 12:21:01', '2019-07-26 12:21:01'),
	(20, '집', '2019-07-26', '18:00:00', '2019-07-26 12:21:01', '2019-07-26 12:21:01'),
	(21, '월요일', '2019-07-29', '11:00:00', '2019-07-29 11:32:36', '2019-07-29 11:32:36'),
	(22, '여름', '2019-07-30', '13:00:00', '2019-07-29 11:33:12', '2019-07-29 11:33:12'),
	(23, '그 해', '2019-07-29', '18:00:00', '2019-07-29 11:33:12', '2019-07-29 11:33:12');
/*!40000 ALTER TABLE `writer_post_materiallist` ENABLE KEYS */;

-- 테이블 writeproject.writer_subscribe 구조 내보내기
CREATE TABLE IF NOT EXISTS `writer_subscribe` (
  `s_idx_pk` int(11) NOT NULL AUTO_INCREMENT,
  `s_memidx_fk` int(11) NOT NULL,
  `s_subsmemidx_fk` int(11) NOT NULL,
  `s_regdate` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`s_idx_pk`),
  KEY `s_memidx` (`s_memidx_fk`),
  KEY `s_subsmemidx` (`s_subsmemidx_fk`),
  CONSTRAINT `writer_subscribe_ibfk_1` FOREIGN KEY (`s_memidx_fk`) REFERENCES `writer_mem` (`mem_idx`),
  CONSTRAINT `writer_subscribe_ibfk_2` FOREIGN KEY (`s_subsmemidx_fk`) REFERENCES `writer_mem` (`mem_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

-- 테이블 데이터 writeproject.writer_subscribe:~5 rows (대략적) 내보내기
DELETE FROM `writer_subscribe`;
/*!40000 ALTER TABLE `writer_subscribe` DISABLE KEYS */;
INSERT INTO `writer_subscribe` (`s_idx_pk`, `s_memidx_fk`, `s_subsmemidx_fk`, `s_regdate`) VALUES
	(11, 10, 62, '2019-07-29 15:02:30'),
	(13, 9, 10, '2019-07-29 15:51:17'),
	(43, 62, 62, '2019-07-30 13:05:30'),
	(44, 62, 63, '2019-07-30 13:05:48'),
	(45, 62, 9, '2019-07-30 16:03:29');
/*!40000 ALTER TABLE `writer_subscribe` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
