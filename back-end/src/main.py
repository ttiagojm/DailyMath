import uvicorn
from fastapi import FastAPI
import psycopg2 
from src.controllers.RequestExercise import RequestExercise
from src.controllers.Connection import Connection
from dotenv import load_dotenv
from dotenv import dotenv_values

load_dotenv()

app = FastAPI()

databaseConfig = dotenv_values(".env")

databaseUser:str = databaseConfig["databaseUser"]
databaseName:str = databaseConfig["databaseName"]
databasePassword:str = databaseConfig["databasePassword"]

db = psycopg2.connect(f'dbname={databaseName} user={databaseUser} password={databasePassword}')

dbController = db.cursor()

@app.get("/")
async def index():
    return{"message": "hello daily!"}

@app.get("/api/getExercise/")
async def getExercise(id:int = "0", token:int = 0):
    
    newConnection = Connection(id, token)

    exercise = RequestExercise(newConnection, dbController)

    response = exercise.getResponse()

    return response
     

def start():
    uvicorn.run("src.main:app", host="127.0.0.1", reload=True)