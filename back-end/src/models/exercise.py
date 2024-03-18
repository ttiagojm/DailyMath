from src.config import DB_CONNECTION
from src.utils.errors import InvalidID
from random import randint

db_conn = DB_CONNECTION.cursor()

def get_rand_id():
    db_conn.execute("SELECT COUNT(Id_Exercise) FROM EXERCISE")
    db_size = db_conn.fetchone()[0]

    if db_size < 1:
        raise InvalidID()

    return randint(1, db_size)

def get_exercise(ex_id):
    db_conn.execute\
        ('SELECT (exercise, type, source, is_multiple_choice, id_solution),\
        (options, multiple_choices.id)\
        FROM exercisetable, multiple_choices WHERE exercisetable.id=%s'%(ex_id))
    raw_query = db_conn.fetchone()

    formated_query = {}
    for index, tabel_element in enumerate(raw_query):
        formated_query[index] = tabel_element[1:-1].split(",")

    exercise = {
        "id": ex_id,
        "question": formated_query[0][0],
        "type": formated_query[0][1],
        "source": formated_query[0][2]
    }

    if formated_query[0][3] == 't':
        exercise["choices_id"] = formated_query[1][0]
        exercise["options"] = formated_query[1][1]
   
