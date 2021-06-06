# API для опросов

## Технологический стек
### Сборка
- Maven
- docker
- docker-compose

### Backend
- Java8
- Spring (boot, data-jpa, web, security)
- Swagger (SpringFox)
- Lombok
- Hibernate
- PostgreSQL

### Сборка и запуск
`./mvnw clean package -DskipTests`

Запуск сервиса: `docker-compose up`.


## Взаимодействие с сервисом

### REST API
Swagger UI доступен по адресу `/swagger-ui.html`
Здесь аутентификация и авторизация для контроллеров не предусмотрена

## Инициализационные данные

### default
Создаётся:
- Admin: пароль и логин `admin`
- Тестовый пользователь с логином `userTest` и паролем `secret`.
