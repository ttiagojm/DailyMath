CREATE TABLE EXERCISE (
    Id_Exercise INT NOT NULL,
    Id_Solution INT NOT NULL,
    Type_ex VARCHAR(100) NOT NULL,
    Source VARCHAR(200) NOT NULL,
    Exercise TEXT NOT NULL,
    Is_multiple BOOL NOT NULL,
    CONSTRAINT pk_exercise PRIMARY KEY (Id_Exercise)
);

CREATE TABLE SOLUTION(
    Id_Solution INT NOT NULL,
    Solution TEXT NOT NULL,
    CONSTRAINT pk_solution PRIMARY KEY (Id_Solution)
);

CREATE TABLE MULTIPLE_CHOICE(
    Id_Multiple INT NOT NULL,
    Id_Exercise INT NOT NULL,
    Options TEXT NOT NULL,
    CONSTRAINT pk_multiple_choice PRIMARY KEY (Id_Multiple)
);

ALTER TABLE EXERCISE 
ADD CONSTRAINT fk_exercise_solution
FOREIGN KEY (Id_Solution) REFERENCES SOLUTION(Id_Solution);

ALTER TABLE MULTIPLE_CHOICE 
ADD CONSTRAINT fk_multiple_exercise
FOREIGN KEY (Id_Exercise) REFERENCES EXERCISE(Id_Exercise);