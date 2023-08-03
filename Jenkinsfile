    pipeline {
        agent any

        stages {
            stage('Build') {
                steps {
                    sh 'mvn clean compile'
                }
            }
            stage('Test') {
                steps {
                    sh 'mvn test'
                }
            }
            stage('SonarQube Analysis') {
                steps {
                    withSonarQubeEnv(installationName:'sq1') {
                        sh "mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=squ_733c2ee06dd96019e53b39a1741a57b3ec940840"
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
        }
    }
