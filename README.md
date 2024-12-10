# Опис компонентів коду

## Клас `Patient` (Пацієнт)

Використовує `data class` для зберігання даних про пацієнта.

### Поля:
- `id`: Унікальний ідентифікатор пацієнта.
- `name`: Ім'я пацієнта.
- `dateOfBirth`: Дата народження пацієнта.
- `phoneNumber`: Номер телефону (nullable).
- `medicalHistory`: Медична історія (nullable).

### Метод:
- `calculateAge`: Розраховує вік пацієнта на основі дати народження та поточної дати.

---

## Клас `Dentist` (Лікар)

Зберігає дані про лікаря.

### Поля:
- `id`: Унікальний ідентифікатор лікаря.
- `name`: Ім'я лікаря.
- `specialization`: Спеціалізація лікаря.

---

## Клас `Appointment` (Прийом)

Зберігає дані про прийом.

### Поля:
- `id`: Ідентифікатор прийому.
- `patient`: Пацієнт, якого обслуговують.
- `dentist`: Лікар, який проводить прийом.
- `date`: Дата прийому.
- `notes`: Примітки (nullable).
- `services`: Список послуг, наданих на прийомі.

---

## Шаблони проектування

### Builder (Породжуючий шаблон)

Клас `AppointmentBuilder` використовується для поступового створення об'єктів `Appointment`. Забезпечує гнучкість та зручність при додаванні нових параметрів.

### Decorator (Структурний шаблон)

Інтерфейс `AppointmentService` додає можливість розширення функціональності базового прийому.

- **BasicAppointment**: Базовий прийом.
- **WhiteningDecorator**: Декоратор для додавання послуги відбілювання зубів.
- **XRayDecorator**: Декоратор для додавання послуги рентгену.

### Observer (Поведінковий шаблон)

Використовується для відправки сповіщень підписникам при додаванні нового прийому.

- **AppointmentObserver**: Інтерфейс для підписників.
- **EmailNotifier**: Реалізація, що надсилає email.
- **SmsNotifier**: Реалізація, що надсилає SMS.

---

## Основний клас `DentalClinic`

Зберігає пацієнтів, лікарів та прийоми у відповідних колекціях (List). Підтримує механізм підписників через список observers.

### Методи:
- `addPatient`: Додає нового пацієнта.
- `addDentist`: Додає нового лікаря.
- `addAppointment`: Додає новий прийом та сповіщає підписників.
- `findPatientsByName`: Пошук пацієнтів за ім'ям.
- `getAppointmentsByDentist`: Отримання прийомів певного лікаря.
- `getUpcomingAppointments`: Отримання майбутніх прийомів.
- `printPatients`: Виведення списку пацієнтів у консоль.
- `printAppointments`: Виведення списку прийомів у консоль.

---

## `main`

1. Створює об'єкт `DentalClinic`.
2. Реєструє сповіщувачів (EmailNotifier і SmsNotifier).
3. Додає пацієнтів, лікарів та прийоми:
    - Використовує `Builder` для створення прийомів.
    - Додає декоратори для додаткових послуг.
4. Виводить інформацію про пацієнтів та прийоми.
