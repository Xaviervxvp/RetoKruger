# Krugger Challenge

## Introducción

La solución plantiada fue una arquitectura de microservicios.

# Estandares de código
- La códificación de los microservicios se realizo siguiendo los principios **SOLID**

## Seguridades

- Solo API de login es pública, el resto requiere de un JWT. Los microservicios ademas de verificar el jwt, comprueban que el ROL pueda consumir la API
- Las contraseñas se encuentran encriptadas con bcrypt
- El proveedor de correos es mailgum, los mismos son enviados mediante SMTP. **Nota: al ser una cuenta gratuita, mailgum restringe el envío solo a correo authorizados**
- En el API Gateway se puede configurar origenes, headers y métodos permitidos.

## Comunicación entre microservicios
La comunicación entre microservicios se hizo mediante API REST, de la siguiente forma:
- WebFlux para el consumo de API
- Generación de JWT internos con el ROl = MICROSERVICIO

## Validación de Campos
- La validación de los objetos que se recibe se la realizo con **javax.validation**
- En ciertos casos especificos como la validación de la cédula y la fecha de cumpleaños se creo una **@annotacion** customizada.

## Documentación de las APIs
La documentación de la realizo mediante **swagger-ui v3.0.0** siguiendo las especificaciones de OpenAPI 

## Notificaciones
- Proveedor de correos: mailgum
- Protocolo: SMTP
- Dependencia de Java: **spring-boot-starter-mail**
- Se recomienda usar una base de datos en cache como redis o h2, puesto que las credenciales del correo se encuentran en una base de datos