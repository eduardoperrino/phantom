# Welcome to Phantom!

Phantom is a small side project used to experiment with different technologies and design patterns to resolve a challenge.

## The Challenge

During the pandemic, my community of neighbours decided to have all our meetings remote-friendly. 
Now more people joined them, but a communication issue arose because it's challenging to be aware of the 
decisions. After some discussions, we agreed to be informed in real-time but also to have a way to access 
decisions later.

## Proposed solution

The system will provide a web application where any neighbour can connect and see the decisions in real-time. 
Also, it will provide a subscription service to get them in their inbox email, allowing later access.

## Run Phantom

### Start phantom server

```
$ cd phantom server
$ ./mvnw spring-boot:run -pl app
```

### Start phantom frontend
```
$ cd phantom frontend
$ npm install
$ npm start
```

### Send decisions
```
$ curl --location --request POST 'http://localhost:8080/circular/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Post microchip",
    "description": "Post Try to override the ADP pixel, maybe it will compress the neural matrix!"
}'
```



