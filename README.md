# quotations

To run this app simply run: `./mvnw spring-boot:run` in application folder. 

### Endpoints:
##### GET 
Getting all current quotations: `http://localhost:8080/quote`

Curl example: `curl --location --request GET 'http://localhost:8080/quote' --data-raw ''`


##### GET 
Getting specific, single quotation: `http://localhost:8080/quote/{id}`

Curl example: `curl --location --request GET 'http://localhost:8080/quote/3'`
  

##### POST
Adding a quote: `http://localhost:8080/quote`

Curl example: 
```
curl --location --request POST 'http://localhost:8080/quote' \
--header 'Content-Type: application/json' \
--data-raw '{
    "quote": "text quoty",
    "authorName": "Janek",
    "authorSurname": "Kowalczykiewski"
}'
```


##### DELETE 
Remove existing quote: `http://localhost:8080/quote/{id}`

Curl example:
```
curl --location --request DELETE 'http://localhost:8080/quote/2'
```


##### PUT
Update existing quote: `http://localhost:8080/quote/{id}`

Curl example: 
```
curl --location --request PUT 'http://localhost:8080/quote/3' \
--header 'Content-Type: application/json' \
--data-raw '{
    "quote": "Jakis cytat",
    "authorName": "Adam",
    "authorSurname": "Nowak"
}'
```
