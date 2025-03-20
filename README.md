# Trainer API

Проект представляет собой REST API сервис для отслеживания дневной нормы калорий пользователя и учета съеденных блюд. Система позволяет пользователю добавлять свои данные, получать отчет по калориям, а также отслеживать свою историю питания.

## Технологии

- **Spring Boot 3.4.3** - для создания REST API.
- **Spring Data JPA** - для работы с базой данных PostgreSQL.
- **ModelMapper** - для преобразования Dto объектов.
- **JUnit 5** - для написания юнит-тестов.
- **PostgreSQL** - база данных.
- **Maven** - для управления зависимостями.
- **Swagger** - для знакомства и тестирования API

## Запуск проекта

1. Клонируйте репозиторий:
```bash
git clone https://github.com/your-username/trainer.git
cd trainer
```

2. Настройте **Docker Compose** для запуска базы данных PostgreSQL. В папке проекта у вас должен быть файл `docker-compose.yml`. Запустите контейнеры с базой данных:
```bash
docker-compose up -d
```
Эта команда запустит контейнеры для PostgreSQL.

3. Установите зависимости:
```bash
mvn clean install
```

4. Настройте параметры подключения к базе данных в файле `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/trainer_db
spring.datasource.username=postgres
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```

5. Запустите приложение:
```bash
mvn spring-boot:run
```

6. API будет доступен по адресу: `http://localhost:8080`.

### Swagger UI:
После того как приложение запустится, вы сможете получить доступ к Swagger UI для знакомства и тестирования API, перейдя по адресу: `http://localhost:8080/swagger-ui.html`.



