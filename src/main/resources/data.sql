INSERT INTO groups (id, number) VALUES
(1, 1823),
(2, 1785),
(3, 5631),
(4, 2541);

INSERT INTO students (student_id, first_name, last_name, email, year, group_id) VALUES
(1, 'Rees', 'Best', 'rb@gmail.com', 1, 1),
(2, 'Matthias ', 'Riggs', 'mr@gmail.com', 1, 1),
(3, 'Brady ', 'Mccartney', 'bm@gmail.com', 1, 1),
(4, 'Inara ', 'Krueger', 'ik@gmail.com', 2, 2),
(5, 'Benjamin ', 'Cameron', 'bk@gmail.com', 3, 3),
(6, 'Kealan ', 'Charlton', 'kc@gmail.com', 4, 4);


INSERT INTO teachers (id, first_name, last_name, email) VALUES
(1, 'Alanah', 'Peck', 'ap@gmail.com'),
(2, 'Zaki', 'Weaver', 'zw@gmail.com'),
(3, 'Phillip', 'Weber', 'pw@gmail.com'),
(4, 'Beverley', 'Forbes', 'bf@gmail.com');

INSERT INTO subjects (id, name) VALUES
(1, 'Math'),
(2, 'History'),
(3, 'Programming'),
(4, 'Physics');

INSERT INTO audiences (id, campus_number, room_number) VALUES
(1, 1, 1105),
(2, 1, 2223),
(3, 1, 2310),
(4, 2, 3121);

INSERT INTO pair_times (pair_number, start_time, end_time) VALUES
(1, '09:00:00', '10:30:00'),
(2, '10:40:00', '12:10:00'),
(3, '12:20:00', '13:50:00'),
(4, '14:10:00', '15:40:00'),
(5, '15:50:00', '17:20:00');

INSERT INTO lectures (lecture_id, audience_id, teacher_id, pair_time) VALUES
(1, 1, 1, 1),
(2, 2, 2, 2),
(3, 3, 3, 3),
(4, 4, 4, 4);

INSERT INTO teacher_subjects (teacher_id, subject_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);

INSERT INTO group_lectures (lecture_id, group_id) VALUES
(1, 1),
(2, 1), (2, 3),
(3, 2), (3, 3), (3, 4),
(4, 1), (4, 2), (4, 3), (4, 4);