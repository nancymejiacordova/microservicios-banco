 # Prueba Técnica para Desarrollador Java Microservices

Descripción
Este repositorio contiene dos microservicios interconectados desarrollados con Java 11 y el framework Spring Boot 2. Los microservicios gestionan cuentas bancarias y transacciones, implementando mecanismos de seguridad mediante JSON Web Tokens (JWT) y utilizando Azure SQL. La comunicación entre los microservicios se realiza mediante peticiones RESTful.
Requisitos Previos
•	Java 11 o superior instalado.
•	Docker y Docker Compose instalados.
•	Acceso a Azure SQL o configuración de base de datos compatible.
•	Maven para gestión de dependencias.
•	Cliente REST como Postman para probar los endpoints.
Despliegue de Microservicios:
Pasos para Ejecutar con Docker
1.	Clonar el repositorio:
git clone https://github.com/nancymejiacordova/microservicios-banco.git cd microservicios-banco
2.	Ejecutar el comando: docker-compose build
3.	Construir las imágenes Docker para ambos microservicios: docker-compose build
4.	Levantar los servicios utilizando Docker Compose: docker-compose up
5.	Acceder a los microservicios a través de los siguientes puertos:
Microservicio de Cuentas Bancarias: http://localhost:8081
Microservicio de Transacciones: http://localhost:8082
EndPoints Microservicio1: Gestión de Cuentas Bancarias
Desde Postam ejecutarloscon el detalle siguiente:

1.	EndPoint Login para la generación de Token.
url: http://localhost:8081/auth/login
Metodo: Post
Body 
{
  "nombre": "juan.perez",
  "password": "1234 "
}

2.	Creacion de cuenta bancaria.
url:Post
Método: http://localhost:8081/cuentaBancaria
Body:
{
  "numerocuenta": "123456782",
  "tipocuenta": "Corriente",
  "saldoInicial": 1000,
  "cliente": {
    "idCliente":2,
    "nombre": "Juan Pérez"
  }
}

Pestaña Authorization: seleccionar BearerToken y colocar el token generado con el punto 1.
 
3.	Consulta cuentas por cliente
url:http://localhost:8081/cuentaBancaria/consulta/cuentascliente/2
Método:GET
Body:
{
  "numerocuenta": "123456782",
  "tipocuenta": "Corriente",
  "saldoInicial": 1000,
  "cliente": {
    "idCliente":2,
    "nombre": "Juan Pérez"
  }
}

Pestaña Authorization: seleccionar BearerToken y colocar el token generado con el punto 1.

EndPoints Microservicio2:
1.	Transacciones: Depositos y Retiros.
url: http://localhost:8082/transacciones/transaccion
Método:POST
Body:
{
  "monto": 10000,
  "tipotransaccion": "Retiros",
  "idcuentadestino": {
    "numerocuenta": "1234567890",
    "saldo": 1500.00
  }
}
Pestaña Authorization: seleccionar BearerToken y colocar el token generado con el punto 1.

2.	Consulta historial de cuentas bancarias
url: http://localhost:8082/transacciones/historial/1234567890
Método: GET
http://localhost:8082/transacciones/historial/1234567890

3.	Transferencia entre cuentas
url: http://localhost:8082/transacciones/transferir 
Metodo: POST
Body : {
  "numeroCuentaOrigen": "1234567890",
  "numeroCuentaDestino": "1234567891",
  "monto": 10
}


Consideraciones Adicionales
1.	Base de Datos: En caso de no tener acceso a Azure SQL, es posible configurar una base de datos local y ajustar las credenciales en los archivos de configuración de cada microservicio.
2.	Seguridad: Los roles definidos en el JWT restringen el acceso a los endpoints según los permisos de cada usuario.
