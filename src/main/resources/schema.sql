DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS group_lectures;
DROP TABLE IF EXISTS lectures;
DROP TABLE IF EXISTS teacher_subjects;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS teachers;
DROP TABLE IF EXISTS subjects;
DROP TABLE IF EXISTS audiences;
DROP TABLE IF EXISTS pair_times;

CREATE TABLE groups
(
    id     SERIAL PRIMARY KEY,
    number INTEGER NOT NULL
);

CREATE TABLE students
(
    student_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    email      VARCHAR(100) NOT NULL,
    year       INTEGER,
    group_id   INTEGER REFERENCES groups (id) ON DELETE CASCADE
);

CREATE TABLE teachers
(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    email      VARCHAR(100) NOT NULL
);

CREATE TABLE subjects
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE audiences
(
    id            SERIAL PRIMARY KEY,
    campus_number INTEGER NOT NULL,
    room_number   INTEGER NOT NULL
);

CREATE TABLE pair_times
(
    pair_number INTEGER PRIMARY KEY,
    start_time  TIME NOT NULL,
    end_time    TIME NOT NULL
);

CREATE TABLE lectures
(
    lecture_id  SERIAL PRIMARY KEY,
    audience_id INTEGER REFERENCES audiences (id) ON DELETE CASCADE,
    teacher_id  INTEGER REFERENCES teachers (id)ппш ON DELETE CASCADE,
    pair_time INTEGER REFERENCES pair_times (pair_number) ON DELETE CASCADE
);

CREATE TABLE teacher_subjects
(
    teacher_id INTEGER REFERENCES teachers (id) ON DELETE CASCADE,
    subject_id INTEGER REFERENCES subjects (id) ON DELETE CASCADE,
    CONSTRAINT teacher_subjects_unique PRIMARY KEY (teacher_id, subject_id)
);

CREATE TABLE group_lectures
(
    lecture_id INTEGER REFERENCES lectures (lecture_id) ON DELETE CASCADE,
    group_id   INTEGER REFERENCES groups (id) ON DELETE CASCADE,
    CONSTRAINT group_lectures_pk PRIMARY KEY (lecture_id, group_id)
);

ALTER TABLE Customer
(

)
