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
    }
}