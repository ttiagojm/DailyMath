CREATE TABLE EXERCISE (
    Id_Exercise SERIAL NOT NULL,
    Id_Solution INT NOT NULL,
    Type_ex VARCHAR(100) NOT NULL,
    Source VARCHAR(200) NOT NULL,
    Exercise TEXT NOT NULL,
    Is_multiple BOOL NOT NULL,
    Done BOOL NOT NULL DEFAULT false,
    CONSTRAINT pk_exercise PRIMARY KEY (Id_Exercise)
);

CREATE TABLE SOLUTION(
    Id_Solution SERIAL NOT NULL,
    Solution TEXT NOT NULL,
    CONSTRAINT pk_solution PRIMARY KEY (Id_Solution)
);

CREATE TABLE MULTIPLE_CHOICE(
    Id_Multiple SERIAL NOT NULL,
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

-- Add some simple Exercise
INSERT INTO SOLUTION (Id_Solution, Solution) 
VALUES (1, 
'An absolute value function can be translated into a piecewise function:\\
\displaystyle |x-2| = \begin{cases}  x-2 & \text{if $x \geq 2$} \\ 2-x & \text{if $x < 2$} \end{cases}.\\

Then we can calculate the integral of this function using two integrals, one for each part of the piecewise function. The first one have boundaries as 0 and 2 because of the first part of the piecewise function. The second one have boundaries 2 and 3 because of the second part of the piecewise function. \\

$\int_{2}^{3} x-2 dx + \int_{2}^{0} 2-x dx = [\frac{x^2}{2}-2x]_{2}^{3} + [2x-\frac{x^2}{2}]_{0}^{2} = \frac{3^2}{2}-2 \cdot 3 - (\frac{2^2}{2}-2 \cdot 2) + 2 \cdot 2 - \frac{2^2}{2} - (2 \cdot 0-\frac{0^2}{2}) = 2.5$');

INSERT INTO EXERCISE (Id_Solution, Type_ex, Source, Exercise, Is_multiple)
VALUES (1, 'Cálculo', 'https://andymath.com/', 'Calculate: $\int_{0}_{3} |x-2| dx$', false);
