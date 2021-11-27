# Dungeons and Dragons character creation

### Before starting

Before starting the app move to `fronted` directory
```bash
cd src/main/frontend
```
and create a `.env` with the following line:
```
REACT_APP_API_URL="http://localhost:8080/api"
```

### Run separate servers
To start the backend run:
```bash
mvn spring-boot:run
```

To start the React app
```bash
cd src/main/frontend
yarn install
yarn start
```

The app is available at `http://localhost:3000`

### Serve frontend from the same server

To serve the React app from the Spring Boot server first build the server:
```bash
mvn clean install
```

Then execute the jar from `target` directory:
```bash
cd target
java -jar DungeonsDragons-0.0.1-SNAPSHOT.jar
```

The app is available at `http://localhost:8080`

