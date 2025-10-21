def COLOR = [
    'SUCCESS': 'good',
    'FAILURE': 'danger'
]

pipeline {
    agent any

    stages {
        stage("Build") {
            steps {
                sh 'mvn clean package -DskipTests'
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
                        -Dsonar.java.binaries=target/classes/ \
                        -Dsonar.host.url=${SONAR_HOST_URL} \
                        -Dsonar.token=${SONAR_AUTH_TOKEN}"""
                }
            }
        }

        stage("Quality Gate") {
            steps {
                timeout(time:1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage("Upload Artifact - Nexus") {
            steps {
                nexusArtifactUploader(
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    nexusUrl: 'http://172.31.21.48:8081',
                    groupId: 'org.brnanas',
                    version: '${env.BUILD_ID}-${env.BUILD_TIMESTAMP}',
                    repository: 'products_app',
                    credentialsId: 'nexuslogin',
                    artifacts: [
                        [artifactId: 'products_app',
                            type: 'war',
                            classifier: 'debug',
                            file: 'products_app-v1.war']
                    ]
               )
            }
        }
    }

    post {
        always {
            slackSend (
                color: COLOR[currentBuild.currentResult],
                message: "*${currentBuild.currentResult}:* Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}"
            )
        }
    }
}