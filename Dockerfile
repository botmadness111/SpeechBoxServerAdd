#FROM openjdk:21
#
#WORKDIR /.
#
#ADD /target/ServerAdd-0.0.1-SNAPSHOT.jar ServerAdd.jar
#
#ENTRYPOINT ["java", "-jar", "ServerAdd.jar"]

# Используем базовый образ OpenJDK
FROM openjdk:21

# Установка рабочей директории внутри контейнера
WORKDIR /app

# Копирование JAR-файла с вашим приложением в контейнер
COPY target/ServerAdd-0.0.1-SNAPSHOT.jar ServerAdd.jar

# Команда для запуска приложения при старте контейнера
CMD ["java", "-jar", "ServerAdd.jar"]