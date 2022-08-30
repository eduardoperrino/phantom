
# Phantom Server
> MultiChannel Notification Server

## Usage

### Build

`$ ./mvnw clean install`

### Run

`$ ./mvnw spring-boot:run -pl app`

[A postman collection is provided](docs/phantom.postman_collection.json) to check all the available endpoints

### Test

To execute `End-to-end Tests`, while running:

`$ ./mvnw test -pl app -Dtest=KarateRunner [-DargLine="-Dapp.server.baseUrl=http://localhost:8080"]`

