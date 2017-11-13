INSERT INTO s_role (id, description, name) VALUES
  ('user', 'USER', 'User'),
  ('admin', 'ADMIN', 'Admin');

INSERT INTO s_user (id, active, username, id_role) VALUES
  ('u001', true, 'user', 'user');

INSERT INTO s_user (id, active, username, id_role) VALUES
  ('u002', true, 'admin', 'admin');

INSERT INTO s_user_password (id_user, password) VALUES
  ('u001', '123');

INSERT INTO s_user_password (id_user, password) VALUES
  ('u002', '1234');
