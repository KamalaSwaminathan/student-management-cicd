pipeline {
    agent any

    environment {
        IMAGE_NAME     = 'student-management'
        CONTAINER_NAME = 'student-management-app'
        HOST_PORT      = '8080'
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code from Git...'
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                echo 'Running Maven build and unit tests...'
                sh 'mvn clean test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging application as JAR...'
                sh 'mvn package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                sh "docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} -t ${IMAGE_NAME}:latest ."
            }
        }

        stage('Deploy Container') {
            steps {
                echo 'Deploying application container...'
                sh "docker rm -f ${CONTAINER_NAME} || true"
                sh "docker run -d --name ${CONTAINER_NAME} -p ${HOST_PORT}:8080 ${IMAGE_NAME}:latest"
            }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully. App is running at http://localhost:${HOST_PORT}"
        }
        failure {
            echo 'Pipeline failed. Check the stage logs above for details.'
        }
    }
}
