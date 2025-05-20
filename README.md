````markdown
# Online Doctor Appointment

## Описание проекта
**Online Doctor Appointment** — веб-приложение для управления онлайн-записью пациентов к врачам. Предоставляет возможности:

- Просматривать расписание врачей с фильтрацией по специализации и доступным датам
- Записывать и отменять приём через веб-интерфейс
- Управлять профилями врачей и пациентов (CRUD)
- Административно контролировать записи и очереди приёмов
- Обеспечивать безопасность через Spring Security
- Логировать действия с помощью SLF4J и Logback

## Стек технологий

| Компонент             | Технология               |
|-----------------------|--------------------------|
| Язык программирования | Java 21                  |
| Фреймворк             | Spring Boot 3            |
| Шаблонизатор          | Thymeleaf                |
| Вёрстка               | HTML5, CSS3              |
| База данных           | PostgreSQL               |
| ORM                   | Spring Data JPA          |
| Безопасность          | Spring Security          |
| Сборка проекта        | Maven                    |

## Архитектура

Классическая многослойная архитектура:
1. **Controller** — MVC-контроллеры для UI и REST-контроллеры для API
2. **Service** — бизнес-логика
3. **Repository** — интерфейсы Spring Data JPA
4. **Model** — сущности и DTO
5. **Configuration** — настройка Spring Boot и компонентов

## Структура проекта

```text
src/main/java/org/example/oma/
├── controller/
│   ├── AppointmentController.java
│   ├── ClientController.java
│   ├── DoctorController.java
│   └── QueueController.java
├── model/
│   ├── Appointment.java
│   ├── AppointmentStatus.java
│   ├── Client.java
│   ├── Doctor.java
│   ├── Queue.java
│   └── QueueStatus.java
├── repository/
│   ├── AppointmentRepository.java
│   ├── ClientRepository.java
│   ├── DoctorRepository.java
│   └── QueueRepository.java
├── service/
│   ├── AppointmentService.java
│   ├── ClientService.java
│   ├── DoctorService.java
│   └── QueueService.java
└── OmaApplication.java
````

## Установка и запуск

1. Установить JDK 21 и Maven.
2. Клонировать репозиторий:

   ```bash
   git clone https://github.com/ilgiz-samudinov/OnlineDoctorAppointment.git
   cd OnlineDoctorAppointment
   ```
3. Создать базу данных PostgreSQL:

   ```sql
   CREATE DATABASE online_doctor;
   ```
4. В `src/main/resources/application.properties` указать параметры подключения:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/online_doctor
   spring.datasource.username=<DB_USERNAME>
   spring.datasource.password=<DB_PASSWORD>
   spring.jpa.hibernate.ddl-auto=update  # используйте 'validate' в продакшене
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   ```
5. Запустить приложение:

   ```bash
   mvn spring-boot:run
   ```

   Или собрать и запустить jar-файл:

   ```bash
   mvn clean package
   java -jar target/OMA-0.0.1-SNAPSHOT.jar
   ```

## Конфигурационные параметры

| Параметр                        | Описание                    | По умолчанию |
| ------------------------------- | --------------------------- | ------------ |
| `server.port`                   | Порт сервера                | 8080         |
| `spring.jpa.hibernate.ddl-auto` | Стратегия создания схемы БД | update       |

Дополнительные настройки можно добавить в `application.properties`.

## API и маршруты

### MVC-контроллеры

#### Запись на приём

* `GET  /appointment/` — главная страница
* `GET  /appointment/check` — форма поиска клиента по телефону
* `GET  /appointment/search-client?phone={phone}` — поиск клиента
* `GET  /appointment/select-client` — выбор врача
* `GET  /appointment/search-doctor?keyword={keyword}` — поиск врачей
* `POST /appointment/select-doctor` — выбор врача, отображение слотов
* `POST /appointment/select-date` — выбор даты/времени
* `POST /appointment/save` — сохранение приёма
* `GET  /appointment/all` — список всех приёмов

### Клиенты

* `GET  /client/create` — форма создания клиента
* `POST /client/create` — сохранение клиента
* `GET  /client/all` — список клиентов
* `GET  /client/edit/{id}` — форма редактирования
* `POST /client/edit/{id}` — обновление клиента
* `POST /client/delete/{id}` — удаление клиента

### Врачи

* `GET  /doctor/all` — список врачей
* `GET  /doctor/create` — форма создания врача
* `POST /doctor/create` — сохранение врача
* `GET  /doctor/edit/{id}` — форма редактирования
* `POST /doctor/edit/{id}` — обновление врача
* `POST /doctor/delete/{id}` — удаление врача
* `GET  /doctor/search?keyword={keyword}` — поиск врачей

### Очередь приёмов

* `GET  /queue/all` — список очередей приёмов
* `POST /queue/update` — обновление статуса очереди

