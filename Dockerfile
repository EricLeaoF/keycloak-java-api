# FROM openjdk:16-jdk-slim
# ARG JAR_FILE=target/*.jar
# COPY ${JAR_FILE} app.jar
# RUN bash -c 'touch /app.jar'
# ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar","/app.jar"]
# Usar uma imagem base do OpenJDK
FROM openjdk:19-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo JAR para o container
COPY . /app

RUN ./mvnw install -DskipTests

# Comando para rodar a aplicação
CMD ["java", "-Dspring.devtools.restart.enabled=true", "-jar", "api-0.0.1-SNAPSHOT.jar"]
