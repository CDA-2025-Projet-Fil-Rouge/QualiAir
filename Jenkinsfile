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

        stage('Compile with maven') {
            steps {
                sh 'cd back-end'
                sh 'mvn clean package'
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

    }
}
