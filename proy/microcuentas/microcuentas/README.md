# Microservicio: microcuentas

Este microservicio gestiona las cuentas bancarias y los movimientos asociados.

---

## Instrucciones de despliegue y ejecución

1. **Requisitos previos:**
   - Java 17 o superior
   - Maven
   - PostgreSQL

2. **Configura la base de datos:**
   - Crea una base de datos en PostgreSQL, por ejemplo: `microcuentasdb`.
   - Modifica el archivo `src/main/resources/application.properties` con tus credenciales y URL de la base de datos.

3. **Crea las tablas ejecutando este SQL:**
```sql
CREATE TABLE cuenta (
    numero_cuenta VARCHAR(20) PRIMARY KEY,
    tipo_cuenta VARCHAR(20),
    saldo_inicial NUMERIC,
    estado VARCHAR(20)
);

CREATE TABLE movimiento (
    id SERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(20),
    fecha DATE,
    tipo_movimiento VARCHAR(20),
    valor NUMERIC,
    saldo NUMERIC,
    CONSTRAINT fk_movimiento_cuenta FOREIGN KEY (numero_cuenta) REFERENCES cuenta(numero_cuenta)
);
```
- La columna `numero_cuenta` en `movimiento` es una clave foránea que referencia a la cuenta correspondiente.

4. **Compila y ejecuta el microservicio:**
```bash
mvn clean install
mvn spring-boot:run
```
   - Por defecto, el microservicio corre en el puerto 8081.

---

## Estructura de entidades y relación (JPA)

- **Cuenta**: entidad principal, numeroCuenta es la PK.
- **Movimiento**: tiene una relación ManyToOne con Cuenta a través de la clave foránea `numeroCuenta`.

Ejemplo de relación en la entidad Movimiento:
```java
@ManyToOne
@JoinColumn(name = "numero_cuenta", referencedColumnName = "numero_cuenta")
private Cuenta cuenta;
```

---

## Endpoints principales

### 1. Listar todas las cuentas
- **GET** `/cuentas`
- **Respuesta:**
```json
[
  {
    "numeroCuenta": "1001",
    "tipoCuenta": "AHORROS",
    "saldoInicial": 500.0,
    "estado": "ACTIVA"
  }
]
```

### 2. Obtener una cuenta por número
- **GET** `/cuentas/{numeroCuenta}`
- **Ejemplo:** `/cuentas/1001`

### 3. Crear una cuenta
- **POST** `/cuentas`
- **Body:**
```json
{
  "numeroCuenta": "1002",
  "tipoCuenta": "CORRIENTE",
  "saldoInicial": 1000.0,
  "estado": "ACTIVA"
}
```
- **Respuesta:** Cuenta creada

### 4. Actualizar una cuenta
- **PUT** `/cuentas/{numeroCuenta}`
- **Body:** (igual que POST)

### 5. Eliminar una cuenta
- **DELETE** `/cuentas/{numeroCuenta}`

---

### 6. Crear un movimiento
- **POST** `/movimientos`
- **Body:**
```json
{
  "fecha": "2024-06-01",
  "tipoMovimiento": "DEPOSITO",
  "valor": 200.0,
  "saldo": 1200.0
}
```
- **Respuesta:** Movimiento creado

### 7. Reporte de movimientos por fecha
- **GET** `/movimientos/reportes?fecha=2024-06-01`
- **Respuesta:**
```json
[
  {
    "id": 1,
    "fecha": "2024-06-01",
    "tipoMovimiento": "DEPOSITO",
    "valor": 200.0,
    "saldo": 1200.0
  }
]
```

---

## Ejemplos de respuestas de error

**Cuenta no encontrada:**
```json
{
  "timestamp": "2024-06-01T12:00:00.000+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "No se encontró la cuenta con el número solicitado",
  "path": "/cuentas/9999"
}
```

**Error de validación:**
```json
{
  "timestamp": "2024-06-01T12:00:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "El campo 'tipoCuenta' es obligatorio",
  "path": "/cuentas"
}
```

---

## Pruebas automáticas

Puedes ejecutar las pruebas unitarias y de integración con:
```bash
mvn test
```

---

## Recomendaciones de seguridad
- No expongas datos sensibles en producción.
- Usa HTTPS en ambientes reales.
- Limita el acceso a los endpoints sensibles.
- Valida y sanitiza todos los datos recibidos.

---

## Troubleshooting
- **Error de conexión a la base de datos:** Verifica las credenciales y que el servicio de PostgreSQL esté activo.
- **Puerto ocupado:** Cambia el puerto en `application.properties`.
- **Problemas de CORS:** Configura los orígenes permitidos en tu aplicación Spring Boot.

---

## Contacto y soporte
Para dudas o soporte, contacta al equipo de desarrollo o abre un issue en el repositorio.

---

## Cómo probar

Puedes usar [Postman](https://www.postman.com/) o `curl`:

**Crear cuenta:**
```bash
curl -X POST http://localhost:8081/cuentas \
  -H "Content-Type: application/json" \
  -d '{
    "numeroCuenta": "1002",
    "tipoCuenta": "CORRIENTE",
    "saldoInicial": 1000.0,
    "estado": "ACTIVA"
  }'
```

**Listar cuentas:**
```bash
curl http://localhost:8081/cuentas
```

**Crear movimiento:**
```bash
curl -X POST http://localhost:8081/movimientos \
  -H "Content-Type: application/json" \
  -d '{
    "fecha": "2024-06-01",
    "tipoMovimiento": "RETIRO",
    "valor": 100.0,
    "saldo": 900.0
  }'
```

**Reporte de movimientos por fecha:**
```bash
curl http://localhost:8081/movimientos/reportes?fecha=2024-06-01
```

---

## Notas
- Cambia el puerto si tu aplicación usa otro.
- Asegúrate de tener la base de datos configurada y corriendo. 