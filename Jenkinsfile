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
                                branches: [[name: 'development']],
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
                    sh 'mvn clean package -DskipTests -Dspring.config.additional-location=$SPRING_EXTRA_CONF'
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
                        npm run build
                    '''
                }
            }
        }


        stage('SonarQube Analysis') {
            steps {
                dir('back-end') {
                    withSonarQubeEnv('SonarQube Server') {
                        sh '''
                            mvn clean verify sonar:sonar \
                                -DskipTests \
                                -Dspring.config.additional-location=$SPRING_EXTRA_CONF \
                                -Dspring.port=8081
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
                            echo "🚀 Deploying to EC2..."

                            # Copy backend JAR and frontend build
                            scp -o StrictHostKeyChecking=no back-end/target/*.jar ubuntu@15.188.10.164:/home/ubuntu/app.jar
                            scp -o StrictHostKeyChecking=no -r front-end/dist/* ubuntu@15.188.10.164:/home/ubuntu/front/

                            # Connect via SSH and deploy
                            ssh -o StrictHostKeyChecking=no ubuntu@15.188.10.164 <<'EOF'
                                set -e

                                echo "🧹 Cleaning old containers..."
                                docker stop myapp-backend || true
                                docker rm myapp-backend || true
                                docker stop myapp-db || true
                                docker rm myapp-db || true

                                echo "🗃️ Starting database..."
                                docker run -d --name myapp-db \
                                    -e POSTGRES_USER=myuser \
                                    -e POSTGRES_PASSWORD=mypass \
                                    -e POSTGRES_DB=mydb \
                                    -v /home/ubuntu/db-data:/var/lib/postgresql/data \
                                    -p 5432:5432 \
                                    postgres:15

                                echo "⚙️ Starting backend..."
                                docker run -d --name myapp-backend \
                                    --link myapp-db:db \
                                    -e SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/mydb \
                                    -e SPRING_DATASOURCE_USERNAME=myuser \
                                    -e SPRING_DATASOURCE_PASSWORD=mypass \
                                    -p 8080:8080 \
                                    -v /home/ubuntu/app.jar:/app.jar \
                                    openjdk:21-jdk \
                                    java -jar /app.jar

                                echo "🌐 Updating frontend..."
                                sudo rm -rf /var/www/html/*
                                sudo cp -r /home/ubuntu/front/* /var/www/html/

                                echo "🔁 Restarting nginx..."
                                sudo systemctl reload nginx

                                echo "✅ Deployment complete."
                            EOF
                        '''
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
