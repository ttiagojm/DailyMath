from datetime import datetime
from src.models.exercise import get_rand_id, get_exercise
from src.utils.errors import InvalidID

class Exercise:
    __exercise = None
    def __init__(self):
        if self.__exercise is None or \
            self.__exercise["date"].day() < datetime.now().day():
            
            self.__exercise = dict()
            self.__exercise["date"] = datetime.now()
            self.__exercise["exercise"] = self.get_rand_exercise()
    
    @property
    def exercise(self):
        return self.__exercise["exercise"]
    
    def get_rand_exercise(self):
        try:
            resp = {"id": get_rand_id()}
            print(get_exercise(resp["id"]))
        except InvalidID as e:
            resp = {"message": "No exercises available."}

        return resp
