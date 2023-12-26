# Cinema

## Работу выполнил 
Мухин Дмитрий БПИ228

## Диаграммы
[Use Case Diagram](use_case.png)

[Class Diagram](class_diagram.png)

## Выбор формата хранения данных
Выбрал JSON, так как этот формат удобно читать и изменять. В файле видно значения по ключам, в нем хранится список, где каждый элемент является объектом, представленным в виде словаря ключей. Удобно сериализовать и десериализовать, необходимо лишь преобразовать струкутуру данных, в которой хранится информация о фильмах, сеансах и пользователях, в список.

## Как пользоваться приложением?
- В самом начале работы программы происходит попытка загрузить данные о фильмах, сеансах и польователях. Если какой-то файл не удается загрузить, пользователь получает соответсвующее сообщение.
## Регистрация
- Далее пользователь попадает в меню регистрации.
- На этом этапе он должен войти в аккаунт или зарегестрироваться.
- После успешного входа пользователь попадает в главное меню.
## Главное меню
- После пользователь видит главное меню, из него он может попасть в меню работы с фильмами, или с сеансами, или с местами в зале.
- Он выбирает соответствующую опцию и переходит в другое меню.
## Меню работы с фильмами
В этом меню есть возможность:
- Добавить фильм
- Изменить название фильма
- Изменить продолжительность фильма
- Посмотреть все фильмы
- Выйти в главное меню
## Меню работы с сеансами
В этом меню есть возможность:
- Добавить сеанс
- Изменить время сеанса
- Изменить фильм на сеансе
- Показать все сеансы
- Выйти в главное меню
## Меню работы с местами
В этом меню есть возможность:
- Отметить место купленным
- Вернуть место
- Отметить место занятым
- Показать все места
- Показать свободные места
- Показать купленные места
- Выйти в главное меню
## Пример работы программы
```
Registration menu. Enter
"log in" If you already have an account
"sign up" In order to make an account
"exit" In order to finish program
log in
Enter login and password in format: login; password
Dmitry-Pr; 123456
Main menu. Enter
"movie" In order to add or change a movie
"session" In order to add or change a session
"places" In order to work with places
"exit" In order to finish program
session
Session menu. Enter
"add" In order to add a session
"change time" In order to change a session start time
"change movie" In order to change the session movie 
"show all" In order to see the list of sessions
"exit" In order to exit to main menu
show all
Session. Id: 0 starts: 2023-12-26T18:00 movie: 0 Movie. id: 0 name: Titanic director: Cameron duration: 3h
Session. Id: 1 starts: 2023-12-26T21:01 movie: 1 Movie. id: 1 name: Avatar director: Cameron duration: 2h 40m
Session menu. Enter
"add" In order to add a session
"change time" In order to change a session start time
"change movie" In order to change the session movie 
"show all" In order to see the list of sessions
"exit" In order to exit to main menu
exit
Left session section
Main menu. Enter
"movie" In order to add or change a movie
"session" In order to add or change a session
"places" In order to work with places
"exit" In order to finish program
places
Places menu. Enter
"buy" In order to mark the bought place
"return" In order to mark the free place   
"take" In order to mark the taken place 
"show all" In order to see all places
"show free" In order to see free places
"show bought" In order to see bought places
"exit" In order to exit to main menu
show all
Enter sessionId
0
         ===============Screen================
Row 1	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	
Row 2	⚪	⚪	⚪	⚪	⚫	⚪	⚪	⚪	⚪	⚪	
Row 3	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	
Row 4	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	
Row 5	⚪	⚪	⚪	⚪	⚪	⚪	⚫	⚪	⚪	⚪	
Row 6	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	
Row 7	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	
Row 8	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	
Row 9	⚪	⚫	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	
Row 10	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	⚪	
Place	1	2	3	4	5	6	7	8	9	10	
Places menu. Enter
"buy" In order to mark the bought place
"return" In order to mark the free place   
"take" In order to mark the taken place 
"show all" In order to see all places
"show free" In order to see free places
"show bought" In order to see bought places
"exit" In order to exit to main menu
exit
Left places section
Main menu. Enter
"movie" In order to add or change a movie
"session" In order to add or change a session
"places" In order to work with places
"exit" In order to finish program
exit
Program is finished
```
