# CarMcQueen - Sistema de Gestión de Vehículos y Ventas

## Descripción
Microservicio desarrollado con Spring Boot para gestionar vehículos, tipos de vehículo, vendedores, clientes y ventas de una concesionaria.

## Estudiantes
- Vicente Villalobos

## Microservicios / Entidades implementadas
| Entidad | Endpoints base |
|---|---|
| TipoVehiculo | `/api/tipos-vehiculo` |
| Vehiculo | `/api/vehiculos` |
| Vendedor | `/api/vendedores` |
| Cliente | `/api/clientes` |
| Venta | `/api/ventas` |

## Rutas principales del Gateway
El Gateway corre en el puerto **8081** y redirige todas las peticiones al microservicio en el puerto **8080**.

| Ruta Gateway | Destino |
|---|---|
| `http://localhost:8081/api/**` | `http://localhost:8080/api/**` |
| `http://localhost:8081/doc/**` | `http://localhost:8080/doc/**` |

## Documentación Swagger
- **Local:** http://localhost:8080/doc/swagger-ui.html
- **Via Gateway:** http://localhost:8081/doc/swagger-ui.html

## Instrucciones de ejecución local (Laragon)
1. Iniciar Laragon y asegurarse de que MySQL esté activo
2. Crear la BD en phpMyAdmin: `CREATE DATABASE carmcqueen_db;`
3. Abrir el proyecto en VSCode
4. Ejecutar: `./mvnw spring-boot:run`
5. Acceder a Swagger: http://localhost:8080/doc/swagger-ui.html

## Instrucciones de ejecución con Docker
```bash
# Construir y levantar todos los servicios
docker-compose up --build

# Verificar que están corriendo
docker-compose ps
```
Servicios disponibles:
- App: http://localhost:8080
- Gateway: http://localhost:8081
- Swagger: http://localhost:8080/doc/swagger-ui.html

## Tecnologías usadas
- Java 17
- Spring Boot 3.4.0
- Spring Cloud Gateway
- JPA + Hibernate
- MySQL
- Lombok
- Swagger / OpenAPI 3
- JUnit 5 + Mockito
