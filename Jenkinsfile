pipeline {
    agent any

    stages {
        stage('Git Clone Repo Back') {
            steps {
                dir('back-end') {
                    git url: 'https://github.com/CDA-2025-Projet-Fil-Rouge/QualiAir.git'
                }
            }
        }

        stage('Git Clone Repo front') {
            steps {
                dir('front-end') {
                    git credentialsId: 'quali-air-front-token',
                    url: 'https://github.com/CDA-2025-Projet-Fil-Rouge/QualiAir-Web.git',
                    branch: 'master'
                }
            }
        }

        stage('Write required config files'){
            steps {
                script {
                   def outputFileName = 'src/main/ressources/application.properties'
                   def lines = ['spring.application.name=QualiAir',
                    'spring.datasource.url=jdbc:mariadb://localhost:3306/air',
                    'spring.datasource.driver-class-name=org.mariadb.jdbc.Driver',
                    'spring.datasource.username=root',
                    'spring.datasource.password=root',
                    'spring.jpa.hibernate.ddl-auto=update',
                    'spring.jpa.show-sql=true',
                    'spring.jpa.properties.hibernate.format_sql=false',
                    'springdoc.api-docs.path=/api-docs',
                    'jwt.secret=monSuperSecretmonSuperSecretmonSuperSecretmonSuperSecretmonSuperSecret',
                    'jwt.cookie=AUTH-TOKEN',
                    'jwt.expires_in=8640000',
                    'logging.level.root=INFO',
                    'logging.level.fr.diginamic.qualiair=DEBUG',
                    'recensement.fichier.communes-with-pop.path=data/2024-12-19_donnees_communes.csv',
                    'recensement.fichier.departement.path=data/departement.csv',
                    'recensement.fichier.region.path=data/region.csv',
                    'recensement.fichier.communes-with-coord.path=data/villes_france_avec_coordonnees.csv'
                        ]
//                    def line2 = 'spring.datasource.url=jdbc:mariadb://localhost:3306/air'
//                    def line3 = 'spring.datasource.driver-class-name=org.mariadb.jdbc.Driver'
//                    def line4 = 'spring.datasource.username=root'
//                    def line5 = 'spring.datasource.password=root'
//                    def line6 = 'spring.jpa.hibernate.ddl-auto=update'
//                    def line7 = 'spring.jpa.show-sql=true'
//                    def line8 = 'spring.jpa.properties.hibernate.format_sql=false'
//                    def line9 = 'springdoc.api-docs.path=/api-docs'
//                    def line10 = 'jwt.secret=monSuperSecretmonSuperSecretmonSuperSecretmonSuperSecretmonSuperSecret'
//                    def line11 = 'jwt.cookie=AUTH-TOKEN'
//                    def line12 = 'jwt.expires_in=8640000'
//                    def line13 = 'logging.level.root=INFO'
//                    def line14 = 'logging.level.fr.diginamic.qualiair=DEBUG'
//                    def line15 = 'recensement.fichier.communes-with-pop.path=data/2024-12-19_donnees_communes.csv'
//                    def line16 = 'recensement.fichier.departement.path=data/departement.csv'
//                    def line17 = 'recensement.fichier.region.path=data/region.csv'
//                    def line18 = 'recensement.fichier.communes-with-coord.path=data/villes_france_avec_coordonnees.csv'

                   def writer = new File(outputFileName).newWrite()
                   lines.forEach {line -> writer.writeLine line}
                   writer.flush()
                   writer.close()
                }
            }
        }


        stage('Compile with maven') {
            steps {
                sh 'cd back-end'
                sh 'mvn clean package'
            }
        }
    }
}
