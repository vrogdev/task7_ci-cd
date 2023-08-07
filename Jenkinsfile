    pipeline {
        agent any

        stages {
            stage('Build') {
                steps {
                    bat 'mvn clean compile'
                }
            }
            stage('Test') {
                steps {
                    bat 'mvn test'
                }
            }
            stage('SonarQube Analysis') {
                steps {
                    withSonarQubeEnv(installationName:'sq1') {
                        bat "mvn sonar:sonar"
                    }
                    jacoco()
                }
            }

            stage('SQuality Gate') {
                steps {
                    timeout(time: 1, unit: 'MINUTES') {
                        waitForQualityGate abortPipeline: true
                    }
                }
            }

            stage('Deploy') {
                steps {
                    bat 'mvn package'
                    deploy adapters: [tomcat9(credentialsId: 'Tomcat', path: '', url: 'http://localhost:8080/')], contextPath: 'TomcatMavenApp', war: '**/*.war'
                }
            }
        }
    }