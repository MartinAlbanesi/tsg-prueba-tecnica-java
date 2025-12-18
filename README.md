# TSG Java Spring Boot Challenge

API REST desarrollada con Spring Boot para la gestión de usuarios y posts,
implementando autenticación JWT y control de acceso basado en el usuario autenticado.

---

## Tecnologías utilizadas

- Java 21
- Spring Boot 4
- Spring Security (JWT, OAuth2 Resource Server)
- JPA / Hibernate
- PostgreSQL
- Maven
- Postman (documentación de la API)

---

## Requisitos previos

- Java 21+
- Maven 3.8+
- PostgreSQL
- Git

---

## Configuración del proyecto

1. Clonar el repositorio:

```bash
git clone https://github.com/MartinAlbanesi/tsg-prueba-tecnica-java.git
cd tsg-prueba-tecnica-java
```

2. Configurar la base de datos PostgreSQL:

- Crear una base de datos llamada `tsg_db`.
- Configurar las credenciales de la base de datos y JWT en `src/main/resources/env.properties`
- Ejemplo de configuración:

```env.properties
DB_DATABASE=tsg_db
DB_USER=tu_usuario
DB_PASSWORD=tu_contraseña

JWT_SECRET=tu_clave_secreta_jwt
JWT_EXPIRATION=1800000
JWT_REFRESH_EXPIRATION=3600000
```

3. Construir el proyecto con Maven:

```bash
mvn clean install
```

4. Ejecutar la aplicación:

```bash
mvn spring-boot:run
```

---

## Documentación de la API

La documentación de la API está disponible en Postman. Puedes importarla desde el siguiente enlace:

[Postman API Documentation](https://app.getpostman.com/join-team?invite_code=796180e4b0b9d95c2cf8a919274a42c721a6b8ef6b939abc2f39cece65e83da2&target_code=c99b406e7fead67ebda3439f5883b272)

---

## Notas finales
- Asegúrate de manejar correctamente los tokens JWT en el cliente para acceder a los endpoints protegidos.
- Revisa la documentación de la API en Postman para más detalles sobre los endpoints y sus parámetros.

