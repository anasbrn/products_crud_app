pipeline {
    agent any

    stages {
        stage("Build") {
            sh 'mvn install'
        }

        stage("Unit test") {
            sh 'mvn test'
        }
    }
}