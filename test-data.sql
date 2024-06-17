INSERT INTO Bookstore (name, address, phone, created_At, updated_At)
VALUES
    ('Vilnius Bookstore', 'Gedimino pr. 9, Vilnius', '+37061234567', NOW(), NOW()),
    ('Alytus Bookstore', 'Alytaus g. 9, Alytus', '+37061234555', NOW(), NOW()),
    ('Kaunas Bookstore', 'Laisvės al. 5, Kaunas', '+37061123456', NOW(), NOW());


INSERT INTO Book (title, author, isbn, price, bookstore_id, created_At, updated_At)
VALUES
    ('Effective Java', 'Joshua Bloch', '978-0134685991', 45.00, 1, NOW(), NOW()),
    ('Clean Code', 'Robert C. Martin', '978-0132350884', 50.00, 1, NOW(), NOW()),
    ('Java Concurrency in Practice', 'Brian Goetz', '978-0321349606', 55.00, 2, NOW(), NOW()),
    ('Spring in Action', 'Craig Walls', '978-1617294945', 40.00, 2, NOW(), NOW());


INSERT INTO Sale (book_id, bookstore_id, quantity, sale_date, created_At, updated_At)
VALUES
    (1, 1, 3, '2023-06-01', NOW(), NOW()),
    (2, 1, 1, '2023-06-02', NOW(), NOW()),
    (3, 2, 2, '2023-06-03', NOW(), NOW()),
    (3, 3, 7, '2023-06-16', NOW(), NOW()),
    (4, 2, 1, '2023-06-04', NOW(), NOW());