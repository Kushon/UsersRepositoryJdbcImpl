create table driver(
                       id serial primary key,
                       first_name char(20),
                       last_name char(30),
                       age integer,
                       rating integer check(rating>=1 and rating<=5),
                       sex varchar(6),
                       driving_experience integer check(driving_experience>=2)
);



INSERT INTO driver (first_name, last_name, age, rating, sex, driving_experience)
VALUES
    ('Артем', 'Сидоров', 28, 5, 'Male', 6),
    ('Мария', 'Иванова', 34, 4, 'Female', 10),
    ('Дмитрий', 'Петров', 41, 3, 'Male', 15),
    ('Анна', 'Смирнова', 29, 2, 'Female', 5),
    ('Игорь', 'Васильев', 50, 5, 'Male', 25),
    ('Екатерина', 'Кузнецова', 37, 4, 'Female', 12),
    ('Владимир', 'Попов', 33, 1, 'Male', 3),
    ('Ольга', 'Соколова', 45, 5, 'Female', 20),
    ('Алексей', 'Лебедев', 26, 3, 'Male', 4),
    ('Светлана', 'Козлова', 40, 4, 'Female', 18);
