$schema=@'

USE `StickerDB`;

INSERT INTO Sticker (Number, Sector, Description, How_Often)
VALUES  (1, 'Oesterreich', 'Toller Tormann', 1),
  (2, 'Oesterreich', 'Toller Verteidiger', 1),
  (3, 'Oesterreich', 'Toller Verteidiger', 1),
  (4, 'Oesterreich', 'Toller Verteidiger', 1),
  (5, 'Oesterreich', 'Toller Mittelfeldspieler', 1),
  (6, 'Oesterreich', 'Toller Mittelfeldspieler', 1),
  (7, 'Oesterreich', 'Toller Mittelfeldspieler', 1),
  (8, 'Oesterreich', 'Toller Mittelfeldspieler', 1),
  (9, 'Oesterreich', 'Toller Stürmer', 1),
  (10, 'Oesterreich', 'Toller Stürmer',1);

INSERT INTO User (Name, Password, Address, Country, Region) VALUES
  ('User1', 'abcd', 'Hagenberg 1', 'Oesterreich', 'Region1'),
  ('User2', 'abcd', 'Hagenberg 2', 'Deutschland', 'Region2'),
  ('User3', 'abcd', 'Hagenberg 3', 'Oesterreich', 'Region1');

INSERT INTO My_Sticker (User, Sticker) VALUES
  ('User1', 1),
  ('User1', 2),
  ('User2', 3),
  ('User2', 4),
  ('User3', 5),
  ('User3', 6),
  ('User3', 7);

INSERT INTO Double_Sticker (User, Sticker) VALUES
  ('User1', 1),
('User1', 2),
('User2', 3),
('User2', 4),
('User3', 5),
('User3', 6),
('User3', 7);




'@

echo $schema | docker exec -i mysql mysql
