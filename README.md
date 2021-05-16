In order to have clickable links below setup in TomCat deploy-URL as http://localhost:8080/topjava_graduation. 

### Graduation project TopJava

The task is:
Build a voting system for deciding where to have lunch.

### Specification API.

The task assume create a voting system for vote in which restaurant we are going to take a lunch. Everybody may watch
the list restaurants and its **actual** menu.
> show restaurants http://localhost:8080/topjava_graduation/restaurants/ <br>
> show restaurants with {restaurant-id} http://localhost:8080/topjava_graduation/restaurants/{restaurant-id}/menu

#### unregistered User scope

New Users may be registered. After that, they can vote for liked restaurant. Registration assume unique email:
> POST {"name":"Stenly","email":"sten@gmail.com","password":"passwordSten","registered":"2021-05-15T12:33:05.947","roles":["USER"]}' http://localhost:8080/topjava_graduation/profile/register

#### Admin Role scope

User, who as well is admin can change menu, temporally switch off or delete some item of menu. Assume that User (Role
Admin) is isAuthenticated. Assume a restaurant_name are unique. Create a new Restaurant with "new Shaverma" name:
> POST {"name":"new Shaverma"}  http://localhost:8080/topjava_graduation/restaurants/

Change restaurant (available change only restaurant name)
> PUT '{"id":"10001","name":"DeadFish"}' http://localhost:8080/topjava_graduation/restaurants/10001

Add new dish in menu restaurant {restaurant-id}. Assume a dish_name and restaurant_id is unique.
> POST {"name":"burger","dateTime":"2020-03-03T03:04:04","price":150,"enabled":true}' http://localhost:8080/topjava_graduation/restaurants/{restaurant-id}/menu/

Change menu (update an existing dish):
> PUT {"id":10002, "name":"coca-cola","dateTime":"2021-05-05T03:04:44","price":50,"enabled":true}' http://localhost:8080/topjava_graduation/restaurants/{restaurant-id}/menu/

Delete dish with dish-id
> DELETE {"dish-id"} http://localhost:8080/topjava_graduation/restaurants/{restaurant-id}/menu

Take away (set invisible for regular users) from a menu:
> PATCH http://localhost:8080/topjava_graduation/restaurants/{restaurant-id}/menu/{dish-id}?enabled=false

Show full menu by {restaurant-id}  (including disabled items)
> http://localhost:8080/topjava_graduation/restaurants/{restaurant-id}/menu/full

Show votes for a current restaurant (if value start and end is empty - show all votes for all time):
> since date_start to date_end http://localhost:8080/topjava_graduation/restaurants/{restaurant-id}/votes/filter?start={start_date}&end={end_date}
> today http://localhost:8080/topjava_graduation/restaurants/{restaurant-id}/votes/today


Show list users:
> http://localhost:8080/topjava_graduation/admin/users/

Show votes user with {user-id}:
> http://localhost:8080/topjava_graduation/admin/users/{user-id}/votes

Show votes users since start_date to end_date. If start and end is empty = show votes for all time:
> http://localhost:8080/topjava_graduation/admin/votes/filter?start={date}&end={date}

Change role for {user-id}:
> PATCH http://localhost:8080/topjava_graduation/admin/users?role=ADMIN,USER&id={user-id}

#### User Role scope

Assume that User (Role User) is isAuthenticated.

Registered users can watch vote results (for today):
> http://localhost:8080/topjava_graduation/votes/results

For voting user send restaurant_id
> POST {restaurant_id} http://localhost:8080/topjava_graduation/votes

User may change mind and re-vote, just repeat previous request with different restaurant_id. In this case, votes for
current day will be re-write.

User may vote(and re-vote) only before 11:00 AM. If user try to vote later he will catch TooLateException and his voice
won't be counted.

User profile:
> http://localhost:8080/topjava_graduation/profile

User may watch previous votes, filter include:
> http://localhost:8080/topjava_graduation/profile/votes?start={start_date}&end={end_date}

And his vote for today
> http://localhost:8080/topjava_graduation/profile/votes/today

User can delete his vote and it won't be count in voting results
> DELETE "{vote_id}" http://localhost:8080/topjava_graduation/profile/votes

User may change his profile:
> PUT {"id": 10001,"name":"Jack","email":"jacke@gmail.com","password":"password","registered":"2021-12-15T12:33:05.947","roles":["USER"]} http://localhost:8080/topjava_graduation/profile

And delete yourself:
> DELETE http://localhost:8080/topjava_graduation/profile

For change voting dead_line user constant ru.topjava.graduation.utils.DateTimeUtils.TOO_LATE

Extra curl-commands: /config/curl.md