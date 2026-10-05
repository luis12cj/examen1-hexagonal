# Etapa 1: Compilar la aplicación con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar configuración de dependencias y código fuente
COPY pom.xml .
COPY src ./src

# Compilar empaquetando en .jar saltando pruebas para agilizar el despliegue
RUN mvn clean package -DskipTests

# Etapa 2: Entorno de ejecución ligero
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copiar el .jar compilado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Puerto por defecto para Spring Boot
EXPOSE 8080

# Iniciar la aplicación pasando el puerto dinámico de Render
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar app.jar"]
