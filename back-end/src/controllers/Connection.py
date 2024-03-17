import psycopg2 
from src.config import DATABASE_USER, DATABASE_NAME, DATABASE_PASSWORD

try:
    DB_CONNECTION = psycopg2.connect(
        dbname=DATABASE_NAME,
        user=DATABASE_USER,
        password=DATABASE_PASSWORD
    )
except (Exception, psycopg2.DatabaseError) as error:
            print(error)