# Tablero Colaborativo en Tiempo Real

Este proyecto es una aplicación web que permite a múltiples usuarios dibujar simultáneamente en un tablero compartido, visualizando en tiempo real los trazos de los demás. Está construido con **Java Spring Boot** en el backend y **P5.js** junto con **SockJS** y **STOMP.js** en el frontend.

---

## Tabla de Contenidos
- [Arquitectura General](#arquitectura-general)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Instalación y Ejecución](#instalación-y-ejecución)
- [Funcionamiento Técnico](#funcionamiento-técnico)
  - [Backend](#backend)
  - [Frontend](#frontend)
  - [Flujo de Mensajes](#flujo-de-mensajes)
- [Personalización](#personalización)
- [Licencia](#licencia)

---

## Arquitectura General

- **Frontend:** HTML + P5.js para el canvas, SockJS y STOMP.js para la comunicación en tiempo real.
- **Backend:** Spring Boot expone endpoints WebSocket y gestiona el broadcast de eventos.
- **Comunicación:** WebSocket (STOMP) para mensajes bidireccionales y broadcast.

---

## Tecnologías Utilizadas
- Java 17+ (Spring Boot)
- Spring Boot Starter WebSocket
- WebJars: sockjs-client, stomp-websocket
- P5.js (CDN)
- SockJS y STOMP.js (WebJars)

---

## Estructura del Proyecto

```
tablero/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── arsw/tarea/tablero/
│   │   │       ├── config/
│   │   │       │   └── WebSocketConfig.java
│   │   │       └── controller/
│   │   │           ├── TableroController.java
│   │   │           └── DrawEvent.java
│   │   └── resources/
│   │       └── static/
│   │           ├── index.html
│   │           └── js/
│   │               └── sketch.js
├── pom.xml
```

---

## Instalación y Ejecución

1. **Clona el repositorio:**
   ```bash
   git clone <url-del-repositorio>
   cd tablero
   ```
2. **Compila el proyecto:**
   ```bash
   ./mvnw clean package
   ```
3. **Ejecuta la aplicación:**
   ```bash
   ./mvnw spring-boot:run
   ```
4. **Abre la aplicación en el navegador:**
   - [http://localhost:8080/drawio.html](http://localhost:8080/drawio.html)

---

## Funcionamiento Técnico

### Backend

- **WebSocketConfig.java**
  - Configura el endpoint `/tablero-websocket` para conexiones WebSocket.
  - Define `/app` como prefijo para mensajes del cliente al servidor.
  - Define `/topic` como prefijo para mensajes broadcast del servidor a los clientes.

- **TableroController.java**
  - `@MessageMapping("/draw")` y `@SendTo("/topic/draw")`: Recibe eventos de dibujo y los reenvía a todos los clientes.
  - `@MessageMapping("/clear")` y `@SendTo("/topic/clear")`: Recibe el evento de borrar y lo reenvía a todos los clientes.

- **DrawEvent.java**
  - POJO que representa un evento de dibujo (coordenadas y color).

### Frontend

- **index.html**
  - Incluye P5.js, SockJS, STOMP.js y el script de la aplicación.
  - Contiene el botón para borrar el tablero.

- **sketch.js**
  - Crea el canvas y gestiona el dibujo local.
  - Conecta al backend usando SockJS y STOMP.
  - Envía eventos de dibujo a `/app/draw`.
  - Escucha eventos de dibujo en `/topic/draw` y de borrado en `/topic/clear`.

#### Ejemplo de flujo de mensajes

1. **Dibujo:**
   - El usuario dibuja → JS envía un objeto JSON a `/app/draw`.
   - Backend recibe el objeto, lo reenvía a `/topic/draw`.
   - Todos los clientes reciben el evento y dibujan el punto.

2. **Borrado:**
   - El usuario pulsa el botón de borrar → JS envía a `/app/clear`.
   - Backend reenvía a `/topic/clear`.
   - Todos los clientes limpian el canvas.

#### Ejemplo de mensaje JSON

```json
{
  "x": 120.5,
  "y": 200.0,
  "color": [255, 0, 0]
}
```

---

## Archivos y Ejercicios Incluidos en el Scaffolding

Este proyecto incluye, además del tablero colaborativo, archivos y componentes que forman parte de ejercicios de aprendizaje y pruebas del curso. Todos estos archivos son parte intencional del scaffolding y pueden ser útiles para referencia, pruebas o ampliación del proyecto.

- **WebSiteController.java:** Controlador REST de ejemplo para manejo de sesiones y endpoints de prueba (`/status`, `/setname`).
- **FirstComponent.jsx:** Componente de ejemplo en React/JSX para prácticas de integración de frontend moderno.
- **Carpeta de pruebas:** Estructura en `src/test/java/arsw/tarea/tablero/` para agregar pruebas unitarias o de integración.



---

