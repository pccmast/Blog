use blogdata;

create table users
(
    id                int PRIMARY KEY AUTO_INCREMENT,
    username          varchar(32) not null unique,
    encryptedPassword varchar(64) not null,
    createdAt         timestamp,
    updatedAt         timestamp,
    avatar            varchar(100)
);

create table blog
(
    id                  int primary key AUTO_INCREMENT,
    title               varchar(100) not null,
    content_description varchar(100),
    content             text,
    user_id             int,
    createdAt           datetime,
    updatedAt           datetime,
    atIndex             boolean default true,
    FOREIGN KEY (user_id) references users (id)
);