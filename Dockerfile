FROM openjdk:21

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем приложение в контейнер
COPY target/ServerAdd-0.0.1-SNAPSHOT.jar ServerAdd.jar

# Запускаем приложение
CMD ["java", "-jar", "ServerAdd.jar"]
