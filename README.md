# Diploma по курсу «Тестировщик ПО»

### Тестирование сервиса покупки тура

## Документация
- [План автоматизации тестирования](https://github.com/BethPech/Diploma/blob/de0612cc9409921fa495cf0cfabf20cd41c60b4d/Plan.md)
- [Отчет по итогам тестирования](https://github.com/BethPech/Diploma/blob/de0612cc9409921fa495cf0cfabf20cd41c60b4d/documents/Report.md)
- [Отчет по итогам автоматизации](https://github.com/BethPech/Diploma/blob/de0612cc9409921fa495cf0cfabf20cd41c60b4d/documents/Summary.md)

## Перед началом работы
*Необходимо, чтобы были уставновлены:*
- IntelliJ IDEA
- Docker desktop / docker toolbox
1. Используя команду `https://github.com/BethPech/Diploma.git` скачать себе данный репозиторий

2. **Запускаем docker-контейнер с MySQL , PostgreSQL и Node.js.**
- ввести в терминале(консоле) команду `docker-compose up`

3.**Для запуска SUT открываем новую вкладку в Терминале IDEA и вводим следующую команду:**
- для **MySQL**:
`java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar ./artifacts/aqa-shop.jar`
Для повторного использования тестов необходимо перезапустить SUT
- для **PostgreSQL**:
`java -Dspring.datasource-postgresql.url=jdbc:postgresql://localhost:5432/app -jar ./artifacts/aqa-shop.jar`
 
 4.**Для запуска авто-тестов необходимо открыть новую вкладку в теминале и ввести команду:**
 - для **MySQL**: `gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app`
 - для **PostgreSQL**: `gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app`'
 
 5.**Для создания отчета Allure ввести в терминале команду:**
 `gradlew allureServe`
 
