pipeline {
    agent any

    tools {
        maven 'maven'
    }

    stages {

        stage('Clean up') {
            steps {
                sh 'rm -rf back-end'
                sh 'rm -rf front-end'
            }
        }

        stage('Git Clone Repos') {
            parallel {
                stage('Back-end') {
                    steps {
                        dir('back-end') {
                            git url: 'https://github.com/CDA-2025-Projet-Fil-Rouge/QualiAir.git', branch: 'jenkins'
                        }
                    }
                }
                stage('Front-end') {
                    steps {
                        dir('front-end') {
                            git credentialsId: 'quali-air-front-token',
                                url: 'https://github.com/CDA-2025-Projet-Fil-Rouge/QualiAir-Web.git',
                                branch: 'master'
                        }
                    }
                }
            }
        }

        stage('Write Spring config') {
            steps {
                withCredentials([
                    file(credentialsId: 'API_PROPERTIES_APPLICATION', variable: 'app_properties'),
                    file(credentialsId: 'API_PROPERTIES_ATMO', variable: 'atmo_properties'),
                    file(credentialsId: 'API_PROPERTIES_MAIL', variable: 'mail_properties'),
                    file(credentialsId: 'API_PROPERTIES_OW', variable: 'ow_properties')
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

        stage('SonarQube Analysis') {
            steps {
                dir('back-end') {
                    withSonarQubeEnv('SonarQube') {
                        sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=qualiair-back'
                    }
                }
            }
        }

        stage('Compile with Maven') {
            steps {
                dir('back-end') {
                    sh 'mvn clean install'
                }
            }
        }
    }
}
