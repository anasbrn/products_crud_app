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
            steps {
                withSonarQubeEnv('sonarServer') {
                     sh "${scannerHome}/bin/sonar-scanner \
                        -Dsonar.projectKey=products_app \
                        -Dsonar.projectName=Products App \
                        -Dsonar.sources=src \
                        -Dsonar.host.url=${SONAR_HOST_URL} \
                        -Dsonar.login=${SONAR_AUTH_TOKEN}"
                }
            }
        }
    }
}