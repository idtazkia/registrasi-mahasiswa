INSERT INTO s_permission (id, permission_value, permission_label) VALUES
  ('editmaster', 'EDIT_MASTER', 'Edit Master'),
  ('viewmaster', 'VIEW_MASTER', 'View Master');

INSERT INTO s_role (id, description, name) VALUES
  ('user', 'USER', 'User'),
  ('admin', 'ADMIN', 'Admin');

INSERT INTO s_role_permission (id_role, id_permission) VALUES
  ('user', 'viewpendaftaran'),
  ('user', 'editpendaftaran'),
  ('admin', 'viewmaster'),
  ('admin', 'editmaster');

INSERT INTO s_user (id, active, username, id_role) VALUES
  ('u001', true, 'user', 'user');

INSERT INTO s_user (id, active, username, id_role) VALUES
  ('u002', true, 'admin', 'admin');

INSERT INTO s_user_password (id_user, password) VALUES
  ('u001', '123');

INSERT INTO s_user_password (id_user, password) VALUES
  ('u002', '1234');

-- "SELECT u.username,ur.name AS authority FROM s_user u JOIN s_role ur ON  u.id_role = ur.id\n" +
-- "where u.username = ?";