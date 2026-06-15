Amisolumi: Ecommerce de venta de productos tejidos a crochet como amigurumis y flores.
#Link Video: https://youtu.be/Epgu0G8dv5k

Endpoints:

#Registro
POST http://localhost:8080/api/v1/auth/register
Request:
{
  "email": "marisolpr@gmail.com",
  "password": "123456",
  "celular": "999999989",
  "direccion": "Av Ciruelos 100",
  "dni": "72924244",
  "username": "marisolpr",
  "fechaNacimiento": "2000-08-15",
  "rol": "ADMIN"
}

#Login:
POST http://localhost:8080/api/v1/auth/login
Request:
{
  "username": "marisolpr",
  "password": "123456"
}

#Buscar usuario por id:
GET http://localhost:8080/api/v1/users/adbac424-112c-4158-a46f-2a7ec5715f93
Response:
{
  "success": true,
  "message": "Usuario encontrado",
  "data": {
    "id": "adbac424-112c-4158-a46f-2a7ec5715f93",
    "nombres": "MARISOL KARINA",
    "apellidos": "PACHAURI RIVERA",
    "email": "marisolpr@gmail.com",
    "celular": "999999988",
    "direccion": "Av Ciruelos 200",
    "dni": "72924244",
    "username": "marisolpr",
    "fechaNacimiento": "2000-08-15T00:00:00.000+00:00",
    "rol": "ADMIN",
    "createdAt": "2026-05-17T05:45:14.092+00:00",
    "updatedAt": "2026-05-17T05:56:54.955+00:00"
  }
}

#
