node{
    stage("Pull Repo"){
        ws ("tmp/"){
            git 'https://github.com/csamatov96/terraform-iaac-eks-burak.git' 
        }

    }
    stage("Download Terraform"){
        ws ("tmp/"){
            sh "terraform version"
            sh "wget https://releases.hashicorp.com/terraform/0.12.19/terraform_0.12.19_linux_amd64.zip"
            sh "unzip terraform_0.12.19_linux_amd64.zip"
            sh "./terraform version"
        }
    }
    stage("Set Backend"){
        ws "tmp/" {
            sh "./terraform init"

        }
        //sh "source setenv.sh configurations/dev/us-east-1/dev.tfvars"
        
    }
    stage("Plan"){
        ws ("tmp/") {
            sh "./terraform plan -var-file configurations/dev/us-east-1/dev.tfvars"
        }
        
    }
}