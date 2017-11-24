INSERT INTO s_permission (id, permission_value, permission_label) VALUES
  ('editfinance', 'EDIT_FINANCE', 'Edit Finance'),
  ('viewfinance', 'VIEW_FINANCE', 'View Finance');

INSERT INTO s_role (id, description, name) VALUES
  ('finance','FINANCE','Finance');

INSERT INTO s_role_permission (id_role, id_permission) VALUES
  ('finance', 'viewfinance'),
  ('finance', 'editfinance');

INSERT INTO s_user (id, active, username, id_role) VALUES
  ('u003', true, 'finance', 'finance');

INSERT INTO s_user_password (id, id_user, password) VALUES
  ('up003', 'u003', '$2a$17$Mhfv.hlqIybDHWqAaTMU/.PKi8RDntt6xe9pTMGQLfnW3phTlhROm');
