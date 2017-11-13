CREATE TABLE s_user (
  id       VARCHAR(36),
  username VARCHAR(255) NOT NULL,
  active   BOOLEAN      NOT NULL,
  id_role  VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (username),
  FOREIGN KEY (id_role) REFERENCES s_role (id)
);


create table s_user_password (
  id_user varchar(36) not null,
  password varchar(255) not null,
  primary key (id_user),
  foreign key (id_user) references s_user (id)
);

CREATE TABLE s_role (
  id          VARCHAR(255) NOT NULL,
  description VARCHAR(255) DEFAULT NULL,
  name        VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (name)
);

SELECT u.username,ur.name AS authority
FROM s_user u JOIN s_role ur ON  u.id_role = ur.id
where u.username = 'user';

select u.username as username,p.password as password, active
from s_user u
inner join s_user_password p on p.id_user = u.id
where username = 'user';

select u.username, p.permission_value as authority
from s_user u
inner join s_role r on u.id_role = r.id
inner join s_role_permission rp on rp.id_role = r.id
inner join s_permission p on rp.id_permission = p.id
where u.username = 'user'