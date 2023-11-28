class Connection:

     def __init__ (self, id:int, isMultiple:bool, token:int):
         print("New connection")
         self.id = id
         self.isMultiple = isMultiple
         self.token = token