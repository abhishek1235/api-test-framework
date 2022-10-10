# Restful-booker API Test Automation

Restful-booker API Test Automation is a Test Framework to test [Restful-booker API](https://restful-booker.herokuapp.com/). Restful-booker is a free Create Read Update Delete Web API that comes with authentication
features.

## Application 
[Restful-booker API](https://restful-booker.herokuapp.com/)
Detailed API documentation is available [here](https://restful-booker.herokuapp.com/apidoc/index.html)

### Supported Operations 
 * CreateBooking
 * GetBooking
 * UpdateBooking
 * DeleteBooking


## Run Locally

Clone the project

```bash
  git clone https://abhishek1235@bitbucket.org/abhishek1235/api-test-framework.git
```

Go to the project directory

```bash
  cd api-test-framework
```

Install dependencies and Run Tests

```bash
  mvn clean install
```

Run and Generate Html Reports

```bash
  mvn site
  cd api-test-framework/target/site
```

Open *surefire-report.html* for Test reports

Open *index.html* for Project Information

## Languages and Frameworks

* Java 11 as the programming language.
* REST Assured as the REST API test automation framework.
* Lombok to generate getters, setters and builders.
* JUnit 5 as the testing framework.
* Hamcrest as the matcher library.
* Datafaker as the fake data generation library.
* Maven as the Java build tool.
* IntelliJ IDEA as the IDE.


## Author

- [@abhishek1235](https://bitbucket.org/abhishek1235/)


