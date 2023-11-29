import uvicorn
from fastapi import FastAPI
import psycopg2 
from src.controllers.RequestExercise import RequestExercise
from src.controllers.Connection import Connection
from dotenv import load_dotenv
from dotenv import dotenv_values
from random import Random
from datetime import datetime

load_dotenv()
databaseConfig = dotenv_values(".env")

app = FastAPI()

rand = Random()
rand.seed()

databaseUser:str = databaseConfig["databaseUser"]
databaseName:str = databaseConfig["databaseName"]
databasePassword:str = databaseConfig["databasePassword"]

#currentDay:int = datetime.now().day
currentDay:int = 29
dayExerciseId = 1


db = psycopg2.connect(f'dbname={databaseName} user={databaseUser} password={databasePassword}')
dbController = db.cursor()

dbController.execute("SELECT COUNT(id) FROM exercisetable")
databaseSize:int = dbController.fetchone()[0];

exercise:RequestExercise = None

@app.get("/")
async def index():
    return{"message": "hello daily!"}


@app.get("/api/getExercise/")
async def getExercise():

    global exercise, currentDay

    newConnection = Connection(getId())    

    exercise = getRequestExerciseInstance(exercise, newConnection)

    response = exercise.getResponse()

    return response

def getRequestExerciseInstance(instanceController:RequestExercise, connection:Connection) -> RequestExercise:
    if instanceController is None:
        return RequestExercise(connection, dbController) 
    return instanceController
     

def getId() -> int:

    global dayExerciseId

    exerciseId:int = dayExerciseId

    if isAnotherDay():
        exerciseId = rand.randint(1, databaseSize)
        dayExerciseId = exerciseId 
    
    return exerciseId



def isAnotherDay() -> bool:
    
    global currentDay, exercise

    if datetime.now().day is not currentDay:
        currentDay = datetime.now().day;
        exercise = None
        return True

    return False

def start():
    uvicorn.run("src.main:app", host="127.0.0.1", reload=True)
