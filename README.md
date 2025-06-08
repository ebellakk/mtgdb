# MTGDB - Magic: The Gathering Deck Builder

### About
(This project is still a work-in-progress)

A simple API for building and saving [Magic: The Gathering](https://magic.wizards.com/) decks, using [Spring Boot](https://spring.io/projects/spring-boot) and the [Scryfall API](https://scryfall.com/docs/api).

Target java version is Java 21.

For now, we got a [Scryfall](https://scryfall.com/) wrapper for searching the cards.
See the Scryfall API documentation for more information on what can be searched
(only some operations and some filters are supported, though).

### How to run the project
In order to run the project, you can just import it into your favorite IDE and run it. Alternatively, you can build the project on maven and run

<code>./mvnw spring-boot:run</code>

The API will be running on port 3000 by default (configurable on the application.properties file).

After the API is running, you can go to the [Swagger instance](http://localhost:3000/swagger-ui/index.html) to have a better view of what is available on the API.

Some reference materials:
* [Scryfall](https://scryfall.com/)
* [Spring](https://spring.io/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [JUnit](https://junit.org/)
* [Mockito](https://site.mockito.org/)
* [Swagger](https://swagger.io/)

### Next steps
Since the project is still not finished, these are the next planned steps:

* Building and saving decks
* Creating a frontend client in a separate project