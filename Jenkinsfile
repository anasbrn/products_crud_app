pipeline {
    agent any

    stages {
        stage("Build") {
            steps {
                sh 'mvn install'
            }
        }

        stage("Unit test") {
            steps {
                sh 'mvn test'
            }
        }

        stage("SonarQube Analysis") {
            environment {
                scannerHome = tool 'sonarScanner7'
            }
            steps {
                withSonarQubeEnv('sonarServer') {
                     sh """${scannerHome}/bin/sonar-scanner \
                        -Dsonar.projectKey=products_app \
                        -Dsonar.projectName=products_App \
                        -Dsonar.sources=src \
                        -Dsonar.host.url=${SONAR_HOST_URL} \
                        -Dsonar.login=${SONAR_AUTH_TOKEN}"""
                }
            }
        }
    }
}