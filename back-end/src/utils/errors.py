class InvalidID(Exception):
    def __init__(self, message="You don't have enough exercises on the Exercice table", *args):
        super(InvalidID, self).__init__(message, *args)