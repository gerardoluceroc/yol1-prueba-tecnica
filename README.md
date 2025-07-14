# README del Proyecto Full Stack (YOL1 - Prueba Técnica)

Este proyecto es una aplicación Full Stack desarrollada como prueba técnica para YOL1. Consiste en un backend RESTful con Spring Boot, Maven y Java 17, y un frontend con React y Vite.

---

## Prerrequisitos

Asegúrate de tener instalados los siguientes componentes en tu sistema, especialmente si usas Ubuntu.

### Para el Backend (Spring Boot / Java)

1.  **Java Development Kit (JDK) 17:**
    ```bash
    sudo apt update
    sudo apt install openjdk-17-jdk
    ```
    Verifica la instalación:
    ```bash
    java -version
    ```

2.  **Apache Maven:**
    ```bash
    sudo apt install maven
    ```
    Verifica la instalación:
    ```bash
    mvn -v
    ```

3.  **MySQL Server:**
    ```bash
    sudo apt install mysql-server
    ```
    Sigue las instrucciones en pantalla para configurar una contraseña para el usuario 'root' de MySQL.
    Inicia el servicio MySQL si no está corriendo:
    ```bash
    sudo systemctl start mysql
    sudo systemctl enable mysql
    ```

4.  **Crear la Base de Datos:**
    Conéctate a MySQL como 'root' (o el usuario que hayas configurado):
    ```bash
    sudo mysql -u root -p
    ```
    Introduce tu contraseña de MySQL. Una vez dentro de la consola de MySQL, ejecuta el siguiente comando para crear la base de datos:
    ```sql
    CREATE DATABASE `yol1-prueba-db`;
    EXIT;
    ```

### Para el Frontend (React / Vite)

1.  **Node.js (versión 20.x o superior) y npm:**
    Se recomienda usar NVM (Node Version Manager) para gestionar las versiones de Node.js.

    * **Instalar NVM:**
        Si no tienes NVM, instala la última versión:
        ```bash
        curl -o- [https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.7/install.sh](https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.7/install.sh) | bash
        ```
        Después de la instalación, cierra y vuelve a abrir tu terminal, o ejecuta:
        ```bash
        source ~/.bashrc # O source ~/.zshrc si usas Zsh
        ```
    * **Instalar y usar Node.js 20 LTS:**
        ```bash
        nvm install 20 --lts
        nvm use 20
        nvm alias default 20
        ```
    * Verifica la instalación:
        ```bash
        node -v
        npm -v
        ```

---

## Ejecución del Proyecto

### 1. Clonar el Repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
cd <NOMBRE_DE_TU_CARPETA_CLONADA>

### 2. Configuración y Ejecución del Backend

1.  Navega al directorio del backend:
    ```bash
    cd yol1-backend # O el nombre de tu carpeta backend, como 'yol1'
    ```
2.  Construye y ejecuta la aplicación Spring Boot:
    ```bash
    ./mvnw spring-boot:run
    ```
    La aplicación del backend se iniciará en **http://localhost:8090**.

### 3. Configuración y Ejecución del Frontend

1.  Abre una nueva terminal.
2.  Navega al directorio del frontend:
    ```bash
    cd yol1-frontend
    ```
3.  Instala las dependencias de Node.js:
    ```bash
    npm install
    ```
4.  Inicia el servidor de desarrollo de React/Vite:
    ```bash
    npm run dev
    ```
    La aplicación del frontend se iniciará en **http://localhost:5173**.

---

## Ejemplos de Interacción con el Backend (API REST)

Una vez que el backend esté corriendo, puedes interactuar con él usando herramientas como Postman, Insomnia o 'curl'.

### 1. Registro de Usuario

* **Ruta:** `POST http://localhost:8090/auth/register`
* **Cuerpo de la Petición (JSON):**
    ```json
    {
        "name": "admin",
        "lastname": "yol1",
        "email": "admin@yol1.com",
        "password": "password123"
    }
    ```
* **Ejemplo con 'curl':**
    ```bash
    curl -X POST http://localhost:8090/auth/register \
    -H "Content-Type: application/json" \
    -d '{ "name": "admin", "lastname": "yol1", "email": "admin@yol1.com", "password": "password123" }'
    ```
    Una vez registrado, el backend devolverá un JSON Web Token (JWT) en la respuesta.

### 2. Inicio de Sesión (Login)

* **Ruta:** `POST http://localhost:8090/auth/login`
* **Cuerpo de la Petición (JSON):**
    ```json
    {
        "email": "admin@yol1.com",
        "password": "password123"
    }
    ```
* **Ejemplo con 'curl':**
    ```bash
    curl -X POST http://localhost:8090/auth/login \
    -H "Content-Type: application/json" \
    -d '{ "email": "admin@yol1.com", "password": "password123" }'
    ```
    Si las credenciales son correctas, el backend devolverá un JWT.

---

## Nota Importante sobre el Frontend

El frontend actualmente solo tiene diseñada la funcionalidad de inicio de sesión (login). Lamentablemente, por tiempo, no se alcanzó a desarrollar la funcionalidad completa para ingresar datos desde el formulario de registro. Se puede probar el login una vez que el usuario haya sido registrado directamente a través de la API del backend (como se mostró en los ejemplos de 'curl').