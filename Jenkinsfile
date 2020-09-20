pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                checkout scm
                sh 'sbt compile'
            }
        }
        stage('Test') {
            steps {
                sh 'sbt test'
            }
            post {
                always {
                    junit 'target/test-reports/TEST*.xml'
                }
            }
        }
        stage('Package') {
            steps {
                sh 'sbt assembly'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/scala*/*.jar', fingerprint: true
                }
            }
        }
    }
}
