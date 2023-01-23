# imdb
DB - MySQL
phpMyAdmin: http://www.phpmyadmin.co
server: sql8.freesqldatabase.com:3306/sql8591294
Username: sql8591294
password: CAcU7XGhNj

Frameworks:
  jsoup - для работы с html страницой
  mysql-connector-java - jdbc, для работы с базой данных MySQL

Программа при запуске предлагает на выбор 3 действия:
  [1] - insert data to db ( заходит на сайт https://www.imdb.com/chart/top/ и сохраняет весь топ в базу данных)
  [2] - enter sql (Требует sql для исполнения)
  [3] - leave (Выход из программы)

Программа сама создает таблицу если ее не существует.
Структура таблицы (id, name, age, rate)

Пример исполнения команды 1:
![image](https://user-images.githubusercontent.com/45192232/213996688-47c8def5-79c3-4435-b75e-b7c6062b5b56.png)

Пример исполнения команды 2:
![image](https://user-images.githubusercontent.com/45192232/213996212-f4f22a0b-224d-4140-b39d-6aee933ff0b9.png)
![image](https://user-images.githubusercontent.com/45192232/213996226-10f1a327-d68d-4318-8d28-b0e79345361f.png)
