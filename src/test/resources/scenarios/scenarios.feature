Feature: Тестирование Reqres API
      Background: тестирование https://reqres.in/

  Scenario: получить одного пользователя по userId
    When Отправляем GET запрос на URL "https://reqres.in/api/users/{id}", c id=2
    Then Получаем statusCode=200 и пользователя с userId=2

  Scenario: не получить одного пользователя по userId
    When Отправляем GET запрос на URL "https://reqres.in/api/users/{id}", c id=67
    Then Получаем statusCode=404