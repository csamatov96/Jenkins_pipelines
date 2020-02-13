properties([[$class: 'JiraProjectProperty'], 
parameters([choice(choices: ['terraform 0.12.19', 'terraform_0.11.14', 'packer'], 
description: 'please choose the image ', 
name: 'DOCKERFILE'), 
string(defaultValue: 'v1', 
description: 'please choose the version ', 
name: 'VERSION', trim: false)])])

node {
    stage("PULLING REPO"){
        git 'https://github.com/csamatov96/repo_for_DockerImages.git'
        
    }
    stage("Building an Image"){
        if ('${DOCKERFILE} == terraform_0.12.19') {
            sh 'cd /var/lib/jenkins/workspace/task/terraform_0.12.19 && docker build -t app1:${VERSION} .'
 
        
        } else if('${DOCKERFILE} == terraform_0.11.14') {
            sh 'cd /var/lib/jenkins/workspace/task/terraform_0.11.14 && docker build -t app1:${VERSION} .'

        
        } else {
            sh 'cd /var/lib/jenkins/workspace/task/packer && docker build -t app1:${VERSION} .'
        }
        
    }
    stage("LIST THE IMAGES"){
        sh 'docker images'
        
    }  
    
    stage("Taggin an Image"){
        sh '''
        
        docker tag app1:${VERSION} ___918110389.dkr.ecr.us-east-1.amazonaws.com/task:${VERSION}
        
        '''
    }
    
    stage("Logging to Artifactory"){
        sh '''$(aws ecr get-login --no-include-email --region us-east-1)'''
    }
    
    stage("Pushing to Artifactory"){
        sh "docker push ___918110389.dkr.ecr.us-east-1.amazonaws.com/task:${VERSION}"
    }
}
