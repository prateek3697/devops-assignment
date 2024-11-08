pipeline {
    agent any

    environment {
        AWS_REGION = 'us-west-2'           // Set your AWS region here
        ECR_REPO = 'devops-test'           // ECR repository name
        IMAGE_TAG = "${env.BUILD_NUMBER}"  // Image tag based on Jenkins build number
        ACCOUNT_ID = 'your-aws-account-id' // AWS Account ID
        REGISTRY = "${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
    }

    stages {
        stage('Checkout') {
            steps {
                // Pull code from the repository
                git 'https://github.com/prateek3697/devops-assignment.git'
            }
        }

        stage('Build') {
            steps {
                // Build Docker image using CLI
                sh "docker build -t ${REGISTRY}/${ECR_REPO}:${IMAGE_TAG} ."
            }
        }

        stage('Authenticate with ECR') {
            steps {
                // Authenticate Docker to the ECR registry
                withCredentials([aws(credentialsId: 'aws-credentials-id', region: "${AWS_REGION}")]) {
                    sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${REGISTRY}"
                }
            }
        }

        stage('Push Image') {
            steps {
                // Push the Docker image to ECR
                sh "docker push ${REGISTRY}/${ECR_REPO}:${IMAGE_TAG}"
            }
        }

        stage('Deploy') {
            steps {
                // Deploy to Kubernetes using kubectl
                sh """
                kubectl set image deployment/test \
                test=${REGISTRY}/${ECR_REPO}:${IMAGE_TAG}
                """
            }
        }
    }

    post {
        always {
            // Clean up workspace and remove Docker images after each run
            sh "docker rmi ${REGISTRY}/${ECR_REPO}:${IMAGE_TAG} || true"
            cleanWs()
        }
        success {
            echo 'Pipeline executed successfully!'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}