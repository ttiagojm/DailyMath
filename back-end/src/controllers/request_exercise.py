class RequestExercise: 
    
    __exercise:dict = {}

    def __init__ (self, id_exercise:int, db_controller):
        self.id_exercise = id_exercise
        self.db_controller = db_controller

        self.__exercise["id"] = self.id_exercise

        self.db_controller.execute\
            ('SELECT (exercise, type, source, is_multiple_choice, id_solution),\
            (options, multiple_choices.id)\
            FROM exercisetable, multiple_choices WHERE exercisetable.id=%s'%(self.__exercise["id"]))
        raw_query = db_controller.fetchone();

        formated_query = {}
        for index, tabel_element in enumerate(raw_query):
            formated_query[index] = tabel_element[1:-1].split(",")

        self.__exercise = {
            "id": self.id_exercise,
            "exercise": formated_query[0][0],
            "type": formated_query[0][1],
            "source": formated_query[0][2]
        }

        if formated_query[0][3] == 't':
            self.__exercise["choices_id"] = formated_query[1][0]
            self.__exercise["options"] = formated_query[1][1]
   
    def getResponse(self) -> dict:
        return self.__exercise 
