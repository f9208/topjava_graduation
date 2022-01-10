In order to have clickable links below setup in TomCat deploy-URL as http://localhost:8080/topjava_graduation.

### Graduation project TopJava

The task is:
Build a voting system for deciding where to have lunch.

### Specification API.

#### unregistered User scope

The task assume create a voting system for vote in which restaurant we are going to take a lunch. Everybody may watch
the list restaurants and its menu.
> show list restaurants http://localhost:8080/topjava_graduation/restaurants/ <br>
> show restaurants menu with {restaurant-id} for today http://localhost:8080/topjava_graduation/restaurants/{restaurant-id}/dishes
> show dish from menu restaurant by dish_id. Assume that dish is belong for the restaurant http://localhost:8080/topjava_graduation/restaurants/{restaurant-id}/dishes/{dish_id}
> show votes for restaurants with {restaurant-id} for today http://localhost:8080/topjava_graduation/restaurants/{restaurant-id}/votes. Available filter path variable start and end, showed votes for the restaurant since start_date to end_date


New Users may be registered. After that, they can vote for liked restaurant. Registration assume unique email:
> POST {"name":"Stenly","email":"sten@gmail.com","password":"passwordSten","registered":"2021-05-15T12:33:05.947","roles":["USER"]}' http://localhost:8080/topjava_graduation/profile/register

Show votes for a current restaurant (if value start and end is empty - show votes for today):
> since date_start to date_end http://localhost:8080/topjava_graduation/restaurants/{restaurant-id}/votes?start={start_date}&end={end_date}

#### Admin Role scope

User, who as well is admin can change menu, temporally switch off or delete some item of menu. Assume that User (Role
Admin) is isAuthenticated. Assume a restaurant_name are unique. Create a new Restaurant with "new Shaverma" name:
> POST {"name":"new Shaverma"}  http://localhost:8080/topjava_graduation/admin/restaurants/

Change restaurant (available change only restaurant name)
> PUT '{"id":"10001","name":"DeadFish"}' http://localhost:8080/topjava_graduation/admin/restaurants/10001

Add new dish in menu restaurant {restaurant-id}. Assume a dish_name and restaurant_id is unique.
> POST {"name":"burger","day":"2021-05-23","price":150}' http://localhost:8080/topjava_graduation/admin/restaurants/{restaurant-id}/dishes/

Change menu (update an existing dish):
> PUT {"id":10002, "name":"coca-cola","day":"2021-05-23","price":50}' http://localhost:8080/topjava_graduation/admin/restaurants/{restaurant-id}/dishes/

Delete dish by dish-id
> DELETE http://localhost:8080/topjava_graduation/admin/restaurants/{restaurant-id}/dishes/{dish-id}

Show list users:
> http://localhost:8080/topjava_graduation/admin/users/

Show votes user by userId since start_date to end_date. If start and end is empty - show votes for today. If value
user_id is empty - show votes for all users.
> http://localhost:8080/topjava_graduation/admin/votes?start={date}&end={date}&userId={number}

Change role for {user-id}:
> PATCH http://localhost:8080/topjava_graduation/admin/users?role=ADMIN,USER&id={user-id}

Delete user by Admin :
> DELETE http://localhost:8080/topjava_graduation/admin/users/{user-id}

#### User Role scope

Assume that User (Role User) is isAuthenticated.

For voting user send restaurant_id
> POST {restaurant_id} http://localhost:8080/topjava_graduation/votes

User may change mind and re-vote only before 11:00 AM. If user try to re-vote later he will catch TooLateException and
his voice won't be counted.
> PUT {restaurant_id} http://localhost:8080/topjava_graduation/votes/{vote_id}

User profile:
> http://localhost:8080/topjava_graduation/profile

User may watch his previous votes, filter include:
> http://localhost:8080/topjava_graduation/profile/votes?start={start_date}&end={end_date}

User can delete his vote and it won't be count in voting results
> DELETE http://localhost:8080/topjava_graduation/profile/votes/{vote_id}

User may change his profile:
> PUT {"id": 10001,"name":"Jack","email":"jacke@gmail.com","password":"password","registered":"2021-12-15T12:33:05.947","roles":["USER"]} http://localhost:8080/topjava_graduation/profile

And delete yourself:
> DELETE http://localhost:8080/topjava_graduation/profile

For change voting dead_line user constant ru.f9208.choicerestaurant.utils.DateTimeUtils.TOO_LATE

Extra curl-commands: /config/curl.md and /config/requests_examples