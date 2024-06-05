pipeline{
    agent any
    tools{
        maven 'local_maven'
    }
    environment{
        AWS_ACESS_KEY_ID=credentials=('jenkins-aws-secret-key-id)
        AWS_SECRET_ACESS_KEY_ID=credentials('jenkins-aws-secret-acess-key')

    }
    stages {
        stage('Build){
            steps{
                echo'build'
            }
        }
    }
    stage('Test'){
        steps{
            echo'test'
        }
    }
    stage('publish'){
        steps {
            sh 'mvn package'
        }
    }
    post {
        sucess{
            archiveArtifacts 'target/*.war'
            sh 'aws configure set region ap-south-1'
            sh 'aws s3 cp ./target/*.war s3://fudzeo'
        }
    }

}