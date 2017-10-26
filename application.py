"""
This script runs the FindGroup3 application using a development server.
"""

import random
from os import environ

from flask import Flask, request, jsonify, json
from flask_restful import *
from sqlalchemy import create_engine

e = create_engine('sqlite:///test_db.db', echo=True)
app = Flask(__name__)
api = Api(app)

# Make the WSGI interface available at the top level so wfastcgi can get it.
wsgi_app = app.wsgi_app

# parser = reqparse.RequestParser()
# parser.add_argument('Group_ID', type=int)
# parser.add_argument('User_ID', type=int)
# parser.add_argument('isON', type=bool)
# parser.add_argument('Fname', type=str)
# parser.add_argument('Group_Name', type=str)
# parser.add_argument('Lname', type=str)
# parser.add_argument('lat', type=float)
# parser.add_argument('long', type=float)


class updateMap(Resource):
    def get(self, group_id) :
        conn = e.connect()
        query = conn.execute("select Fname, Lname, lat, long from users where users.User_ID in (select users.User_ID "
                             "from lookup, users where lookup.group_id ={0} and users.isOn = 1)".format(group_id))
        if len(query.keys()) == 0:
            abort(404, message="Group_ID {0} doesn't exist (yet)".format(group_id))
        # result = {'data': [dict(zip(tuple (query.keys()), i)) for i in query.cursor]}
        return jsonify()


class updateSelf(Resource):
    def put(self, user_id):
        args = request.get_json(force=True)
        args = args['data'][0]
        params = (args["lat"], args["long"], user_id)

        conn = e.connect()
        conn.execute('update users set lat = ?, long = ? where User_ID = ?', params)
        return '', 201

class joinGroup(Resource):
    def put(self, group_id, user_id):
        conn = e.connect()
        conn.execute("insert into lookup (User_ID, Group_ID) values (?, ?)", (user_id, group_id))
        conn.execute("update groups set count = (SELECT count (*) from lookup where lookup.Group_ID = groups.Group_ID)")
        return '', 201


class newUser(Resource):
    def post(self):
        args = request.get_json(force=True)
        args = args['data'][0]
        params = (args["User_ID"], args["Fname"], args["Lname"], args["lat"], args["long"])
        conn = e.connect()
        conn.execute("insert into users values (?, ?, ?, ?, ?, 1)", params)
        return args, 201


class newGroup(Resource):
    def post(self):
        group_id = random.randrange(1000000)
        args = request.get_json(force=True)
        args = args['data'][0]
        params1 = (args["User_ID"], group_id)
        params2 = (group_id, args["name"])
        conn = e.connect()
        conn.execute("insert into lookup values (?, ?)", params1)
        conn.execute("insert into groups values (?, ?, 1)",params2)
        return '', 201


class u_details(Resource):
    def get(self, user_id):
        conn = e.connect()
        query = conn.execute("select * from users where User_ID = {0}".format(user_id))
        if len(query.keys()) == 0:
            abort(404, message="User {0} doesn't exist (yet)".format(user_id))
        result = {'data': [dict(zip(tuple(query.keys()), i)) for i in query.cursor]}
        return result


api.add_resource(updateMap, '/map')
api.add_resource(updateSelf, '/self/<int:user_id>')
api.add_resource(joinGroup, '/join/<int:group_id>/<int:user_id>')
api.add_resource(newGroup, '/create')
api.add_resource(newUser, '/new')
api.add_resource(u_details,'/user/<int:user_id>')


if __name__ == '__main__':
    HOST = environ.get('SERVER_HOST', 'localhost')
    try:
        PORT = int(environ.get('SERVER_PORT', '5555'))
    except ValueError:
        PORT = 5555
    app.run(HOST, PORT)
