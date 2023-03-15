## How to run?

The application can be run in development or production mode by applying the following steps.
<br/>

### Prerequisites

The following apps and tools should be installed before running the application:

- A command line app

- Docker Desktop. For more information regarding the system requirements, etc. refer to the following pages:

[Install on Mac](https://docs.docker.com/desktop/install/mac-install/)<br/>
[Install on Windows](https://docs.docker.com/desktop/install/windows-install/)<br/>
[Install on Linux](https://docs.docker.com/desktop/install/linux-install/)<br/>

<br/>



### Running app in Development mode

In order to run the application in development mode, apply the following steps:


1. Run Docker desktop.

<br/>



2. Open command prompt window and clone the project from GitHub using the following command:

```
git clone https://github.com/yildizmy/pet-clinic.git
```
<br/>



3. Change the current directory to the project directory where the `docker-compose.yml` file is in:

```
cd pet-clinic
```
<br/>


4. Run the following command:

```
docker-compose up --build
```

<br/>

By running this command, the database container is built and starts up. 


<br/>


2. Open backend project using IntelliJ IDEA and then select _Run > Edit Configurations_ and add the following values to the Environment variables field. 
3. 


```
DB_NAME=pet_clinic;DB_USERNAME=postgres;DB_PASSWORD=pass;JWT_SECRET=appSecretKey
```

Then run the application.

<br/>

> **Note** If Environment variables field is not available, it can be added via Modify options menu on the _Rub/Debug Configurations_ dialog.

<br/>



3. Open command prompt window. Change the current directory to the frontend project directory and run the frontend application via this command:

```
npm start
```

<br/>


4. Then the application starts on http://localhost:3000/ and will be opened on your default browser. After this step, API requests can be sent to the endpoints.
   For this purpose, see the details on [How to test?](how_to_test.md) section.

<br/>























### Running app in Production mode

In order to run the application in production mode, apply the following steps:





1. Run Docker desktop.

<br/>



2. Open command prompt window and clone the project from GitHub using the following command:

```
git clone https://github.com/yildizmy/pet-clinic.git
```
<br/>



3. Change the current directory to the project directory where the `docker-compose.yml` file is in:

```
cd pet-clinic
```
<br/>


4. Run the following command:

```
docker compose -f docker-compose.yml -f docker-compose.prod.yml --build
```

<br/>

By running this command, the app and database containers are built and start up. After this step is completed, the application will be available on http://localhost:3000/.

<br/>







<br/>

### Troubleshooting

* If there is any process using the same port of with the application (port 3000), _"port is already in use"_ error is
  encountered. In this situation, terminating that process and restarting the related containers will fix the problem. 

<br/>

### Documentation

[docker compose up](https://docs.docker.com/engine/reference/commandline/compose_up/)<br/>


<br/>
<br/>