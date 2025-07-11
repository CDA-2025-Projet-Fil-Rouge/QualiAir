pipeline {
    agent any


    stages {

        stage('Clean up'){
            steps {
            sh 'rm -rf back-end'
            sh 'rm -rf front-end'
            }
        }

        stage('Git Clone Repo Back') {
            steps {
                dir('back-end') {
                    git url: 'https://github.com/CDA-2025-Projet-Fil-Rouge/QualiAir.git',
                    branch: 'jenkins'
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

        stage('Write config files in spring app'){

            steps {

                // if(!fileExists("back-end/src/main/ressources")){
                //     sh 'mkdir back-end/src/main/ressources'
                // }
                    withCredentials([
                        file(credentialsId: 'API_PROPERTIES_APPLICATION', variable: 'app_properties'),
                        file(credentialsId: 'API_PROPERTIES_ATMO', variable: 'atmo_properties'),
                        file(credentialsId: 'API_PROPERTIES_MAIL', variable: 'mail_properties'),
                        file(credentialsId: 'API_PROPERTIES_OW', variable: 'ow_properties'),
                        ]) {
                            sh '''
                            mkdir -p back-end/src/main/resources
                            cp $app_properties back-end/src/main/resources/application.properties
                            cp $atmo_properties back-end/src/main/resources/atmo.properties
                            cp $mail_properties back-end/src/main/resources/mail.properties
                            cp $ow_properties back-end/src/main/resources/openweather.properties
                            '''
                        }

            }
        }


        stage('Compile with maven') {
            steps {
                sh '''
                cd back-end
                mvn clean install
                '''
            }
        }
    }
}
