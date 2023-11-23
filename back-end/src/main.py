import uvicorn
from fastapi import FastAPI

app = FastAPI()

@app.get("/")
async def index():
    return{"message": "hello daily!"}


def start():
    uvicorn.run("src.main:app", host="127.0.0.1", reload=True)