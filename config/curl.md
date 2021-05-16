<<<<<<<<<<<<<<<<<<<<<<<<    AdminVoteController    >>>>>>>>>>>>>>>>>>>>>>>>

## this controller return voteTO, which inclusive userId and restaurantId

#### get all votes all users for all time. Admin only

`curl -s http://localhost:8080/topjava_graduation/admin/votes/ --user admin@gmail.com:123456789`

#### get vote by vote_id (10005). Admin only

`curl -s http://localhost:8080/topjava_graduation/admin/votes/10005 --user admin@gmail.com:123456789`

#### get all votes between 2021-04-21 and 2021-04-26. Admin only

`curl -s 'http://localhost:8080/topjava_graduation/admin/votes/filter?start=2021-04-21&end=2021-04-26' --user admin@gmail.com:123456789`

#### get all votes all users for all time (second way). Admin only

`curl -s http://localhost:8080/topjava_graduation/admin/votes/filter --user admin@gmail.com:123456789`

#### get all votes today. Admin only

`curl -s http://localhost:8080/topjava_graduation/admin/votes/today --user admin@gmail.com:123456789`

#### delete vote. Admin Only

`curl -s -i -X DELETE -d "10001" -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/admin/votes/ --user admin@gmail.com:123456789`

<<<<<<<<<<<<<<<<<<<<<<<<    AdminUserController    >>>>>>>>>>>>>>>>>>>>>>>>

#### get all registered user. Admin only

`curl -s http://localhost:8080/topjava_graduation/admin/users/ --user admin@gmail.com:123456789`

#### get user with id 10001. Admin only

`curl -s http://localhost:8080/topjava_graduation/admin/users/10001 --user admin@gmail.com:123456789`

#### get votes for user 10001. Admin only

`curl -s http://localhost:8080/topjava_graduation/admin/users/10001/votes --user admin@gmail.com:123456789`

#### delete user 10002. Admin Only

`curl -s -i -X DELETE -d "10002" -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/admin/users/ --user admin@gmail.com:123456789`

#### update user 10003. Admin Only

`curl -s -i -X PUT -d '{"name":"Boris","id":10003,"email":"Boris@gmail.com","password":"passwordBoris","registered":"2021-05-13T23:08:49.321","roles":["USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/admin/users/ --user admin@gmail.com:123456789`

#### change user 10003 Role to ADMIN and USER. This is only one way to change Role. Admin Only

`curl -s -i -X PATCH -d -H 'Content-Type:application/json;charset=UTF-8' 'http://localhost:8080/topjava_graduation/admin/users?role=ADMIN,USER&id=10003' --user admin@gmail.com:123456789`

<<<<<<<<<<<<<<<<<<<<<<<<    RestaurantController    >>>>>>>>>>>>>>>>>>>>>>>>

#### get All restaurants without menu and vote, just name and id. Everybody can see this.

`curl -s http://localhost:8080/topjava_graduation/restaurants`

#### get All restaurants with menu. Everybody can see this.

`curl -s http://localhost:8080/topjava_graduation/restaurants/with-menu`

#### get restaurant 10000 without menu and vote. Everybody can see this.

`curl -s http://localhost:8080/topjava_graduation/restaurants/10000`

#### get restaurant 1000 with vote but without menu. Admin only

`curl -s http://localhost:8080/topjava_graduation/restaurants/10001/votes --user admin@gmail.com:123456789`

#### get all votes restaurant's 10001 between 2021-04-21 and 2021-04-26. Admin only

`curl -s "http://localhost:8080/topjava_graduation/restaurants/10001/votes/filter?start=2021-04-21&end=2021-04-26" --user admin@gmail.com:123456789`

#### get all votes restaurant's 10001 for all time. Admin Only

`curl -s http://localhost:8080/topjava_graduation/restaurants/10001/votes/filter --user admin@gmail.com:123456789`

#### get votes restaurant's 10001 today. Admin Only

`curl -s http://localhost:8080/topjava_graduation/restaurants/10001/votes/today --user admin@gmail.com:123456789`

#### create a new restaurant. Admin Only

`curl -s -i -X POST -d '{"name":"BurgerLord"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/restaurants/ --user admin@gmail.com:123456789`

#### delete 10001 restaurant. Admin Only

`curl -s -i -X DELETE -d "10001" -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/restaurants --user admin@gmail.com:123456789`

#### update name 10001 restaurant. Admin Only

`curl -s -X PUT -d '{"id":"10001","name":"DeadFish"}' -H 'Content-Type: application/json' http://localhost:8080/topjava_graduation/restaurants/10001  --user admin@gmail.com:123456789`

<<<<<<<<<<<<<<<<<<<<<<<<   DishController    >>>>>>>>>>>>>>>>>>>>>>>>

#### get actual menu for 10001 restaurant. Everybody can see this.

`curl -s http://localhost:8080/topjava_graduation/restaurants/10001/menu`

#### get full menu for 10001 restaurant. Admin Only.

`curl -s http://localhost:8080/topjava_graduation/restaurants/10001/menu/full` --user admin@gmail.com:123456789`

#### get dish 10008 for 10001 restaurant. Admin Only.

`curl -s 'http://localhost:8080/topjava_graduation/restaurants/10001/menu/10008' --user admin@gmail.com:123456789`

#### add new dish restaurant 10001 . Admin Only.

`curl -s -i -X POST -d '{"name":"burger","dateTime":"2020-03-03T03:04:04","price":150,"enabled":true}' -H 'Content-Type:application/json;charset=UTF-8' 'http://localhost:8080/topjava_graduation/restaurants/10001/menu/' --user admin@gmail.com:123456789`

#### update dish 10002 restaurant 10000. Admin Only.

`curl -s -i -X PUT -d '{"id":10002, "name":"coca-cola","dateTime":"2021-05-05T03:04:44","price":50,"enabled":true}' -H 'Content-Type:application/json;charset=UTF-8' 'http://localhost:8080/topjava_graduation/restaurants/10000/menu/' --user admin@gmail.com:123456789`

#### delete dish 10002 restaurant 10000. Admin Only

`curl -s -i -X DELETE -d "10002" -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/restaurants/10000/menu --user admin@gmail.com:123456789`

#### set dish 10002 as disabled for restaurant 10000. Admin Only

`curl -s -i -X PATCH -d -H  'http://localhost:8080/topjava_graduation/restaurants/10000/menu/10002?enabled=false' --user admin@gmail.com:123456789`

<<<<<<<<<<<<<<<<<<<<<<<<    ProfileController    >>>>>>>>>>>>>>>>>>>>>>>>

#### get profile for user Jonny.

`curl -s http://localhost:8080/topjava_graduation/profile --user jonny@gmail.com:passwordJonny`

#### create new user and try to Vote

`curl -i -X POST -d '{"name":"Stenly","email":"sten@gmail.com","password":"passwordSten","registered":"2021-05-15T12:33:05.947","roles":["USER"]}' -H 'Content-Type:application/json;charset=UTF-8'  'http://localhost:8080/topjava_graduation/profile/register'`
`curl -i -X POST -d "10000"  'http://localhost:8080/topjava_graduation/votes'  -H 'Content-Type:application/json;charset=UTF-8' --user sten@gmail.com:passwordSten`

#### delete user by yourself

`curl -i -X DELETE http://localhost:8080/topjava_graduation/profile --user jonny@gmail.com:passwordJonny`

#### update user

`curl -i -X PUT -d '{"id": 10001,"name":"Jack","email":"jacke@gmail.com","password":"password","registered":"2021-12-15T12:33:05.947","roles":["USER"]}' -H 'Content-Type:application/json;charset=UTF-8'  'http://localhost:8080/topjava_graduation/profile' --user jonny@gmail.com:passwordJonny`

#### get votes user since 2021-04-21 to 2021-04-26

`curl -s  'http://localhost:8080/topjava_graduation/profile/votes?start=2021-04-21&end=2021-04-26' --user jonny@gmail.com:passwordJonny`

#### get votes user for today

`curl -s  'http://localhost:8080/topjava_graduation/profile/votes/today' --user jonny@gmail.com:passwordJonny`

#### get vote 10010 user

`curl -s  'http://localhost:8080/topjava_graduation/profile/votes/10010' --user jonny@gmail.com:passwordJonny`

#### delete vote 10010

`curl -i -X DELETE -d "10013" -H 'Content-Type:application/json;charset=UTF-8' 'http://localhost:8080/topjava_graduation/profile/votes' --user jonny@gmail.com:passwordJonny`

<<<<<<<<<<<<<<<<<<<<<<<<   VoteController    >>>>>>>>>>>>>>>>>>>>>>>>

#### get vote results

`curl -s  'http://localhost:8080/topjava_graduation/votes/results' --user jonny@gmail.com:passwordJonny`

#### to vote

`curl -i -X POST -d "10000"  'http://localhost:8080/topjava_graduation/votes'  -H 'Content-Type:application/json;charset=UTF-8' --user jonny@gmail.com:passwordJonny`

<<<<<<<<<<<<<<<<<<<<<<<<  assume with errors   >>>>>>>>>>>>>>>>>>>>>>>>

##### error:DATA_ERROR, duplicate dish name for one restaurant

`curl -s -i -X POST -d '{"name":"borscht","dateTime":"2021-03-03T03:04:04","price":250,"enabled":false}' -H 'Content-Type:application/json;charset=UTF-8' 'http://localhost:8080/topjava_graduation/restaurants/10000/menu/' --user admin@gmail.com:123456789`

##### error:DATA_ERROR, duplicate restaurant name

`curl -s -i -X POST -d '{"name":"Meat Home"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/restaurants/ --user admin@gmail.com:123456789`

##### error:DATA_ERROR, duplicate user email

`curl -i -X POST -d '{"name":"Stenly","email":"admin@gmail.com","password":"passwordSten","registered":"2021-05-15T12:33:05.947","roles":["USER"]}' -H 'Content-Type:application/json;charset=UTF-8'  'http://localhost:8080/topjava_graduation/profile/register'`

##### error: DATE_NOT_FOUND not found restaurant with  id=10051

`curl -s http://localhost:8080/topjava_graduation/restaurants/10051/menu`

##### error: VALIDATION_ERROR: create  restaurant with error

`curl -s -i -X POST -d '{"id":"10020", "name":"BurgerLord"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/restaurants/ --user admin@gmail.com:123456789`





