INSERT INTO s_permission (id, permission_value, permission_label) VALUES
  ('editmaster', 'EDIT_MASTER', 'Edit Master'),
  ('viewmaster', 'VIEW_MASTER', 'View Master'),
  ('editpeserta', 'EDIT_PESERTA', 'Edit Peserta'),
  ('viewpeserta', 'VIEW_PESERTA', 'View Peserta');

INSERT INTO s_role (id, description, name) VALUES
  ('peserta', 'PESERTA', 'Peserta'),
  ('humas', 'HUMAS', 'Humas');

INSERT INTO s_role_permission (id_role, id_permission) VALUES
  ('peserta', 'viewpeserta'),
  ('peserta', 'editpeserta'),
  ('humas', 'viewmaster'),
  ('humas', 'editmaster');

INSERT INTO s_user (id, active, username, id_role) VALUES
  ('u001', true, 'user', 'peserta');

INSERT INTO s_user (id, active, username, id_role) VALUES
  ('u002', true, 'admin', 'humas');

INSERT INTO s_user_password (id_user, password) VALUES
  ('u001', '123');

INSERT INTO s_user_password (id_user, password) VALUES
  ('u002', '1234');