# DailyMath
It's a webapp which will show a new math problem everyday. Exercises could be about linear algebra, calculus, trignometry even be applied or pure math.

# Create Database
For creating your database using Docker:

* Open your terminal inside `api`
* Run `docker-compose build && docker-compose up -d` 
* Voilá! Your database is created and working!


# API
The API has 5 environment variables:

* POSTGRES_USER: User of the database
* POSTGRES_PASSWORD: Password to access database
* POSTGRES_DB: Database name
* SECRET: 32 bit random string for JWT

It's important to know that sign issuer is "dailymath".

# Credits
5 people made this project possible, each of them with differente tasks and experience but all helped. Let's credit them!

* [Bruna Ferreira](https://github.com/bugelseif) - Backend Developer

* [Finalshare90](https://github.com/Finalshare90) - Backend Developer

* [Francisco Santos](https://github.com/ProgramingIsTheFuture) - Code Reviewer

* [Gonçalo Rosa](https://github.com/GoncalojmRosa) - Frontend Developer

* [Tiago Martins](https://github.com/ttiagojm) - Backend Developer, Code Reviewer and suggested mathematical problems
