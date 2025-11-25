# 🧬 API Mutantes – Examen MercadoLibre  
**Alumno:** Luciano Córdoba  
**Legajo:** 50854  
**Carrera:** Ingeniería en Sistemas de Información  
**Universidad:** UTN – Facultad Regional Mendoza  
**Materia:** Desarrollo de Software  
**Año:** 2025  

---

# 📌 Descripción del Proyecto

Este proyecto implementa la API solicitada en el Examen MercadoLibre:  
determinar si una secuencia ADN pertenece a un **mutante** o un **humano**,  
almacenar los resultados, exponer estadísticas y documentar la solución.

El sistema cumple **todos los requisitos de la consigna**, incluyendo:  
✔ Validaciones estrictas del ADN  
✔ Algoritmo de detección mutante  
✔ Persistencia en base de datos  
✔ Endpoint de estadísticas  
✔ Documentación Swagger  
✔ Dockerfile funcional  
✔ Tests unitarios + Jacoco (+80% requerido, logrado +90%)  
✔ Despliegue en Render  

---

# 🚀 Tecnologías principales

- **Java 21**
- **Spring Boot 3.5**
- **Gradle**
- **Spring Web**
- **Spring Data JPA**
- **H2 Database**
- **Spring Validation**
- **Springdoc OpenAPI (Swagger)**
- **JUnit 5**
- **Mockito**
- **Jacoco**
- **Docker**
- **Render**

---

# 🗂️ Estructura del Proyecto

```

mutantes/
│
├── src/main/java/com/global/ds_mutantes/
│   ├── config/
│   │   └── SwaggerConfig.java
│   ├── controller/
│   │   ├── MutantController.java
│   │   └── HomeController.java
│   ├── dto/
│   │   ├── DnaRequest.java
│   │   ├── StatsResponse.java
│   │   └── ErrorResponse.java
│   ├── entity/
│   │   └── DnaRecord.java
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java
│   │   └── DnaHashCalculationException.java
│   ├── repository/
│   │   └── DnaRecordRepository.java
│   ├── service/
│   │   ├── MutantDetector.java
│   │   ├── MutantService.java
│   │   └── StatsService.java
│   ├── validation/
│   │   ├── ValidDnaSequence.java
│   │   └── ValidDnaSequenceValidator.java
│   └── MutantDetectorApplication.java
│
├── src/test/java/com/global/ds_mutantes/
│   ├── controller/MutantControllerTest.java
│   └── service/
│       ├── MutantDetectorTest.java
│       ├── MutantServiceTest.java
│       └── StatsServiceTest.java
│
├── build.gradle
├── settings.gradle
├── Dockerfile
└── README.md

````

---

# ⚙️ Ejecución del Proyecto (Local)

### 1️⃣ Clonar el repositorio
```bash
git clone https://github.com/Lucho-IDN/mutantes-global-ds-meli.git
cd mutantes-global-ds-meli
````

### 2️⃣ Ejecutar con Gradle

```bash
./gradlew bootRun
```

La API se levanta en:
👉 [http://localhost:8080](http://localhost:8080)

---

# 🐳 Ejecución con Docker

### 1️⃣ Construir la imagen

```bash
docker build -t mutantes-api .
```

### 2️⃣ Ejecutar el contenedor

```bash
docker run -p 8080:8080 mutantes-api
```

---

# 🌐 Despliegue en Render

La API está deployada en:
👉 **[https://mutantes-global-ds-meli.onrender.com](https://mutantes-global-ds-meli.onrender.com)**

Swagger:
👉 **[https://mutantes-global-ds-meli.onrender.com/swagger-ui.html](https://mutantes-global-ds-meli.onrender.com/swagger-ui.html)**

Stats:
👉 **[https://mutantes-global-ds-meli.onrender.com/stats](https://mutantes-global-ds-meli.onrender.com/stats)**

Root redirect:
👉 **[https://mutantes-global-ds-meli.onrender.com/](https://mutantes-global-ds-meli.onrender.com/)**

---

# 📄 Endpoints

## 🔹 POST `/mutant`

Determina si el ADN es mutante.

**Request**

```json
{
  "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
```

**Responses**

* `200 OK` → Mutante
* `403 Forbidden` → Humano
* `400 Bad Request` → ADN inválido

---

## 🔹 GET `/stats`

Devuelve estadísticas del sistema.

**Response**

```json
{
  "countMutantDna": 40,
  "countHumanDna": 100,
  "ratio": 0.4
}
```

---

# 🧪 Tests – JUnit + Mockito

El proyecto incluye **35 tests**:

* 16 tests del detector
* 5 tests del servicio mutante
* 6 tests del servicio de estadísticas
* 8 tests del controller

### 🔸 Ejecutar tests

```bash
./gradlew test
```

---

# 📊 Jacoco – Cobertura

### Generar reporte:

```bash
./gradlew test jacocoTestReport
```

Reporte disponible en:

```
build/jacocoHtml/index.html
```

<img width="1440" height="294" alt="Captura de pantalla 2025-11-25 a la(s) 15 24 22" src="https://github.com/user-attachments/assets/4be260cc-06a3-41e3-9f64-0aedfceea262" />

---

# 📈 Diagrama de Secuencia (Mermaid)

<img width="4198" height="3794" alt="Untitled diagram-2025-11-25-181024" src="https://github.com/user-attachments/assets/13824fb6-eab7-4824-97d5-bf640816d32f" />

---

# 🧬 Algoritmo de detección mutante (resumen técnico)

La detección evalúa secuencias de **4 caracteres iguales** en:

* Horizontal
* Vertical
* Diagonal ↓
* Diagonal ↑

Si encuentra **dos o más**, el ADN es mutante.

---

# 🛡️ Validaciones del ADN

* La matriz debe ser **NxN**
* Solo caracteres **A, T, C, G**
* No se aceptan filas nulas o vacías
* Se debe enviar un array JSON válido

Las validaciones custom están implementadas con `@ValidDnaSequence`.

---

# 📂 Base de datos – H2 (Memoria)

Consola habilitada en:

```
/h2-console
```

Configuración:

* JDBC URL: `jdbc:h2:mem:testdb`
* User: `sa`
* Pass: *(vacío)*

---

# 🧾 Rúbrica cumplida

| Requisito            | Estado   |
| -------------------- | -------- |
| Detección mutante    | ✔        |
| Validaciones ADN     | ✔        |
| Persistencia         | ✔        |
| /stats               | ✔        |
| Swagger              | ✔        |
| Tests > 80%          | ✔ (~92%) |
| Dockerfile funcional | ✔        |
| Deploy en Render     | ✔        |
| README profesional   | ✔        |
| DS completo          | ✔        |

---

# 📝 Conclusión

Este trabajo implementa una solución completa, modular y profesional de la API Mutantes solicitada en el examen, cumpliendo cada punto de la consigna y aplicando buenas prácticas de programación, testing y despliegue continuo.

---

# ✨ Autor

**Luciano Córdoba (Legajo 50854)**
UTN – Facultad Regional Mendoza
Ingeniería en Sistemas de Información
Desarrollo de Software
