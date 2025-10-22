def COLOR = [
    'SUCCESS': 'good',
    'FAILURE': 'danger'
]

pipeline {
    agent any

    environment {
        registryCreds = "ecr:us-east-1:awscreds"
        imageName = "701544682801.dkr.ecr.us-east-1.amazonaws.com/products-app-img"
        registry = "https://701544682801.dkr.ecr.us-east-1.amazonaws.com"
        cluster = "products"
        service = "products_task-service-i8g72zfh"
    }

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

//         stage("SonarQube Analysis") {
//             environment {
//                 scannerHome = tool 'sonarScanner7'
//             }
//             steps {
//                 withSonarQubeEnv('sonarServer') {
//                      sh """${scannerHome}/bin/sonar-scanner \
//                         -Dsonar.projectKey=products_app \
//                         -Dsonar.projectName=products_App \
//                         -Dsonar.sources=src \
//                         -Dsonar.java.binaries=target/classes/ \
//                         -Dsonar.host.url=${SONAR_HOST_URL} \
//                         -Dsonar.token=${SONAR_AUTH_TOKEN}"""
//                 }
//             }
//         }

//         stage("Quality Gate") {
//             steps {
//                 timeout(time:1, unit: 'HOURS') {
//                     waitForQualityGate abortPipeline: true
//                 }
//             }
//         }

//         stage("Upload Artifact - Nexus") {
//             steps {
//                 nexusArtifactUploader(
//                     nexusVersion: 'nexus3',
//                     protocol: 'http',
//                     nexusUrl: '172.31.21.48:8081',
//                     groupId: 'org.brnanas',
//                     version: "${env.BUILD_ID}-${env.BUILD_TIMESTAMP}",
//                     repository: 'products_app',
//                     credentialsId: 'nexuslogin',
//                     artifacts: [
//                         [artifactId: 'products_app',
//                             type: 'war',
//                             classifier: 'debug',
//                             file: 'target/products_app-v1.war']
//                     ]
//                )
//             }
//         }

            stage("Build Docker") {
                steps {
                    script {
                        dockerImage = docker.build(imageName + ":$BUILD_NUMBER")
                    }
                }
            }

            stage("Upload image to Registry") {
                steps {
                    script {
                        docker.withRegistry( registry, registryCreds) {
                            dockerImage.push("$BUILD_NUMBER")
                            dockerImage.push("latest")
                        }
                    }
                }
            }

            stage("Deploy to ECS") {
                steps {
                    withAWS(credentials: "awscreds", region: "us-east-1") {
                        sh 'aws ecs update-service --cluster ${cluster} --service ${service} --force-new-deployment'
                    }
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