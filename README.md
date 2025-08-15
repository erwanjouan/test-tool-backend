# Backend

## test

http://localhost:8080/tnr/api/tests
http://localhost:8080/tnr/api/executions
http://localhost:8080/tnr/api/tests?priorite=1

## Docker

```shell
mvn clean install -DskipTests
docker build -t backend .
docker run --rm -p 8080:8080 backend
```

## Reference

https://springframework.guru/using-h2-and-oracle-with-spring-boot/

- what is the use of annotations @Id and @GeneratedValue(strategy = GenerationType.IDENTITY)? Why the generationtype is
  identity?
    - https://stackoverflow.com/a/71095911
    - When we specify the generation strategy as GenerationType.IDENTITY
      we are telling the persistence provider(hibernate)
      to let the database handle the auto incrementing of the id.