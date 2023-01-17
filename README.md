# tasklist-graphql-adapter
Адаптер(переходник) между camunda rest api и существующим tasklist_lite flutter frontend.
Предполагается взаимодействие с camunda по схеме remote PE (в варианте platform 7 -- Camunda Run).
Для доступа к camunda rest api использует библиотеку camunda-platform-7-rest-client-spring-boot-openapi 
(https://github.com/camunda-community-hub/camunda-platform-7-rest-client-spring-boot) в варианте standalone
usage, то есть не имея зависимостей от camunda PE на classpath.

# запуск
Как обычно, ./gradlew bootRun или через IDE

# подключение к Camunda Run
Имей развернутую Camunda (можно не обязательно в варианте Run, главное чтобы был доступен Rest API).
Укажи адрес rest endpoint`а camunda`ы в application.properties. #TODO: учетка для подключения. 

# проверка
Например, через altair (https://altairgraphql.dev/). Поставь его себе как приложение или плагин к chrome.
Имей запущенную camunda run (https://downloads.camunda.cloud/release/camunda-bpm/run/). Для проверки хорошо,
чтобы в Камунде были какие-то задачки, она была непустая. Поэтому поделай там разные процессы, погоняй задачи.
В Альтаире укажи url к graphql endpoint`у запущенного приложения (по умолчанию http://localhost:8080/graphql) и метод POST.
Для аутентификации в альтаире используй заголовок basic-аутентификации, например, для подключения под 
test:test_pass нужен заголовок Authorization: Basic dGVzdDp0ZXN0X3Bhc3M= (где dGVzdDp0ZXN0X3Bhc3M= -- это
BASE-64 encoded значение строки test:test_pass). 
Потом в левой части окна altair укажи свою query (или мутацию), нажми Send Request и получи результат в правой
части окна:
![Тут просто картинка, ничего важного](https://github.com/kostd/tasklist-graphql-adapter/blob/master/screenshots/altair-1.png)