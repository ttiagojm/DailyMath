
from types import FunctionType
from src.controllers.Connection import Connection


class RequestExercise: 
    
    __exercise:dict = {}

    def __init__ (self, connection:Connection, dbController):
        self.connection = connection
        self.dbController = dbController

        self.__exercise["id"] = self.connection.id

        self.dbController.execute\
            ('SELECT (exercise, type, source, is_multiple_choice, id_solution),\
            (options, multiple_choices.id)\
            FROM exercisetable, multiple_choices WHERE exercisetable.id=%s'%(self.__exercise["id"]))
        rawQuery = dbController.fetchone();

        formatedQuery = {}
        for index, tableElement in enumerate(rawQuery):
            formatedQuery[index] = tableElement[1:-1].split(",")

        self.__exercise = {
            "id": self.connection.id,
            "exercise": formatedQuery[0][0],
            "type": formatedQuery[0][1],
            "source": formatedQuery[0][2]
        }

        if formatedQuery[0][3] == 't':
            self.__exercise["choices_id"] = formatedQuery[1][0]
            self.__exercise["options"] = formatedQuery[1][1]
   
    def getResponse(self) -> dict:
        return self.__exercise 
