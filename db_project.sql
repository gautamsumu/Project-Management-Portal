-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 08, 2012 at 08:01 AM
-- Server version: 5.5.28
-- PHP Version: 5.3.10-1ubuntu3.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `db_project`
--

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE IF NOT EXISTS `employee` (
  `employee_id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `post_id` int(5) NOT NULL,
  `department` varchar(15) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `address` text,
  `email_id` text,
  `phone` int(15) DEFAULT NULL,
  PRIMARY KEY (`employee_id`),
  KEY `post_id` (`post_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`employee_id`, `name`, `post_id`, `department`, `dob`, `address`, `email_id`, `phone`) VALUES
(0, 'Dummy Employee', 1, NULL, NULL, NULL, NULL, NULL),
(1, 'Uday K Shah', 3, 'Security', '1993-09-09', '163, hostel 4', 'uday@mail.com', 18450),
(2, 'Anirudh D', 3, 'Coding', '1993-11-11', '163, hostel 4', 'anirudh@mail.com', 40989),
(3, 'Gautam Sumu', 2, 'Management', '1993-07-21', '158, hostel 4', 'sumu@mail.com', 18280),
(4, 'Chandan Kumar', 1, 'Coding', '1992-11-18', '158, hostel 5', 'chandan@mail.com', 18275);

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE IF NOT EXISTS `login` (
  `employee_id` int(10) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`employee_id`, `password`) VALUES
(1, 'password'),
(2, 'anirudh'),
(3, 'password'),
(4, 'password');

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE IF NOT EXISTS `message` (
  `message_id` int(10) NOT NULL AUTO_INCREMENT,
  `from_id` int(10) NOT NULL,
  `to_id` int(10) NOT NULL,
  `message` text,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `flag` int(5) NOT NULL DEFAULT '1',
  PRIMARY KEY (`message_id`),
  KEY `from_id` (`from_id`,`to_id`),
  KEY `to_id` (`to_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`message_id`, `from_id`, `to_id`, `message`, `time`, `flag`) VALUES
(1, 1, 2, 'Hi, how are you?', '2012-11-07 23:44:05', 1),
(2, 1, 3, 'Hello!', '2012-11-08 00:16:17', 1),
(4, 4, 1, 'Status of project?', '2012-11-08 00:18:02', 1);

-- --------------------------------------------------------

--
-- Table structure for table `position`
--

CREATE TABLE IF NOT EXISTS `position` (
  `post_id` int(5) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  `salary` int(10) NOT NULL,
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `position`
--

INSERT INTO `position` (`post_id`, `name`, `salary`) VALUES
(1, 'CEO', 10000),
(2, 'Manager', 1000),
(3, 'Engineer', 1000);

-- --------------------------------------------------------

--
-- Table structure for table `project`
--

CREATE TABLE IF NOT EXISTS `project` (
  `project_id` int(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(40) NOT NULL,
  `budget` int(11) DEFAULT NULL,
  `deadline` date DEFAULT NULL,
  `status` varchar(1) NOT NULL DEFAULT 'p',
  `emp_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  KEY `emp_id` (`emp_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `project`
--

INSERT INTO `project` (`project_id`, `title`, `budget`, `deadline`, `status`, `emp_id`) VALUES
(1, 'Windows 7', 8000, '2012-11-20', 'c', 1),
(2, 'Windows 8', 10000, '2012-11-21', 'p', 1),
(3, 'Ubuntu 12.04', 7000, '2012-11-18', 'c', 2),
(4, 'Ubuntu 12.10', 10000, '2012-11-22', 'c', 2),
(12, 'Sumo', 0, '2022-12-31', 'p', 2);

-- --------------------------------------------------------

--
-- Table structure for table `task`
--

CREATE TABLE IF NOT EXISTS `task` (
  `task_id` int(10) NOT NULL AUTO_INCREMENT,
  `task_title` text,
  `project_id` int(10) NOT NULL,
  `employee_id` int(10) NOT NULL,
  `deadline` date NOT NULL,
  `status` varchar(1) NOT NULL DEFAULT 'p',
  PRIMARY KEY (`task_id`),
  KEY `employee_id` (`employee_id`),
  KEY `project_id` (`project_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `task`
--

INSERT INTO `task` (`task_id`, `task_title`, `project_id`, `employee_id`, `deadline`, `status`) VALUES
(1, 'Eat', 1, 1, '2012-11-15', 'c'),
(2, 'Drink', 1, 1, '2012-11-17', 'p'),
(3, 'Movie', 2, 1, '2012-11-17', 'c'),
(4, 'Dance', 2, 1, '2012-11-19', 'p'),
(6, 'Bathing', 3, 2, '2012-11-14', 'c'),
(7, 'Brush', 4, 2, '2012-11-17', 'c'),
(8, 'Do nothig!', 4, 2, '2012-11-19', 'c'),
(9, 'sfdfds', 1, 2, '2012-11-10', 'p'),
(11, 'wow', 12, 2, '2012-11-13', 'p');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `position` (`post_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `login`
--
ALTER TABLE `login`
  ADD CONSTRAINT `login_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `message_ibfk_1` FOREIGN KEY (`from_id`) REFERENCES `employee` (`employee_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `message_ibfk_2` FOREIGN KEY (`to_id`) REFERENCES `employee` (`employee_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `project`
--
ALTER TABLE `project`
  ADD CONSTRAINT `project_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`employee_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `task`
--
ALTER TABLE `task`
  ADD CONSTRAINT `task_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `task_ibfk_2` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
