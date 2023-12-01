from dotenv import load_dotenv
from dotenv import dotenv_values

load_dotenv()
database_config = dotenv_values(".env")

DATABASE_USER:str = database_config["DATABASE_USER"]
DATABASE_NAME:str = database_config["DATABASE_NAME"]
DATABASE_PASSWORD:str = database_config["DATABASE_PASSWORD"]