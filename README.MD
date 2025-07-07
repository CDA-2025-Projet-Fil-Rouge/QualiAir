### application.properties

````
spring.application.name=QualiAir
spring.datasource.url=jdbc:mariadb://localhost:3306/air
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
springdoc.api-docs.path=/api-docs
spring.jpa.properties.hibernate.format_sql=false
recensement.fichier.communes-with-pop.path=data/communesAvecPopulation.csv
recensement.fichier.communes-with-coord.path=data/communesAvecCoordonnees.csv
jwt.secret=monSuperSecretmonSuperSecretmonSuperSecretmonSuperSecretmonSuperSecret
jwt.cookie=AUTH-TOKEN
jwt.expires_in=8640000
logging.level.root=INFO
logging.level.fr.diginamic.qualiair=DEBUG
````
