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

## Content

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

### Step 8 (semaine 14)

Les opérations d'ajout d'un véhicule et de suppression de véhicule ne peuvent être effectués que par un utilisateur authentifié et autorisé.

![Cars - Step 09 - Authentification - Form UI](docs/cars-step-09-authentification-ui.png)

Nous allons implémenter cette fonctionnalité en 2 parties : (1) l'authentification avec l'ajout d'un formulaire de login et (2) l'autorisation avec l'ajout de règle de validation par URL et méthode.

#### Step 8 - Ajout de l'authentification

L'authentification consiste à la vérification de l'identité (comprend aussi la création et la sécurisation), voir [https://en.wikipedia.org/wiki/Authentication](https://en.wikipedia.org/wiki/Authentication).

![Cars - Step 09 - Authentification - Sequence Diagram](docs/cars-step-09-authentification-sequence-diagram.svg)

Référence : [https://www.baeldung.com/spring-security-login-angular](https://www.baeldung.com/spring-security-login-angular)

- SpringBoot Security (back) :
    - Ajouter au "build.gradle": `compile "org.springframework.boot:spring-boot-starter-security:2.2.4.RELEASE"`
    - Ajouter à "com.renault.config.BasicAuthConfiguration.configure(AuthenticationManagerBuilder)" un username, password, et roles
    - Créer un "LoginController" sur l'URL "/login" qui reçoit un username et password et qui appelle le "LoginService" pour valider
    - Le "LoginController" doit retourner true/false ou un code HTTP pour le front
- Angular (front) :
    - Dans le service "cars.service.ts", ajouter une méthode "login"
    - La méthode login fait un POST sur "http://localhost:8080/login" avec les identifiants en body
    - Créer un nouveau composant "login.component.ts"
    - Le composant est un formulaire pour entrer "username" et "password"
    - (vous pouvez vous baser sur le "car-form.component.html", ce sera presque identique)
    - Dans "login.component.ts", appeler la méthode "login" du service
    - Si le login est en succès, stocker le login en "session storage" : `sessionStorage.setItem('loggedIn', true)`
    - Créer un nouveau composant "logout.component.ts"
    - Ce composant remet à zéro le stockage et redirige vers la home
    - Dans le composant racine, le bouton doit changer entre login / logout en fonction de l'état du login  

#### Step 8 - Ajout de l'autorisation

L'autorisation est la spécification et application des droits liés à une identité (précédemment authentifiée), voir [https://en.wikipedia.org/wiki/Authorization](https://en.wikipedia.org/wiki/Authorization).

![Cars - Step 09 - Authorization - Sequence Diagram](docs/cars-step-09-authorization-sequence-diagram.svg)

Référence : [https://www.baeldung.com/spring-security-login-angular](https://www.baeldung.com/spring-security-login-angular)

- SpringBoot Security (back) :
    - Ajouter à "com.renault.config.BasicAuthConfiguration.configure(HttpSecurity)" un `antMatchers` pour chaque méthode à autoriser
    - Le `antMatchers` prend une méthode HTTP et une URL, sur lequel on doit appeler `authenticated`
- Angular (front) :
    - Vérifier que les URL authentifiées retournent maintenant des 401 
    - Pour authentifier les opérations, il faut envoyer le Header "Authorization"
    - (le format est "Authorization: Basic TOKEN", ou TOKEN est la concaténation de "username:password", encodé en Base64, avec [`btoa`](https://developer.mozilla.org/en-US/docs/Web/API/WindowOrWorkerGlobalScope/btoa))
    - Dans "login.component.ts", si le login est en succès, concaténer "username:password", puis appeler `btoa` :
        ```javascript
        let base64hash = btoa(username + ':' + password);
        sessionStorage.setItem('token', base64hash);
        ```
    - Le token doit être envoyé avec les requêtes authentifiées

### Step 9 - Enregistrement

Afin d'enregistrer un nouvel utilisateur, nous devons stocker les utilisateurs et roles en base de données. Pour cela, nous avons besoin de 2 nouvelles entités, `User` et `Role`, que vous pouvez reprendre de votre exercice de la semaine passé (voir exercice de Frank : [https://github.com/Frank-readresolve/people](https://github.com/Frank-readresolve/people)). Nous avons aussi besoin de encoder (hasher) le mot de passe de l'utilisateur, et configurer Spring pour qu'il le fasse automatiquement. Finalement, nous allons ajouter l'enregistrement d'un nouvel utilisateur grâce à un formulaire.

Références :

- Utilisation de l'encodage de mod de passe : https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt
- Quelques explication sur le hashing BCrypt et autres : https://www.baeldung.com/java-password-hashing
- Custom authentication provider : https://www.baeldung.com/spring-security-authentication-provider
- Custom user details service : https://www.baeldung.com/spring-security-authentication-with-a-database
 
Backend :

- `User` et `Role`
    - Implémenter les 2 entités (reprendre le code de la semaine passé)
    - Un `User` a un username, un password, et peut avoir plusieurs `Role`
    - Un `Role` a un nom
- `ApplicationService`
    - À l'init de l'application, ajouter 2 roles : "USER" et "ADMIN"
    - Ajouter un user "admin" qui a le role "ADMIN"
- `BasicAuthConfiguration`
    - Configurer le authentication provider en lui ajoutant un `UserDetailsService`
    - Configurer le authentication provider en lui ajoutant le `BCryptPasswordEncoder`
    - Configurer l'autorisation pour que le suppression de voiture ne soit possible que pour un role "ADMIN"
- `UserDetailsService`
    - Créer un nouveau service pour le authentication provider qui étend `org.springframework.security.core.userdetails.UserDetailsService`
    - Implémenter la méthode `loadUserByUsername` qui doit retourner charger un `User` et retourner un `UserDetails` (vous pouvez utiliser la sous-classe `org.springframework.security.core.userdetails.User`)
    - Dans la méthode `loadUserByUsername` vous allez devoir instancier des GrantedAuthority pour chaque role (vous pouvez utiliser la sous-classe `org.springframework.security.core.authority.SimpleGrantedAuthority`)
- `UserService`
    - Dans la méthode `verifyUser`, vous devez utiliser le password encoder pour valider l'utilisateur
    - (si le mot de passe n'est pas bon, vous pouvez renvoyer un `org.springframework.security.authentication.BadCredentialsException`)
    - Créer une méthode `registerUser`, qui servira à créer un nouvel utilisateur
    
Frontend :

- Créer un nouveau component "register" qui appelle le backend pour créer un nouveau user
- (ce component ressemblera beaucoup au component "login")

Bonus :

- Récupérer l'utilisateur authentifié : https://www.baeldung.com/get-user-in-spring-security
- Expressions de sécurités pour roles : https://www.baeldung.com/spring-security-expressions-basic
- Pour la configuration de l'OAuth2 : https://www.baeldung.com/spring-security-5-oauth2-login
- Spring security user / role schema (pas nécessaire de faire pareil) : https://docs.spring.io/spring-security/site/docs/current/reference/html5/#user-schema
- Spring JDBC authentification : https://www.baeldung.com/spring-security-jdbc-authentication

### Step 10 - Analyse fonctionnelle

Avant de commencer, nous allons créer une nouvelle application à partir de spring initializr et créer l'arborescence nécessaire au démarrage du projet (le corrigé se fera sur l'application "Cars", mais vous pouvez le faire pour votre projet).

- Créer un nouveau dépôt sur github
- Création du serveur
    - Aller sur [spring initializr](https://start.spring.io/)
    - Choisir Groovy / Maven
    - Choisir Java 11 (voir [https://en.wikipedia.org/wiki/Java_version_history](https://en.wikipedia.org/wiki/Java_version_history))
    - Choisir Spring Boot 2.2.6
    - Remplir project metadata ("cars")
    - Ajouter les dépendances
        - Spring Boot DevTools
        - Spring Web
        - Spring Security
        - Spring JDBC API
        - Spring Data JPA
        - MySQL Driver / PostgreSQL Driver
    - Télécharger le zip et unzip à la racine de votre projet
    - Renommer le dossier "cars" en "server"
- Création du client
    - Exécuter `ng new cars` à la racine de votre projet
    - Renommer le dossier "cars" en "client"

Nous allons procéder à l'analyse fonctionnelle pour l'application "Cars" (vous pouvez utiliser votre projet comme exemple). [Introduction sur les diagrammes UML](https://en.wikipedia.org/wiki/Unified_Modeling_Language#Diagrams) à lire. 

- [UML - Use case diagram](https://en.wikipedia.org/wiki/Use_case_diagram)
    - Commencer sur papier à réfléchir aux différents cas d'usage
    - Version électronique
        - [https://app.diagrams.net](https://app.diagrams.net) (utiliser le format "blank")
        - [PlantUML - Use Case Diagram](https://plantuml.com/use-case-diagram)
    - Pour l'application "Cars", modéliser les différentes actions possibles :
        - Un visiteur peut voir la liste de voitures
        - Un administrateur peut supprimer une voiture
        - etc.
    - Déterminer les roles à partir du diagramme
- Maquettage
    - Commencer sur papier à réfléchir aux différents écran
    - Penser en terme de "boites" (tag html, composant angular)
    - Version électronique [https://pencil.evolus.vn/](https://pencil.evolus.vn/)

### Step 11 - Analyse technique

L'analyse technique défini les concepts techniques avant de les implémenter.

- [UML - Class diagram](https://en.wikipedia.org/wiki/Class_diagram)
    - Définir les concepts de votre application sur papier
    - Version électronique
        - [https://app.diagrams.net](https://app.diagrams.net) (utiliser le format "Class Diagram")
        - [PlantUML - Class Diagram](https://plantuml.com/class-diagram)
- [UML - Entity–relationship model](https://en.wikipedia.org/wiki/Entity%E2%80%93relationship_model)
    - Représente les concepts de votre application du point de vu de la base de données
    - (tip : à partir du SQL généré par Spring, vous pouvez utiliser [MySQL Workbench](https://dev.mysql.com/downloads/workbench/) (utiliser "Files > Import" pour importer un script SQL)
    - Version électronique
        - [https://app.diagrams.net](https://app.diagrams.net) (utiliser le format "Entity Relationship Diagram")
        - [PlantUML - Entity Relationship Diagram](https://plantuml.com/ie-diagram)

Bonus :

- [UML - Component diagram](https://en.wikipedia.org/wiki/Component_diagram)
    - Exemple plus simple : [https://www.uml-diagrams.org/package-diagrams-examples.html](https://www.uml-diagrams.org/package-diagrams-examples.html)
    - Aussi architecture multi-tiers : [https://www.uml-diagrams.org/multi-layered-web-architecture-uml-package-diagram-example.html](https://www.uml-diagrams.org/multi-layered-web-architecture-uml-package-diagram-example.html)
    - Définir les composants de votre application sur papier : un client, un serveur, une bdd, des API externes ?
    - Version électronique
        - [https://app.diagrams.net](https://app.diagrams.net) (utiliser le format "blank")
        - [PlantUML - Component diagram](https://plantuml.com/component-diagram)
- Documentation technique :
    - serveur : Exécuter `./gradlew javadoc` pour générer la document des classes Java
    - client : Utiliser [compodoc](https://compodoc.github.io/compodoc/)

Alternatives :

- [StarUML 3](http://staruml.io/)
