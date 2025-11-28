# gestor-peliculas-psp-rls

Clonar repositorio:
https://github.com/rllisom/gestor-peliculas-psp-rls.git

⸻

Descripción
API para crear, editar, obtener y eliminar películas, directores y actores.
⸻

El sistema gestiona tres entidades principales:

1. Director (Entidad Principal)
	•	id (Long, PK)
	•	nombre (String)
	•	anioNacimiento (Integer)

2. Pelicula (Entidad Secundaria)
	•	id (Long, PK)
	•	titulo (String, único)
	•	genero (String)
	•	fechaEstreno (LocalDate)


3. Actor (Entidad Secundaria)
	•	id (Long, PK)
	•	nombre (String)
	•	Asociación: Muchos a Muchos con Película
	•	Una película tiene varios actores
	•	Un actor puede participar en varias películas

    Relaciones
  - Pelicula -> Director (ManyToOne / OneToMany)
  - Pelicula <-> Actor (ManyToMany)

	
⸻

Funcionalidades Principales

CRUD de Directores
	•	Crear director
	•	Listar directores
	•	Buscar por id
	•	Editar
	•	Eliminar

CRUD de Películas
	•	CRUD completo
	•	El POST de Película requiere un director existente

CRUD de Actores
	•	Crear actor
	•	Listar actores

Gestión del Reparto (Relación M:M)
	•	Asignar un actor a una película:


⸻

Gestión de Errores

El proyecto implementa excepciones personalizadas, mapeadas a ProblemDetail, manejadas dentro de la clase GlobalHandlerException:

EntidadNoEnonctradException -> 404
  - PeliculaNoEncontradaExcepcion
  - DirectorNoEncontradoExcepcion
  - ActorNoEncontradoExcepcion
PeliculaYaExisteException -> 409
ActorYaEnRepartoExcepcion -> 409
DirectorMenorEdadException -> 400
IllegalArgumentException -> 400


⸻

Diseño y DTOs

Se utilizan DTOs anidados:
  - PeliculaResponseDTO -> Muestra el valor de cada atributo
  - PeliculaRequestDTO -> Cuerpo necesario e indispensable para la creación/edición de películas
  - PeliculaSimpleResponseDTO -> Muestra id y nombre de la película 
  - DirectorResponseDTO -> Muestra el valor de cada atributo
  - DirectorRequestDTO -> Cuerpo necesario e indispensable para la creación/edición de directores
  - DirectorSimpleResponseDTO -> Muestra id y nombre de la película 
  - ActorResponseDTO -> Muestra el valor de cada atributo
  - ActorRequestDTO  -> Cuerpo necesario e indispensable para la creación/edición de actores
  - ActorSimpleResponseDTO -> Muestra id y nombre de la película 

⸻
Arquitectura del Proyecto

src/main/java
 ├── controller
 ├── dto
 ├── error
 ├── model
 ├── repository
 └── service

Servicios – Funcionamiento

 PeliculaService
  - Crud basiscos con manejo de errores incluidos
  - Validaciones para evitar duplicados
  - Método para agregar actores


ActorService
	- Crear y listar actores
	- Manejo de excepciones al crear actores
  
DirectorService
	- CRUD completo
	- Valida la edad mínima en un método que se encuentra en la clase entidad como helper
    - No se puede eliminar un director si tiene asociado una película

⸻

Endpoints principales

Directores

GET  /api/v1/director  Listar directores
GET  /api/v1/director/{id}  Obtener por ID
POST  /api/v1/director  Crear director
PUT  /api/v1/director/{id}  Editar director
DELETE  /api/v1/director/{id}  Eliminar director

Actores

GET  /api/v1/actores  Listar actores
GET  /api/v1/actores/{id}  Listar actores
POST  /api/v1/actores  Crear actor

Películas

GET  /api/v1/peliculas  Listar películas
GET  /api/v1/peliculas/{id}  Obtener película con reparto
POST  /api/v1/peliculas  Crear película
POST  /api/v1/peliculas/{peliculaId}/actores/{actorId}  Asignar actor a película
PUT  /api/v1/peliculas/{id}  Editar película
DELETE  /api/v1/peliculas/{id}  Eliminar película


