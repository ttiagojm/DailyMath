import uvicorn
from fastapi import FastAPI
import psycopg2 
from src.controllers.RequestExercise import RequestExercise
from src.controllers.Connection import Connection

app = FastAPI()

databaseName:str = "db"
databaseUser:str = "postgres"
databasePassword:str = input("type the database password: ")

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