## *****Methods RestaurantController*****

[comment]: <> (get /topjava_graduation/restaurants - ������ ���������� ��� ��� )

[comment]: <> (get /topjava_graduation/restaurants/with-menu - ����� ������ ���������� ������ � ���� )

[comment]: <> (get /topjava_graduation/restaurants/{id} - ����� ��������� ��������� &#40;��� ����&#41; &#40;..todo ���������� � ����&#41;)

[comment]: <> (//todo ��������� ������ ������ ������ ������ ������������ � ����� �����)

[comment]: <> (get /topjava_graduation/restaurants/{id}/votes - ������ ������������ �� ���� �������� �������)

[comment]: <> (get /topjava_graduation/restaurants/{id}/votes?start&end - ������ ������� �� ���� �������� � start �� end)

post post /topjava_graduation/restaurants - ��������� ����� ��������. ������������ {id} ������ ���������

put /topjava_graduation/restaurants/{id} - ����� ������ ��������� � �������� ��������� � ��������� {id}

## **Methods DishController**

get /topjava_graduation/restaurants/{restaurant_id}/menu - ����� ���� ��� ���������� � {restaurant_id} ��������� 
get /topjava_graduation/restaurants/{restaurant_id}/menu/{dish_id} - ���������� ������ ���������� ����� � ����

������� ����� ������ ������ ������������� � ����� �����
post /restaurants/{restaurant_id}/menu - ��������� dish � ����
put /restaurants/{restaurant_id}/menu/{dish_id} - ��������� dish � ����. � ������ ��������������� {dish_id} ������� ����� 
delete /restaurants/{restaurant_id}/menu/{dish_id} - ������� ��������� ������ � ����

## **Methods VoteController**

---------------��������������---------------
get /votes - ������� ��� ������ ��� ������������� ������������ � ��������� ����������
get /votes/today - ���������� ��� ������������ ������������ �������
get /votes/{vote_id} - ���������� ����� ������������ �� vote_id  
get /votes/filter?start&end - ����������, ��� ������������ ������������ � ��������� ������� � ��������� ����������
get /votes/results - ���������� ��������� ����������� 