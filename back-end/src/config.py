import psycopg2 
from dotenv import load_dotenv
from dotenv import dotenv_values

load_dotenv()
database_config = dotenv_values(".env")

DATABASE_USER:str = database_config["DATABASE_USER"]
DATABASE_NAME:str = database_config["DATABASE_NAME"]
DATABASE_PASSWORD:str = database_config["DATABASE_PASSWORD"]

try:
    DB_CONNECTION = psycopg2.connect(
        dbname=DATABASE_NAME,
        user=DATABASE_USER,
        password=DATABASE_PASSWORD
    )
except (Exception, psycopg2.DatabaseError) as error:
            print(error)