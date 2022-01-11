####��������� ������ ����� javaops.ru �����������
������� [���](http://github.com/f9208/topjava_graduation/blob/mastertask.md)

����������� ������� ������������� ������ ������� REST ����������, � �������� ��� ��������� ���������� � ��������� ���
�� [heroku](http://choicerestaurant.herokuapp.com/). ��������, ���������� ����������� �� �����, ����� 15-20 ������ -
��� ����������� ����������� ������ � heroku, ���������� �������� ���� ��� �� ���������� ������ 30 �����.

����������� � ���� �����: 

admin@ 




####REST API Specification
curl ������� ����� ����� � ������ /config/curl.md � /config/requests_examples

#### ����������� ��������������������� ������������: 
> �������� [������ ����������](http://choicerestaurant.herokuapp.com/rest/restaurants)  
> �������� ��������� ������ � �� ���� �� [�������](http://choicerestaurant.herokuapp.com/rest/restaurants/{restaurant-id}/dishes)
> �������� ��������� [�����](http://choicerestaurant.herokuapp.com/rest/restaurants/{restaurant-id}/dishes/{dish_id}) ��� ����������� ���������. �������������� ��� ����� ������� � ���������� 
> �������� ��� ��������� ������� �� ��������� �������� http://localhost:8080/topjava_graduation/restaurants/{restaurant-id}/votes
> �������� ����� path variables start � end, ������������ ��� ������ ���������� ��������� � �������� ��������� ���.

����� ������������ ����� ����� ������������������, ����� ���� ���������� �� ��������� �� ��������. ����������� ������������ ���������� email.
> POST {"name":"Stenly","email":"sten@gmail.com","password":"passwordSten","registered":"2021-12-15T12:33:05.947","roles":["USER"]}' [http://choicerestaurant.herokuapp.com/rest/profile/register]

�������� ������ �� ������� �������� (���� �������� start � end ������ - �������� ������ �� �������)
> � date_start �� date_end http://choicerestaurant.herokuapp.com/rest/restaurants/{restaurant-id}/votes?start={start_date}&end={end_date}

#### ����������� ������������ � ����� ADMIN

������������, ��� ����� �������� � �������, ����� ������ ����: �������� ������� ��� ������� ����� �� �����. �������������� ��� ������������ ��� ���� ����������������.
����� ADMIN ����� ��������� ����� ���������:
> POST {"name":"new Shaverma"}  http://choicerestaurant.herokuapp.com/rest/admin/restaurants/

�������� �������� (�������� ��������� ������ ��������)
> PUT '{"id":"10001","name":"DeadFish"}' http://localhost:8080/topjava_graduation/admin/restaurants/10001

�������� ����� ����� � ���� ��������� {restaurant-id}. �������������� ��� �������� ����� ��� ������ ��������� �� �����������
> POST {"name":"burger","day":"2021-05-23","price":150}' http://choicerestaurant.herokuapp.com/rest/admin/restaurants/{restaurant-id}/dishes/

�������� ������������ �����:
> PUT {"id":10002, "name":"coca-cola","day":"2021-05-23","price":50}' http://choicerestaurant.herokuapp.com/rest/admin/restaurants/{restaurant-id}/dishes/

������� ����� �� ��� id
> DELETE http://choicerestaurant.herokuapp.com/rest/admin/restaurants/{restaurant-id}/dishes/{dish-id}

�������� ������ �������������
> http://choicerestaurant.herokuapp.com/rest/admin/users/

�������� ������ ������������� �� �� Id � �������� ��������� ���. ���� ���� ������ - �������� �� �������, 
���� Id ������������ �� ��������� - �� �������� ������ ���� �������������.
> http://choicerestaurant.herokuapp.com/rest/admin/votes?start={date}&end={date}&userId={number}

������� ���� ������������:
> PATCH http://choicerestaurant.herokuapp.com/rest/admin/users?role=ADMIN,USER&id={user-id}

������� ������������:
> DELETE http://choicerestaurant.herokuapp.com/rest/admin/users/{user-id}

#### ����������� ������������ � ����� USER

�������������� ��� ������������ ����������������.

������������� �� ��������� ��������
> POST {restaurant_id} http://choicerestaurant.herokuapp.com/rest/votes

~~������������ ����� ���������� � �������������� ������ �� 11:00 AM. ���� �� ���������� ������������� �����, ��� ����� �� ����� �����.~~ **��� ������� ����������� �� ��������� - ����� ���, ��� ����� �������� ���������� ����� ����� �� ������ ������ � ��� �������**  
> PUT {restaurant_id} http://choicerestaurant.herokuapp.com/rest/votes/{vote_id}

�������� ������� ������������
> http://choicerestaurant.herokuapp.com/rest/profile

�������� ���������� ������, �������� ���� �������������.
> http://choicerestaurant.herokuapp.com/rest/profile/votes?start={start_date}&end={end_date}

������������ ����� ������� ��� ����� � ���� ����� �� ����� �����
> DELETE http://choicerestaurant.herokuapp.com/rest/profile/votes/{vote_id}

������������ ����� ������� ������ ������ �������
> PUT {"id": 10001,"name":"Jack","email":"jacke@gmail.com","password":"password","registered":"2021-12-15T12:33:05.947","roles":["USER"]} http://choicerestaurant.herokuapp.com/rest/profile

� �������������� ���������
> DELETE http://choicerestaurant.herokuapp.com/rest/profile

