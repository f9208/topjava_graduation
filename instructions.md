## *****Methods RestaurantController*****

get /topjava_graduation/restaurants - список ресторанов без еды get /topjava_graduation/restaurants/with-menu - вывод
списка ресторанов вместе с меню get /topjava_graduation/restaurants/{id} - вывод называния ресторана, его id (без меню)
get /topjava_graduation/restaurants/{id}/menu - вывод меню для указанного в {id} ресторана

следующие методы должны делать только пользователи с ролью админ

post post /topjava_graduation/restaurants - добавлять новый ресторан. возвращается {id} нового ресторана

delete удалить указанный ресторан можно двумя способами:
delete /topjava_graduation/restaurants/{id} - удалит ресторан {id} delete /topjava_graduation/restaurants?delete={id} -
удалит ресторан {id}

put /topjava_graduation/restaurants/{id} - можно внести изменение в название ресторана с указанным {id}

## **Methods DishController**

get /restaurants/{restaurant_id}/menu/{dish_id} - возвращает запись указанного блюда в меню

сделать здесь доступ только пользователям с ролью Админ
post /restaurants/{restaurant_id}/menu - добавляет dish в меню
put /restaurants/{restaurant_id}/menu/{dish_id} - Обновляет dish в меню. в случае несуществующего {dish_id} создает новый 
delete /restaurants/{restaurant_id}/menu/{dish_id} - удаляет указанную запись в меню