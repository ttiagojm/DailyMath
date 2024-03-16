import psycopg2 


from src.config import DATABASE_USER, DATABASE_NAME, DATABASE_PASSWORD

DB_CONNECTION = psycopg2.connect(f'dbname={DATABASE_NAME} user={DATABASE_USER} password={DATABASE_PASSWORD}')