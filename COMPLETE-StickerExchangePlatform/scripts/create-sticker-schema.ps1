$schema=@'
CREATE DATABASE IF NOT EXISTS `StickerDB` DEFAULT CHARACTER SET latin1 COLLATE latin1_general_ci;
USE `StickerDB`;

drop table if exists Double_Sticker;
drop table if exists Requests;
drop table if exists My_Sticker;

drop table if exists Sticker;
create table Sticker (id int auto_increment primary key,
                      Number int,
                      Sector varchar(50),
                      Description varchar(50),
                      How_Often int);

drop table if exists User;
create table User (
                    Name varchar(50) primary key,
                    Password varchar(50),
                    Address varchar(50),
                    Country varchar(50),
                    Region varchar(50));

drop table if exists My_Sticker;
create table My_Sticker (id int auto_increment primary key,
                            User varchar(50),
                            Sticker int,
                            FOREIGN KEY (User)
                                REFERENCES User(Name)
                                ON DELETE CASCADE,
                            FOREIGN KEY (Sticker)
                                REFERENCES Sticker(id)
                                ON DELETE CASCADE);

drop table if exists Double_Sticker;
create table Double_Sticker (id int auto_increment primary key,
                                User varchar(50),
                                Sticker int,
                                FOREIGN KEY (User)
                                    REFERENCES User(Name)
                                    ON DELETE CASCADE,
                                FOREIGN KEY (Sticker)
                                    REFERENCES Sticker(id)
                                    ON DELETE CASCADE);

drop table if exists Requests;
create table Requests (id int auto_increment primary key,
                        Requesting_User varchar(50),
                        Asked_User varchar(50),
                        Sticker_Give int,
                        Sticker_Wanted int,
                        FOREIGN KEY (Requesting_User)
                                    REFERENCES User(Name)
                                    ON DELETE CASCADE,
                        FOREIGN KEY (Asked_User)
                                    REFERENCES User(Name)
                                    ON DELETE CASCADE,
                        FOREIGN KEY (Sticker_Give)
                                    REFERENCES Sticker(id)
                                    ON DELETE CASCADE,
                        FOREIGN KEY (Sticker_Wanted)
                                    REFERENCES Sticker(id)
                                    ON DELETE CASCADE
                        );

'@

echo $schema | docker exec -i mysql mysql
