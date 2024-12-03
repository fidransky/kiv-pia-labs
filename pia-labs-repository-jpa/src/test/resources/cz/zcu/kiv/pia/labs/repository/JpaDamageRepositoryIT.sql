-- this script is used to populate database thanks to @Sql annotation at JpaDamageRepositoryIT test class

INSERT INTO `user` (`id`, `name`, `email_address`, `role`) VALUES
('65b032ec-b0e3-11ef-801a-0242ac130002', 'John Doe', 'john.doe@example.com', 0),
('71936127-b0e3-11ef-801a-0242ac130002', 'Jane Doe', 'jane.doe@example.com', 1),
('7e5162d3-b0e3-11ef-801a-0242ac130002', 'Jack Doe', 'jack.doe@example.com', 1);

INSERT INTO `damage` (`id`, `insured_user_id`, `impaired_user_id`, `timestamp`, `latitude`, `longitude`, `description`, `state`) VALUES
(UUID(), '65b032ec-b0e3-11ef-801a-0242ac130002', '71936127-b0e3-11ef-801a-0242ac130002', '2024-08-13 21:53:12.672', 49.7339986, 13.4018103, 'I got hit by a motorbike', 0),
(UUID(), '65b032ec-b0e3-11ef-801a-0242ac130002', '7e5162d3-b0e3-11ef-801a-0242ac130002', '2022-06-26 14:31:01.243', 49.7269708, 13.3516872, 'server room got flooded', 0);