import uvicorn
from fastapi import FastAPI
from src.controllers.exercise import Exercise


app = FastAPI()
exercise = Exercise().exercise

@app.get("/api/exercise/")
async def getExercise(): 
    return exercise

def start():
    uvicorn.run("src.main:app", host="127.0.0.1", reload=True)


if __name__ == "__main__":
    start()