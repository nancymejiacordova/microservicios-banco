# Usa una imagen de OpenJDK ligera
FROM openjdk:11-jdk-slim

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el JAR generado por Maven al contenedor
COPY target/microserviciocuentas-0.0.1-SNAPSHOT.jar microserviciocuentas.jar

# Expone el puerto en el que corre el microservicio
EXPOSE 8081

# Comando para ejecutar el microservicio
CMD ["java", "-jar", "microserviciocuentas.jar"]
