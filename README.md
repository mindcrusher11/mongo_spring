* [Creating a REST API with Spring Boot and MongoDB]()

I have implemented apis based on requirement given ,I have used Java,Spring boot,Maven,Mongodb to implement rest service.

http://localhost:8080/api/birds GET request if records are there then it willl return data bd status 200 OK if not then NOT found exception.

http://localhost:8080/api/birds/{id} GET request will retrieve matching record ,if does not exist then NOT found exception.

http://localhost:8080/api/birds/{id} Delete request will delet matching record ,if does not exist then NOT found exception.

http://localhost:8080/api/birds POST request will save data into mongodb.

{"id":"1","name":"testname","family":"testfamily","continents":["abc","cde","abc"]} data as input i gave.



You can run the example application by using the following command:

    mvn clean spring-boot:run
