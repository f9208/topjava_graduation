## *****Methods RestaurantController*****

get /topjava_graduation/restaurants - список ресторанов без еды 
get /topjava_graduation/restaurants/with-menu - вывод списка ресторанов вместе с меню 
get /topjava_graduation/restaurants/{id} - вывод называния ресторана (без меню) (..todo переделать с едой)

//todo следующие методы должны делать только пользователи с ролью админ
get /topjava_graduation/restaurants/{id}/votes - список голосовавших за этот ресторан сегодня
get /topjava_graduation/restaurants/{id}/votes?start&end - список голосов за этот ресторан с start по end

post post /topjava_graduation/restaurants - добавлять новый ресторан. возвращается {id} нового ресторана

delete удалить указанный ресторан можно двумя способами:
delete /topjava_graduation/restaurants/{id} - удалит ресторан {id} delete /topjava_graduation/restaurants?delete={id} -
удалит ресторан {id}

put /topjava_graduation/restaurants/{id} - можно внести изменение в название ресторана с указанным {id}

## **Methods DishController**

get /topjava_graduation/restaurants/{restaurant_id}/menu - вывод меню для указанного в {restaurant_id} ресторана 
get /topjava_graduation/restaurants/{restaurant_id}/menu/{dish_id} - возвращает запись указанного блюда в меню

сделать здесь доступ только пользователям с ролью Админ
post /restaurants/{restaurant_id}/menu - добавляет dish в меню
put /restaurants/{restaurant_id}/menu/{dish_id} - Обновляет dish в меню. в случае несуществующего {dish_id} создает новый 
delete /restaurants/{restaurant_id}/menu/{dish_id} - удаляет указанную запись в меню

## **Methods VoteController**

---------------авторизованный---------------
get /votes - выводит все голоса для залогиненного пользователя с указанием ресторанов
get /votes/today - показывает как проголосовал пользователь сегодня
get /votes/{vote_id} - показывает голос пользователя по vote_id  
get /votes/filter?start&end - показывает, как проголосовал пользователь в интервале времени с указанием ресторанов
get /votes/results - показывает результат голосований 