#!groovy

pipeline {
    agent any

    stages {
        stage('Build & Test') {
            steps {
                echo 'Building...'
                sh 'mvn verify'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying...'
            }
        }
    }
}
