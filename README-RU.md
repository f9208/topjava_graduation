####Выпускной проект курса javaops.ru Техническое
задание [тут](http://github.com/f9208/topjava_graduation/blob/mastertask.md)

Изначальное задание подразумевало только наличие REST интерфейса, я дополнил его небольшим фронтендом и задеплоил его
на [heroku](http://choicerestaurant.herokuapp.com/). Внимание, приложение открывается не сразу, через 15-20 секунд -
это особенность бесплатного пакета в heroku, приложение засыпает если его не используют дольше 30 минут.

Добавленные в базу юзеры: 

admin@ 




####REST API Specification
curl команды можно найти в файлах /config/curl.md и /config/requests_examples

#### Возможности незарегистрированного пользователя: 
> показать [список ресторанов](http://choicerestaurant.herokuapp.com/rest/restaurants)  
> показать рестораны вместе с их меню на [сегодня](http://choicerestaurant.herokuapp.com/rest/restaurants/{restaurant-id}/dishes)
> показать единичное [блюдо](http://choicerestaurant.herokuapp.com/rest/restaurants/{restaurant-id}/dishes/{dish_id}) для конкретного ресторана. Предполагается что блюдо связано с рестораном 
> показать кто голосовал сегодня за указанный ресторан http://localhost:8080/topjava_graduation/restaurants/{restaurant-id}/votes
> доступны также path variables start и end, показывающие все голоса указанного ресторана в пределах указанных дат.

Новый пользователь также может зарегистрироваться, после чего голосовать за выбранный им ресторан. Регистрация предполагает уникальные email.
> POST {"name":"Stenly","email":"sten@gmail.com","password":"passwordSten","registered":"2021-12-15T12:33:05.947","roles":["USER"]}' [http://choicerestaurant.herokuapp.com/rest/profile/register]

Показать голоса за текущий ресторан (если значения start и end пустые - показать голоса за сегодня)
> с date_start по date_end http://choicerestaurant.herokuapp.com/rest/restaurants/{restaurant-id}/votes?start={start_date}&end={end_date}

#### Возможности пользователя с ролью ADMIN

Пользователь, кто также является и админом, может менять меню: временно убирать или удалять какие то блюда. Предполагается что пользователь при этом аутентифицирован.
Также ADMIN может добавлять новые рестораны:
> POST {"name":"new Shaverma"}  http://choicerestaurant.herokuapp.com/rest/admin/restaurants/

Изменить ресторан (доступна изменения только названия)
> PUT '{"id":"10001","name":"DeadFish"}' http://localhost:8080/topjava_graduation/admin/restaurants/10001

Добавить новое блюдо в меню ресторана {restaurant-id}. Предполагается что название блюда для одного ресторана не повторяются
> POST {"name":"burger","day":"2021-05-23","price":150}' http://choicerestaurant.herokuapp.com/rest/admin/restaurants/{restaurant-id}/dishes/

Обновить существующее блюдо:
> PUT {"id":10002, "name":"coca-cola","day":"2021-05-23","price":50}' http://choicerestaurant.herokuapp.com/rest/admin/restaurants/{restaurant-id}/dishes/

Удалить блюдо по его id
> DELETE http://choicerestaurant.herokuapp.com/rest/admin/restaurants/{restaurant-id}/dishes/{dish-id}

Показать список пользователей
> http://choicerestaurant.herokuapp.com/rest/admin/users/

Показать голоса пользователей по их Id в пределах указанных дат. Если даты пустые - показать за сегодня, 
если Id пользователя не указывать - то показать голоса всех пользователей.
> http://choicerestaurant.herokuapp.com/rest/admin/votes?start={date}&end={date}&userId={number}

Сменить роль пользователя:
> PATCH http://choicerestaurant.herokuapp.com/rest/admin/users?role=ADMIN,USER&id={user-id}

Удалить пользователя:
> DELETE http://choicerestaurant.herokuapp.com/rest/admin/users/{user-id}

#### Возможности пользователя с ролью USER

Предполагается что пользователь аутентифицирован.

Проголосовать за указанный ресторан
> POST {restaurant_id} http://choicerestaurant.herokuapp.com/rest/votes

~~Пользователь может передумать и переголосовать только до 11:00 AM. Если он попытается проголосовать позже, его голос не будет учтен.~~ **Эта функция реализована но отключена - иначе все, кто будет смотреть приложение после обеда не смогут ничего с ним сделать**  
> PUT {restaurant_id} http://choicerestaurant.herokuapp.com/rest/votes/{vote_id}

Показать профиль пользователя
> http://choicerestaurant.herokuapp.com/rest/profile

Показать предыдущие голоса, отданные этим пользователем.
> http://choicerestaurant.herokuapp.com/rest/profile/votes?start={start_date}&end={end_date}

Пользователь может удалить его голос и этот голос не будет учтен
> DELETE http://choicerestaurant.herokuapp.com/rest/profile/votes/{vote_id}

Пользователь может сменить данные своего профиля
> PUT {"id": 10001,"name":"Jack","email":"jacke@gmail.com","password":"password","registered":"2021-12-15T12:33:05.947","roles":["USER"]} http://choicerestaurant.herokuapp.com/rest/profile

И самостоятельно удалиться
> DELETE http://choicerestaurant.herokuapp.com/rest/profile

