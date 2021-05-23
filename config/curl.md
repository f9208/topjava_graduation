<<<<<<<<<<<<<<<<<<<<<<<<    AdminVoteController    >>>>>>>>>>>>>>>>>>>>>>>>

## this controller return voteTO, which inclusive userId and restaurantId

#### get vote by vote_id (10005). Admin only

`curl -s http://localhost:8080/choiserestaurant/admin/votes/10005 --user admin@gmail.com:123456789`

#### get all votes between 2021-04-21 and 2021-04-26 for user 10000. Admin only

`curl -s 'http://localhost:8080/choiserestaurant/admin/votes?start=2021-04-21&end=2021-04-26&userId=10000' --user admin@gmail.com:123456789`

#### get all votes all users for today

`curl -s http://localhost:8080/choiserestaurant/admin/votes --user admin@gmail.com:123456789`

<<<<<<<<<<<<<<<<<<<<<<<<    AdminUserController    >>>>>>>>>>>>>>>>>>>>>>>>

#### get all registered user. Admin only

`curl -s http://localhost:8080/choiserestaurant/admin/users/ --user admin@gmail.com:123456789`

#### get user with id 10001. Admin only

`curl -s http://localhost:8080/choiserestaurant/admin/users/10001 --user admin@gmail.com:123456789`

#### delete user 10002. Admin Only

`curl -s -i -X DELETE -d -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/choiserestaurant/admin/users/10002 --user admin@gmail.com:123456789`

#### change user 10003 Role to ADMIN and USER. This is only one way to change Role. Admin Only

`curl -s -i -X PATCH -d -H 'Content-Type:application/json;charset=UTF-8' 'http://localhost:8080/choiserestaurant/admin/users?role=ADMIN,USER&id=10003' --user admin@gmail.com:123456789`
<<<<<<<<<<<<<<<<<<<<<<<<    AdminRestaurantController   >>>>>>>>>>>>>>>>>>>>>>>>

#### create a new restaurant. Admin Only

`curl -s -i -X POST -d '{"name":"BurgerLord"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/choiserestaurant/admin/restaurants/ --user admin@gmail.com:123456789`

#### update name 10001 restaurant. Admin Only

`curl -s -X PUT -d '{"id":"10001","name":"DeadFish"}' -H 'Content-Type: application/json' http://localhost:8080/choiserestaurant/admin/restaurants/10001  --user admin@gmail.com:123456789`

#### delete 10001 restaurant. Admin Only

`curl -s -i -X DELETE -d -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/choiserestaurant/admin/restaurants/10001 --user admin@gmail.com:123456789`

<<<<<<<<<<<<<<<<<<<<<<<<    AdminDishController   >>>>>>>>>>>>>>>>>>>>>>>>

#### add new dish restaurant 10001 for today. Admin Only.

`curl -s -i -X POST -d '{"name":"burger","day":"","price":150}' -H 'Content-Type:application/json;charset=UTF-8' 'http://localhost:8080/choiserestaurant/admin/restaurants/10001/dishes/' --user admin@gmail.com:123456789`

#### update dish 10002 restaurant 10000. Admin Only.

`curl -s -i -X PUT -d '{"id":10002, "name":"coca-cola","day":"2021-12-23","price":50}' -H 'Content-Type:application/json;charset=UTF-8' 'http://localhost:8080/choiserestaurant/admin/restaurants/10000/dishes/' --user admin@gmail.com:123456789`

#### delete dish 10002 restaurant 10000. Admin Only

`curl -s -i -X DELETE -d -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/choiserestaurant/admin/restaurants/10000/dishes/10002 --user admin@gmail.com:123456789`

<<<<<<<<<<<<<<<<<<<<<<<<    RestaurantController    >>>>>>>>>>>>>>>>>>>>>>>>

#### get All restaurants without menu and vote, just name and id. Everybody can see this.

`curl -s http://localhost:8080/choiserestaurant/restaurants`

#### get restaurant 10000 without menu and vote. Everybody can see this.

`curl -s http://localhost:8080/choiserestaurant/restaurants/10000`

#### get votes for restaurant 10000 for today. Everybody can see this.

`curl -s http://localhost:8080/choiserestaurant/restaurants/10001/votes`

#### get all votes for restaurant 10001 between 2021-04-21 and 2021-04-26. Everybody can see this.

`curl -s "http://localhost:8080/choiserestaurant/restaurants/10001/votes?start=2021-04-21&end=2021-04-26"`

<<<<<<<<<<<<<<<<<<<<<<<<   DishController    >>>>>>>>>>>>>>>>>>>>>>>>

#### get menu for 10001 restaurant. Everybody can see this.

`curl -s http://localhost:8080/choiserestaurant/restaurants/10001/dishes`

#### get dish 10008 for 10001 restaurant

`curl -s http://localhost:8080/choiserestaurant/restaurants/10001/dishes/10008`

<<<<<<<<<<<<<<<<<<<<<<<<    ProfileController    >>>>>>>>>>>>>>>>>>>>>>>>

#### get profile for user Jonny.

`curl -s http://localhost:8080/choiserestaurant/profile --user jonny@gmail.com:passwordJonny`

#### create new user and try to Vote

`curl -i -X POST -d '{"name":"Stenly","email":"sten@gmail.com","password":"passwordSten","registered":"2021-05-15T12:33:05.947","roles":["USER"]}' -H 'Content-Type:application/json;charset=UTF-8'  'http://localhost:8080/choiserestaurant/profile/register'`
`curl -i -X POST -d "10000"  'http://localhost:8080/choiserestaurant/votes'  -H 'Content-Type:application/json;charset=UTF-8' --user sten@gmail.com:passwordSten`

#### delete user by yourself

`curl -i -X DELETE http://localhost:8080/choiserestaurant/profile --user jonny@gmail.com:passwordJonny`

#### update user

`curl -i -X PUT -d '{"id": 10001,"name":"Jack","email":"jacke@gmail.com","password":"password","registered":"2021-12-15T12:33:05.947","roles":["USER"]}' -H 'Content-Type:application/json;charset=UTF-8'  'http://localhost:8080/choiserestaurant/profile' --user jonny@gmail.com:passwordJonny`

#### get votes user since 2021-04-21 to 2021-04-26

`curl -s  'http://localhost:8080/choiserestaurant/profile/votes?start=2021-04-21&end=2021-04-26' --user jonny@gmail.com:passwordJonny`

#### get votes user for today

`curl -s  'http://localhost:8080/choiserestaurant/profile/votes' --user jonny@gmail.com:passwordJonny`

#### get vote 10010 user

`curl -s  'http://localhost:8080/choiserestaurant/profile/votes/10010' --user jonny@gmail.com:passwordJonny`

#### delete vote 10010

`curl -i -X DELETE -d -H 'Content-Type:application/json;charset=UTF-8' 'http://localhost:8080/choiserestaurant/profile/votes/10013' --user jonny@gmail.com:passwordJonny`

<<<<<<<<<<<<<<<<<<<<<<<<   VoteController    >>>>>>>>>>>>>>>>>>>>>>>>

#### to vote for by difference users

`curl -i -X POST -d "10001"  'http://localhost:8080/choiserestaurant/votes'  -H 'Content-Type:application/json;charset=UTF-8' --user kety@gmail.com:passwordKety`
`curl -i -X POST -d "10001"  'http://localhost:8080/choiserestaurant/votes'  -H 'Content-Type:application/json;charset=UTF-8' --user leonard@gmail.com:passwordLeon`

#### re-vote for vote with id=10013

`curl -i -X PUT -d "10000" 'http://localhost:8080/choiserestaurant/votes/10013'  -H 'Content-Type:application/json;charset=UTF-8' --user jonny@gmail.com:passwordJonny`

<<<<<<<<<<<<<<<<<<<<<<<<  Assume with errors   >>>>>>>>>>>>>>>>>>>>>>>>

##### error:DATA_ERROR, duplicate dish name for one restaurant today

`curl -s -i -X POST -d '{"name":"bforscht","day":"","price":250}' -H 'Content-Type:application/json;charset=UTF-8' 'http://localhost:8080/choiserestaurant/admin/restaurants/10000/dishes/' --user admin@gmail.com:123456789`

##### error:DATA_ERROR, duplicate restaurant name

`curl -s -i -X POST -d '{"name":"Meat Home"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/choiserestaurant/admin/restaurants/ --user admin@gmail.com:123456789`

##### error:DATA_ERROR, duplicate user email

`curl -i -X POST -d '{"name":"Stenly","email":"admin@gmail.com","password":"passwordSten","registered":"2021-05-15T12:33:05.947","roles":["USER"]}' -H 'Content-Type:application/json;charset=UTF-8'  'http://localhost:8080/choiserestaurant/profile/register'`

##### error: VALIDATION_ERROR: create  restaurant with error

`curl -s -i -X POST -d '{"id":"10020", "name":"BurgerLord"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/choiserestaurant/admin/restaurants/ --user admin@gmail.com:123456789`





