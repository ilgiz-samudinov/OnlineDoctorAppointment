````markdown
# Online Doctor Appointment
## Описание проекта
**Online Doctor Appointment** — веб-приложение для управления онлайн-записью пациентов к врачам. Обеспечивает функциональность бронирования, просмотра расписания, управления данными врачей и пациентов.

## Особенности

- **Просмотр расписания врачей**: фильтрация по специализации и доступным датам
- **Запись и отмена приёма**: подтверждение записи через веб-интерфейс
- **Управление профилями**: CRUD-операции для врачей и пациентов
- **Административная панель**: мониторинг записей

## Стек технологий

| Компонент         | Технология               |
|-------------------|--------------------------|
| Язык программирования | Java 21                |
| Фреймворк         | Spring Boot 3           |
| Шаблонизатор      | Thymeleaf               |
| Вёрстка           | HTML5, CSS3,          |
| База данных       | PostgreSQL              |
| ORM               | Spring Data JPA         |
| Безопасность      | Spring Security         |
| Сборка проекта    | Maven                   |

## Архитектура

Приложение построено по классической многослойной архитектуре:

1. **Controller** — REST и MVC контроллеры для обработки HTTP-запросов
2. **Service** — бизнес-логика приложения
3. **Repository** — интерфейсы Spring Data JPA для работы с БД
4. **Model** — сущности и DTO

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

1. Клонировать репозиторий:

   ```bash
   git clone https://github.com/ilgiz-samudinov/OnlineDoctorAppointment.git
   cd OnlineDoctorAppointment
   ```
2. Настроить базу данных PostgreSQL:

    * Создать базу данных:

      ```sql
      CREATE DATABASE online_doctor;
      ```
    * В файле `src/main/resources/application.yml` указать параметры подключения:

      ```yaml
      spring:
        datasource:
          url: jdbc:postgresql://localhost:5432/online_doctor
          username: <DB_USERNAME>
          password: <DB_PASSWORD>
        jpa:
          hibernate:
            ddl-auto: update  # use 'validate' in production
          properties:
            hibernate.dialect: org.hibernate.dialect.PostgreSQLDialect
      ```
3. Сборка и запуск приложения:

   ```bash
   mvn clean package
   java -jar target/OnlineDoctorAppointment-0.0.1-SNAPSHOT.jar
   ```
4. Открыть в браузере: [http://localhost:8080](http://localhost:8080)

## Конфигурационные параметры

| Параметр                        | Описание                    | По умолчанию |
| ------------------------------- | --------------------------- | ------------ |
| `server.port`                   | Порт сервера                | 8080         |
| `spring.jpa.hibernate.ddl-auto` | Стратегия создания схемы БД | update       |

Дополнительные параметры можно задать в `application.yml`.

## API и маршруты

### MVC-контроллеры

#### Запись на приём

* `GET  /appointment/` — главная страница приложения (index)
* `GET  /appointment/check` — форма поиска клиента по телефону
* `GET  /appointment/search-client?phone={phone}` — поиск клиента, редирект на регистрацию или отображение данных клиента
* `GET  /appointment/select-client` — выбор врача из списка
* `GET  /appointment/search-doctor?keyword={keyword}` — поиск врачей по ключевому слову или отображение всех при отсутствии фильтра
* `POST /appointment/select-doctor` — выбор врача (передаётся `doctorId`), отображение доступных временных слотов
* `POST /appointment/select-date` — выбор даты и времени приёма (параметры `appointmentDate`, `appointmentTime`), перенаправление на сохранение записи
* `POST /appointment/save` — сохранение записи пациента к врачу, вывод подтверждения с данными клиента, врача, даты и времени
* `GET  /appointment/all` — вывод списка всех записей (для администратора)

#### Клиенты

* `GET  /client/create` — форма создания нового клиента
* `POST /client/create` — сохранение нового клиента, редирект на выбор врача
* `GET  /client/all` — вывод списка всех клиентов
* `GET  /client/edit/{id}` — форма редактирования клиента по идентификатору
* `POST /client/edit/{id}` — обновление данных клиента
* `POST /client/delete/{id}` — удаление клиента по идентификатору

#### Врачи

* `GET  /doctor/all` — список всех врачей
* `GET  /doctor/create` — форма создания нового врача
* `POST /doctor/create` — сохранение нового врача
* `GET  /doctor/edit/{id}` — форма редактирования врача
* `POST /doctor/edit/{id}` — обновление данных врача
* `POST /doctor/delete/{id}` — удаление врача
* `GET  /doctor/search?keyword={keyword}` — поиск врачей по ключевому слову

#### Очередь приёмов

* `GET  /queue/all` — список всех очередей приёма
* `POST /queue/update` — обновление статуса очереди (параметры `id`, `newStatus`)
