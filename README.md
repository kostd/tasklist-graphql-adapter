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
Через altair (ну или сразу клиентом). #TODO: 
