create database jukebox;
use jukebox;
create table userdetails(
user_id int primary key auto_increment not null,
user_name varchar(20) not null,
user_email varchar(50) not null,
contact bigint not null,
user_pwd varchar(20) not null unique
);
alter table userdetails auto_increment=100;
create table playlist(
playlist_id int primary key auto_increment not null,
playlist_name varchar(20) not null,
playlist_created_date datetime default now(),
user_id int, foreign key (user_id) references userdetails (user_id)
);
alter table playlist auto_increment=100;
drop table playlist;


create table genre(
genre_id int primary key auto_increment not null,
genre_type varchar(20) not null
);
insert into genre(genre_type) values('fast');
insert into genre(genre_type) values('TalkShow');
insert into genre(genre_type) values('Festive');
insert into genre(genre_type) values('Romantic');

alter table genre auto_increment=100;


create table artist(
artist_id int primary key auto_increment not null,
artist_name varchar(20) not null,
genre_id int, foreign key (genre_id) references genre(genre_id)
);
alter table artist auto_increment=100;

insert into artist(artist_name,genre_id) values('Arjith',100);
insert into artist(artist_name,genre_id) values('Vinneth',102);
insert into artist(artist_name,genre_id) values('Vijay Yesudas',103);


create table song(
song_id int primary key auto_increment not null,
song_name varchar(20) not null,
duration time not null,
artist_id int, foreign key (artist_id) references artist (artist_id)
);
insert into song(song_name,duration,artist_id) values('Makhna','00:01:17',100);
insert into song(song_name,duration,artist_id) values('Onakka','00:02:10',101);
insert into song(song_name,duration,artist_id) values('Malare','00:05:13',102);

alter table song auto_increment=1;
create table songplaylist(
songpl_id int primary key auto_increment not null,
songpl_name varchar(20) not null,
playlist_id int, foreign key (playlist_id) references playlist (playlist_id),
song_id int, foreign key (song_id) references song (song_id)
);
alter table songplaylist auto_increment=1;
create table podcast(
podcast_id int primary key auto_increment not null,
podcast_name varchar(20) not null,
duration timestamp not null,
no_of_episodes int not null,
genre_id int, foreign key (genre_id) references genre(genre_id)
);
alter table podcast auto_increment=1;

insert into podcast(podcast_name,duration,no_of_episodes,genre_id) values('The Real Talk','00:20:17',2,101);

create table podcastplaylist(
podcastpl_id int primary key auto_increment not null,
podcastpl_name varchar(20) not null,
playlist_id int, foreign key (playlist_id) references playlist (playlist_id),
podcast_id int, foreign key (podcast_id) references podcast (podcast_id)
);
alter table podcastplaylist auto_increment=1;