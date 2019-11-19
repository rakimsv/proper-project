pipeline {
    agent any

    stages {
        stage('Testing a project') {
            steps {
		    echo "Testing a project"
            }
        }
        stage('Building a project') {
            steps {
                    sh 'mvn package -DskipTests'
		    sh 'docker build -t="rakimsv/proper-project:latest" .'
            }
        }
        stage('Staging a project') {
            steps {
                   echo "Staging"
            }
        }
        stage('Deploying a project') {
            steps {
		   sh 'docker push "rakimsv/proper-project:latest"'
                   echo "Deploy"
            }
        }
	stage('Project Deployed') {
	    steps {
		    echo "Project Deployed"
	    }
	}
    }
}
