## *****Methods RestaurantController*****

get /topjava_graduation/restaurants - ������ ���������� ��� ��� 
get /topjava_graduation/restaurants/with-menu - ����� ������ ���������� ������ � ���� 
get /topjava_graduation/restaurants/{id} - ����� ��������� ��������� (��� ����)

//todo ��������� ������ ������ ������ ������ ������������ � ����� �����

post post /topjava_graduation/restaurants - ��������� ����� ��������. ������������ {id} ������ ���������

delete ������� ��������� �������� ����� ����� ���������:
delete /topjava_graduation/restaurants/{id} - ������ �������� {id} delete /topjava_graduation/restaurants?delete={id} -
������ �������� {id}

put /topjava_graduation/restaurants/{id} - ����� ������ ��������� � �������� ��������� � ��������� {id}

## **Methods DishController**

get /topjava_graduation/restaurants/{restaurant_id}/menu - ����� ���� ��� ���������� � {restaurant_id} ��������� 
get /topjava_graduation/restaurants/{restaurant_id}/menu/{dish_id} - ���������� ������ ���������� ����� � ����

������� ����� ������ ������ ������������� � ����� �����
post /restaurants/{restaurant_id}/menu - ��������� dish � ����
put /restaurants/{restaurant_id}/menu/{dish_id} - ��������� dish � ����. � ������ ��������������� {dish_id} ������� ����� 
delete /restaurants/{restaurant_id}/menu/{dish_id} - ������� ��������� ������ � ����