import uvicorn
from fastapi import FastAPI
from .controllers.request_exercise import RequestExercise
from random import Random
from datetime import datetime
from .controllers.connection import DB_CONNECTION

app = FastAPI()

rand = Random()
rand.seed()

current_day:int = datetime.now().day
day_exercise_id = 1

db_controller = DB_CONNECTION.cursor()

db_controller.execute("SELECT COUNT(id) FROM exercisetable")
db_size:int = db_controller.fetchone()[0];

exercise:RequestExercise = None

@app.get("/")
async def index():
    return{"message": "hello daily!"}


@app.get("/api/getExercise/")
async def getExercise():

    global exercise, current_day

    id_exercise = get_id()
 
    exercise = get_request_exercise_instance(exercise, id_exercise)

    response = exercise.getResponse()

    return response

def get_request_exercise_instance(instance_controller:RequestExercise, id_exercise:int) -> RequestExercise:
    if instance_controller is None:
        return RequestExercise(id_exercise, db_controller) 
    return instance_controller

def get_id() -> int:
    global day_exercise_id

    exercise_id:int = day_exercise_id

    if is_another_day():
        exercise_id = rand.randint(1, db_size)
        day_exercise_id = exercise_id 
    
    return exercise_id


def is_another_day() -> bool:
    global current_day, exercise

    if datetime.now().day is not current_day:
        current_day = datetime.now().day;
        exercise = None
        return True

    return False

def start():
    uvicorn.run("src.main:app", host="127.0.0.1", reload=True)
