pipeline {
    agent any

    tools {
        maven 'Maven'
        nodejs 'Node'
    }

    options {
        disableConcurrentBuilds()
        skipDefaultCheckout(true)
        timestamps()
    }

    environment {
        SPRING_EXTRA_CONF = "${WORKSPACE}/runtime-conf"
    }

    stages {

        stage('Clean workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Checkout Repositories') {
            parallel {
                stage('Checkout Back-end') {
                    steps {
                        dir('back-end') {
                            checkout([
                                $class: 'GitSCM',
                                branches: [[name: 'master']],
                                userRemoteConfigs: [[
                                    url: 'https://github.com/CDA-2025-Projet-Fil-Rouge/QualiAir.git',
                                    credentialsId: 'qualiair-jenkins'
                                ]],
                                extensions: [
                                    [$class: 'CleanBeforeCheckout'],
                                    [$class: 'CloneOption', depth: 0, noTags: true],
                                    [$class: 'DisableRemotePoll']
                                ]
                            ])
                        }
                    }
                }

                stage('Checkout Front-end') {
                    steps {
                        dir('front-end') {
                            checkout([
                                $class: 'GitSCM',
                                branches: [[name: 'master']],
                                userRemoteConfigs: [[
                                    url: 'https://github.com/CDA-2025-Projet-Fil-Rouge/QualiAir-Web.git',
                                    credentialsId: 'qualiair-jenkins'
                                ]],
                                extensions: [
                                    [$class: 'CleanBeforeCheckout'],
                                    [$class: 'CloneOption', depth: 0, noTags: true],
                                    [$class: 'DisableRemotePoll']
                                ]
                            ])

                            sh 'ls -la'
                        }
                    }
                }
            }
        }

        stage('Prepare Spring config') {
            steps {
                sh 'mkdir -p "$SPRING_EXTRA_CONF"'
                withCredentials([
                    file(credentialsId: 'API_PROPERTIES_APPLICATION', variable: 'APP_P'),
                    file(credentialsId: 'API_PROPERTIES_ATMO', variable: 'ATMO_P'),
                    file(credentialsId: 'API_PROPERTIES_MAIL', variable: 'MAIL_P'),
                    file(credentialsId: 'API_PROPERTIES_OW', variable: 'OW_P')
                ]) {
                    sh '''
                        cp "$APP_P"  "$SPRING_EXTRA_CONF/application.properties"
                        cp "$ATMO_P" "$SPRING_EXTRA_CONF/atmo.properties"
                        cp "$MAIL_P" "$SPRING_EXTRA_CONF/mail.properties"
                        cp "$OW_P"   "$SPRING_EXTRA_CONF/openweather.properties"
                    '''
                }
            }
        }

        stage('Build Back-end') {
            steps {
                dir('back-end') {
                    sh 'mvn -B -U clean verify -Dspring.config.additional-location=$SPRING_EXTRA_CONF'
                }
            }
        }

        stage('Prepare Front config') {
            steps {
                dir('front-end') {

                    withCredentials([
                        file(credentialsId: 'FRONTEND_SECRET_FILE', variable: 'FRONT_ENV')
                    ]) {
                        sh '''
                            cp "$FRONT_ENV" "src/config/secret.ts"
                        '''
                    }
                }
            }
        }

        stage('Build & Test Front-end') {
            steps {
                dir('front-end') {
                    sh '''
                        export CHROME_BIN=$(which chromium)
                        echo "Using Chrome binary: $CHROME_BIN"
                        npm ci
                        npm run test -- --browsers=ChromeHeadless --watch=false || echo "Some front tests failed"
                    '''
                }
            }
        }


        stage('SonarQube Analysis') {
            steps {
                dir('back-end') {
                    withSonarQubeEnv('SonarQube Server') {
                        sh '''
                            mvn -B sonar:sonar \
                                -Dspring.config.additional-location=$SPRING_EXTRA_CONF \
                                -Dspring.port=8081 \
                                -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                        '''
                    }
                }
            }
        }

        stage('SonarQube QualityGate') {
            steps {
                script {
                    timeout(time: 10, unit: 'MINUTES') {
                        def qg = waitForQualityGate()
                        if (qg.status != 'OK') {
                            error "Quality Gate failed: ${qg.status}"
                        }
                    }
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                script {
                    sshagent(['ec2-ssh-key']) {
                        sh '''
                            echo "Deploying to EC2..."

                            # copy artifacts (adjust target path as needed)
                            scp -o StrictHostKeyChecking=no back-end/target/*.jar ubuntu@15.188.10.164:/opt/myapp/backend/
                            scp -o StrictHostKeyChecking=no -r front-end/dist/* ubuntu@15.188.10.164:/var/www/html/

                            # restart services
                            ssh -o StrictHostKeyChecking=no ubuntu@15.188.10.164 <<'EOF'
                                systemctl restart myapp-backend.service
                                systemctl reload nginx
                                echo "Deployment complete."
                            EOF
                        '''
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
            cleanWs()
        }
        failure {
            echo 'Build failed. Check logs.'
        }
    }
}