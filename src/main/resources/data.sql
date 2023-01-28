/*
-- users
INSERT INTO public.USERS (EMAIL, LOGIN, NAME, BIRTHDAY) VALUES ('user1@mail.ru', 'user-1', 'name-1', '2022-12-01');
INSERT INTO public.USERS (EMAIL, LOGIN, NAME, BIRTHDAY) VALUES ('user2@mail.ru', 'user-2', 'name-2', '2020-11-02');
INSERT INTO public.USERS (EMAIL, LOGIN, NAME, BIRTHDAY) VALUES ('user3@mail.ru', 'user-3', 'name-3', '2019-10-03');
INSERT INTO public.USERS (EMAIL, LOGIN, NAME, BIRTHDAY) VALUES ('user4@mail.ru', 'user-4', 'name-4', '2018-09-02');
INSERT INTO public.USERS (EMAIL, LOGIN, NAME, BIRTHDAY) VALUES ('user5@mail.ru', 'user-5', 'name-5', '2018-08-01');
INSERT INTO public.USERS (EMAIL, LOGIN, NAME, BIRTHDAY) VALUES ('user6@mail.ru', 'user-6', 'name-6', '2017-07-30');
-- mpa
INSERT INTO public.MPA  (NAME) VALUES ('G'), ('PG'), ('PG-13'), ('R'), ('NC-17');
-- films
INSERT INTO public.FILMS  (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID) VALUES ('Он дракон', 'Сказочка про дракона', '2020-01-01', 120, 1);
INSERT INTO public.FILMS  (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID) VALUES ('Терминатор', 'Фантастика про будущее', '1994-06-11', 120, 2);
INSERT INTO public.FILMS  (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID) VALUES ('Олд бой', 'Корейское кино', '2003-08-12', 145, 3);
-- genres
INSERT INTO public.GENRE  (NAME) VALUES ('Комедия'),('Драма'), ('Мультфильм'), ('Триллер'), ('Документальный'), ('Боевик');
-- films-genre
INSERT INTO public.FILM_GENRE  (FG_FILM_ID, FG_GENRE_ID) VALUES (1, 4);
INSERT INTO public.FILM_GENRE  (FG_FILM_ID, FG_GENRE_ID) VALUES (1, 5);
INSERT INTO public.FILM_GENRE  (FG_FILM_ID, FG_GENRE_ID) VALUES (1, 6);
INSERT INTO public.FILM_GENRE  (FG_FILM_ID, FG_GENRE_ID) VALUES (2, 1);
INSERT INTO public.FILM_GENRE  (FG_FILM_ID, FG_GENRE_ID) VALUES (2, 3);
INSERT INTO public.FILM_GENRE  (FG_FILM_ID, FG_GENRE_ID) VALUES (3, 1);
INSERT INTO public.FILM_GENRE  (FG_FILM_ID, FG_GENRE_ID) VALUES (3, 5);

-- friends
INSERT INTO public.FRIENDS  (FRIEND_ONE, FRIEND_TWO) VALUES (1,2);
INSERT INTO public.FRIENDS  (FRIEND_ONE, FRIEND_TWO) VALUES (1,3);
INSERT INTO public.FRIENDS  (FRIEND_ONE, FRIEND_TWO) VALUES (1,4);
INSERT INTO public.FRIENDS  (FRIEND_ONE, FRIEND_TWO) VALUES (1,5);
INSERT INTO public.FRIENDS  (FRIEND_ONE, FRIEND_TWO) VALUES (2,3);
INSERT INTO public.FRIENDS  (FRIEND_ONE, FRIEND_TWO) VALUES (2,4);
INSERT INTO public.FRIENDS  (FRIEND_ONE, FRIEND_TWO) VALUES (3,1);
INSERT INTO public.FRIENDS  (FRIEND_ONE, FRIEND_TWO) VALUES (3,4);
INSERT INTO public.FRIENDS  (FRIEND_ONE, FRIEND_TWO) VALUES (3,5);
INSERT INTO public.FRIENDS  (FRIEND_ONE, FRIEND_TWO) VALUES (3,2);
*/

-- mpa
INSERT INTO public.MPA  (NAME) VALUES ('G'), ('PG'), ('PG-13'), ('R'), ('NC-17');
-- genres
INSERT INTO public.GENRE  (NAME) VALUES ('Комедия'),('Драма'), ('Мультфильм'), ('Триллер'), ('Документальный'), ('Боевик');
