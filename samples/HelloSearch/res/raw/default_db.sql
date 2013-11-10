CREATE VIRTUAL TABLE books USING FTS3 (
    _id INTEGER PRIMARY KEY,
    isbn TEXT,
    keywords TEXT,
    name TEXT
);

INSERT INTO books (isbn, keywords, name) values (1, 'art,theory,aesthetic,aesthetics', 'Aesthetic Theory');
INSERT INTO books (isbn, keywords, name) values (2, 'art,theory,aesthetic,aesthetics', 'Art in Theory 1900-2000');
INSERT INTO books (isbn, keywords, name) values (3, 'graphics', 'ShaderX 4');
INSERT INTO books (isbn, keywords, name) values (4, 'science', 'The Origins of Modern Science');
INSERT INTO books (isbn, keywords, name) values (5, 'computing', 'Pro Android 4');
INSERT INTO books (isbn, keywords, name) values (6, 'computing', 'Advanced UNIX Programming');
INSERT INTO books (isbn, keywords, name) values (7, 'computing', 'Game Development with Lua');
INSERT INTO books (isbn, keywords, name) values (8, 'computing', 'Game Design Perspectives');