# Cars

Implement a car client / server application.

## Installation

En utilisant MySQL:

- Installer [docker](https://www.docker.com/get-started)
- Exécuter la dernière image mysql `docker run --name mysql_renault -e MYSQL_ROOT_PASSWORD=12345 -d mysql:latest`
- S'assurer que l'image "mysql_renault" s'exécute avec `docker ps` (copier / coller le port dans application.yml)
- Trouver l'IP du container docker `docker inspect mysql_renault` (copier / coller le port dans application.yml)

En utilisant Postgresql

- Utiliser l'installation de Frank (mettre les bonnes valeurs dans application.yml)

## Démarrer le serveur

Configurer la bdd dans [application.yml](./src/main/resources/application.yml) avec la bdd choisie. Démarrer le serveur Spring Boot ([http://localhost:8008](http://localhost:8008)):

```bash
# Linux & MacOS
./gradlew bootRun

# Windows
gradlew bootRun
```

## TODO

### Step 1 (semaine 3)

For the first step, implement the "TODO step 1" comments.

- In `CarsService`, implement the `getBrands()` method:
    - The method should return the brands from the .csv
    - For example: "BMW", "Cadillac", etc.
- In `CarsServlet`, implement the `goGetHtml()` method:
    - Forward the request to the ".jsp" file
    - Add the set of brands to the request attributes
- In `cars.jsp` page, implement the loop to show the buttons:
    - Use a `for` loop, in the loop, use the `<%= variable %>` syntax
    - The buttons should show as: `<button data-brand="BMW">BMW</button>`

### Step 2 (semaine 3)

For the second step, implement the "TODO step 2" comments.

- In `CarsService`, implement the `getCars()` method:
    - The method should return the cars from the .csv
    - The method should filter on the provided argument
    - Use the `Car` constructor with the brand and model
- In `CarsServlet`, implement the `goGetJson()` method:
    - Use [JSON-P](https://javaee.github.io/jsonp/getting-started.html) to convert to JSON
    - Use `PrintWriter writer = response.getWriter()` to print the output
- In `cars.jsp` page, implement the js to send the request to the server:
    - Use the [`fetch` API](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch)
    - Send a request to the proper URL for example "http://localhost:8080/dubreuia-cars/cars?brand=BMW"
    - Put the content in the `div` element with the "content" id
    
### Step 3 (semaine 4)

For the third step, start a MySql server using Docker, and use Java to insert and query data in the database.

- Start a MySql database
    - Install [docker](https://www.docker.com/get-started)
    - Run latest mysql image `docker run --name mysql_renault -e MYSQL_ROOT_PASSWORD=12345 -d mysql:latest`
    - Run latest mysql image (windows) `docker run --name mysql_renault -e MYSQL_ROOT_PASSWORD=12345 -d -P mysql:latest`
    - Make sure the "mysql_renault" image is running `docker ps` (note the port on windows, it will change)
    - Use inspect to find the IP of the running docker `docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mysql_renault`
    - Try connecting to it using docker `docker run -it --rm mysql mysql -h 172.17.0.2 -u root -p` (password: 12345)
    - Try connecting to it using docker (windows) `winpty docker run -it --rm mysql mysql -h 172.17.0.2 -u root -p` (password: 12345)
    - In Mysql, you need to create a database first, use `CREATE DATABASE renault`
    - Then use the database using `USE renault`
    - (`docker stop mysql_renault`, `docker ps -a`, `docker start mysql_renault`)
    - (`docker inspect mysql_renault` show volume)
    - (`docker rm mysql_renault` loses data!)
    - (`docker image list`, `docker image rm`)
- Use Java to modify and query the database 
    - Add the Mysql connector jar to build.gradle `https://mvnrepository.com/artifact/mysql/mysql-connector-java`
    - In `CarsDatabaseInsert` read the "cars.csv" file and insert the content in the database
    - Use the connection `DriverManager.getConnection("jdbc:mysql://172.17.0.2:3306/renault", "root", "12345");`
    - Modify the `CarService` class to read from the database instead of the "csv" file

### Step 4 (semaine 5)

We'll convert the application to a Spring application. Use the "deployToTomcat" task to deploy to tomcat.

- MySQL
    - Start the MySql database using docker
    - Create the "renault" database and the "cars" table
    - Insert the data in the cars database using `CarsDatabaseInsert`
- Spring
    - Add the spring-boot-starter-data-jpa jar to the build file
    - Create the application.yml file for Spring
    - Convert the `Car` class to an `Entity`
    - Add content to the `CarsRepository` class
    - Modify the `CarsServlet` class to use the new `CarsRepository`

Bonus (CMS):

- Add a "Add new vehicule" button
- Add a "Modify vehicule" button

### Step 5 (semaine 8)

Change the Servlet to a controller, deploy the Spring application to a Tomcat (https://www.baeldung.com/spring-boot-war-tomcat-deploy).

### Step 6 (semaine 9)

Migrate the application to angular (step 1):

- Create a root component for the app
- Create a BrandList component for the brands list
- Create a CarList component for the cars list
- Uses fetch to GET the backend

### Step 7 (semaine 10)

Add DELETE and POST:

- Add a delete button for each car (frontend and backend)
- Add a create form to create a new car (frontend and backend)
- Update components states when a new car (and brand) is created or deleted

### Step 8 (semaine 12)

![Cars class diagram - Step 8](docs/cars_class_diagram_step_08.svg)

Make sure the application is split into the following packages:

- `com.renault.controllers`
- `com.renault.dtos`
- `com.renault.entities`
- `com.renault.repositories`
- `com.renault.services`

A car has a owner (a Owner has a name) and dealerships (a Dealership has an address):

- Add the Owner entity
    - Add the required properties (name)
    - Add its relation to the Car entity
    - Add a form in the UI to add an owner to a car
- Add the Dealership entity
    - Add the required properties (address)
    - Add its relation to the Car entity
    - Add a form in the UI to make a dealership sell a car
