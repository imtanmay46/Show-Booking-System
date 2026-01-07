TRUNCATE TABLE
    bookings,
    show_seats,
    shows,
    screens,
    theatres,
    cities,
    users
RESTART IDENTITY CASCADE;

SELECT 'DATA.SQL IS RUNNING';

INSERT INTO cities (id, name, created_at, updated_at)
VALUES
    (1, 'Delhi', now(), now()),
    (2, 'Mumbai', now(), now());


INSERT INTO theatres (id, name, city_id, created_at, updated_at)
VALUES
    (1, 'PVR Saket', 1, now(), now()),
    (2, 'INOX Andheri', 2, now(), now());


INSERT INTO screens (id, name, theatre_id, created_at, updated_at)
VALUES
    (1, 'Screen 1', 1, now(), now()),
    (2, 'Screen 2', 1, now(), now()),
    (3, 'Audi 1', 2, now(), now());


INSERT INTO shows (id, movie_name, screen_id, show_time, created_at, updated_at)
VALUES
    (1, 'Interstellar', 1, '2026-01-10 18:30:00', now(), now()),
    (2, 'Inception', 2, '2026-01-10 21:30:00', now(), now()),
    (3, 'Dune: Part Two', 3, '2026-01-11 19:00:00', now(), now());


INSERT INTO show_seats
(id, show_id, seat_number, price, status, created_at, updated_at)
VALUES
-- Show 1
(1, 1, 'A1', 250, 'AVAILABLE', now(), now()),
(2, 1, 'A2', 250, 'AVAILABLE', now(), now()),
(3, 1, 'B1', 200, 'AVAILABLE', now(), now()),

-- Show 2
(4, 2, 'A1', 300, 'AVAILABLE', now(), now()),
(5, 2, 'A2', 300, 'AVAILABLE', now(), now()),

-- Show 3
(6, 3, 'A1', 350, 'AVAILABLE', now(), now()),
(7, 3, 'A2', 350, 'AVAILABLE', now(), now());


INSERT INTO users
(id, email, password, role, created_at, updated_at)
VALUES
    (1, 'user@test.com',  '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Q9Pq2B5Gx9lZP0z5Qz9zR8K9QJQ0y', 'USER', now(), now()),
    (2, 'admin@test.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Q9Pq2B5Gx9lZP0z5Qz9zR8K9QJQ0y', 'ADMIN', now(), now());


INSERT INTO bookings
(id, user_id, show_id, status, created_at, updated_at)
VALUES
    (1, 1, 1, 'CONFIRMED', now(), now());