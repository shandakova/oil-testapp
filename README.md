# oil-testapp

Тестовое задание “Парсинг данных и API”

Необходимо распарсить открытые данные о цене на сырую нефть марки и предоставить к ним доступ по API.

За основу данных взят индекс мосбиржи, за цену взят параметр OPEN.

## Описание структуры проекта

Для проекта использовался Scala, Play Framework, Anorm и Postgres

/conf - конфигурация проекта

- conf/evolutions/default - скрипты бд
- conf/data - данные

/app - основные файлы проекта

- /app/dao - слой доступа к БД
- /app/services - сервисный слой. Два сервиса для доступа к данным и для парсинга файла
- /app/controllers - слой контроллеров. Три контроллера: для работы с ценами, для запуска чтения файла и для создания
  документации
- /app/model - объекты для парсинга ответов из бд

## Описание API

Интерактивную версию можно посмотреть при запуске проекта на эндопинте /docs

GET /oil-price - Метод выдает цену масла, на заданную дату, переданную в параметром строке. (Все даты здесь и далее
ожидаются в формате YYYY-MM-dd)

`http://localhost:9000/oil-price?date=2000-10-10`

GET /oil-price/average - Метод выдает среднюю цену масла на период

`http://localhost:9000/oil-price/average?startDate=2000-10-10&endDate=2030-10-10`

GET /oil-price/bounds - Метод выдает json c максимальной и минимальной ценой на период

`http://localhost:9000/oil-price/bounds?startDate=2000-10-10&endDate=2030-10-10`

GET /oil-price/statistic - Метод выдает количество записей в базе данных

`http://localhost:9000/oil-price/statistic`

POST /oil-price/update - Метод запускает процесс чтения файла и выдает его результат

`http://localhost:9000/oil-price/update`