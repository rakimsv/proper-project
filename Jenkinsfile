pipeline {
    agent any

    stages {
        stage('Testing a Ryan') {
            steps {
		    sh 'mvn test -Dtest=ControllerAndServiceSuite'
		    sh 'mvn test -Dtest=IntegrationSuite'
            }
        }
        stage('Building a Ryan') {
            steps {
                    sh 'mvn package -DskipTests'
		    sh 'docker build -t="rakimsv/simple-project:latest" .'
            }
        }
        stage('Staging a Ryan') {
            steps {
                   echo "Staging"
            }
        }
        stage('Deploying a Ryan') {
            steps {
		   sh 'docker push "rakimsv/simple-project:latest"'
                   echo "Deploy"
            }
        }
	stage('Ryan Deployed') {
	    steps {
		    echo "Ryan Deployed"
	    }
	}
    }
}
