DROP TABLE IF EXISTS user;

CREATE TABLE user (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(250) UNIQUE NOT NULL,
  password VARCHAR(250) NOT NULL);
/*
user1, password1
user2, password2
 */
  INSERT INTO user (username, password) VALUES
  ('user1', '$2y$12$IYEe5J5vkSthS9ZMT7ANH.KY82hFn98hF4PY532ln2aDPv2teyaeO'),
  ('user2', '$2y$12$hzsMyeAmmE.iikXRK/6KGOxwt.hM2VhPDTXQHhOdbQpmntUvgmroW');