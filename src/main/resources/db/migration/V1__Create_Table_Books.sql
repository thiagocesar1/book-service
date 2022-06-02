CREATE TABLE book (
  id SERIAL PRIMARY KEY,
  author varchar(100),
  launch_date timestamp NOT NULL,
  price decimal(65,2) NOT NULL,
  title varchar(255)
);
