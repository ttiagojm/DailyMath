
from types import FunctionType
from src.controllers.Connection import Connection


class RequestExercise: 
    
    __exercise:dict = {}

    def __init__ (self, connection:Connection, dbController):
        self.connection = connection
        self.dbController = dbController

        self.__exercise["id"] = self.connection.id
        self.setExerciseData("exercise", "exercisetable")
        self.setExerciseData("type", "exercisetable")
        self.setExerciseData("source", "exercisetable")
        self.setExerciseData("id_solution", "exercisetable")

        self.dbController.execute('SELECT is_multiple_choice FROM exercisetable WHERE id=%s'%(self.__exercise["id"]))
        isMultiple = self.dbController.fetchone() 
        
        if isMultiple[0] == True:
            self.setExerciseData("id", "multiple_choices", "multiple_choices_id")
            self.setExerciseData("options", "multiple_choices")
            


    def setExerciseData(self, rowLabel:str, table:str, requestLabel:str = None):

        if requestLabel == None:
            requestLabel = rowLabel

        self.dbController.execute('SELECT %s FROM %s WHERE id=%s;'%(rowLabel, table, self.__exercise["id"]))
        self.__exercise[requestLabel] = self.dbController.fetchone()
        
        if self.__exercise[requestLabel] is None:
            self.__exercise[requestLabel] = '%s not found'%(requestLabel)

    def getResponse(self) -> dict:
        return self.__exercise 
