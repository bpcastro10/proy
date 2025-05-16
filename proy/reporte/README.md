
# Proyecto Reporte - Spring Boot API

Este proyecto es una API REST construida con Spring Boot 3.2.3, Java 21 y Maven. El servicio está preparado para levantar en el puerto `8083` y se conecta a una base de datos PostgreSQL.

---

## ⚙️ Requisitos previos

- Java 21
- Maven 3.9+
- PostgreSQL instalado localmente o en contenedor Docker

---

## 🛠️ Crear la base de datos

Puedes crear la base de datos usando la consola de PostgreSQL o mediante Docker:

### Opción 1: Manualmente en PostgreSQL local

```sql
CREATE DATABASE reportedb;
CREATE USER reporteuser WITH ENCRYPTED PASSWORD 'reporte123';
GRANT ALL PRIVILEGES ON DATABASE reportedb TO reporteuser;
```

### Opción 2: Usando Docker

```bash
docker run --name reportepg -e POSTGRES_DB=reportedb -e POSTGRES_USER=reporteuser -e POSTGRES_PASSWORD=reporte123 -p 5432:5432 -d postgres:15
```

---

## ⚙️ Configuración de conexión en `src/main/resources/application.properties`

```properties
server.port=8083

spring.datasource.url=jdbc:postgresql://localhost:5432/reportedb
spring.datasource.username=reporteuser
spring.datasource.password=reporte123
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

---

## 🚀 Cómo levantar el proyecto

Desde la raíz del proyecto, ejecuta:

```bash
./mvnw clean spring-boot:run
```

Esto iniciará el backend en `http://localhost:8083`.

---

## 📡 Endpoints disponibles y ejemplos Postman

### 🔹 Obtener todos los reportes

**GET** `http://localhost:8083/api/reportes`

Ejemplo en Postman:
```
GET /api/reportes
```

---

### 🔹 Obtener un reporte por ID

**GET** `http://localhost:8083/api/reportes/{id}`

Ejemplo:
```
GET /api/reportes/1
```

---

### 🔹 Crear un nuevo reporte

**POST** `http://localhost:8083/api/reportes`

Header:
```
Content-Type: application/json
```

Body:
```json
{
  "titulo": "Reporte de ventas",
  "descripcion": "Ventas Q1",
  "autor": "Bryan Castro"
}
```

---

### 🔹 Actualizar un reporte

**PUT** `http://localhost:8083/api/reportes/{id}`

Body:
```json
{
  "titulo": "Reporte actualizado",
  "descripcion": "Ventas Q1 actualizado",
  "autor": "Bryan Castro"
}
```

---

### 🔹 Eliminar un reporte

**DELETE** `http://localhost:8083/api/reportes/{id}`

Ejemplo:
```
DELETE /api/reportes/1
```

---

## 🧪 Pruebas

Puedes probar los endpoints usando:

- [Postman](https://www.postman.com/)
- [Insomnia](https://insomnia.rest/)
- `curl` desde terminal

---

## 📦 Dependencias clave

- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- PostgreSQL Driver

---

## 📌 Notas

- Si usas perfiles como `dev` o `prod`, asegúrate de activarlos con:  
  `--spring.profiles.active=dev`
- En caso de errores, puedes ejecutar con `--debug` para más detalles.
