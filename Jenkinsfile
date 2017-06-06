pipeline {
  agent none

  stages  {
    stage('Unit Tests') {
      agent {
        label 'apache'
      }
      steps{
        sh 'ant -f test.xml -v'
        junit 'reports/result.xml'
      }
    }
    stage('build') {
      agent {
        label 'apache'
      }
      steps{
        sh 'ant -f build.xml -v'
      }
      post {
        success {
          archiveArtifacts artifacts: 'dist/*.jar', fingerprint: true
        }
      }
    }
    stage('deploy'){
      agent {
        label 'apache'
      }
      steps{
        sh "mkdir /var/www/html/rectangles/all/${env.BRANCH_NAME}"
        sh "cp dist/rectangle_${env.BUILD_NUMBER}.jar /var/www/html/rectangles/all/${env.BRANCH_NAME}/"
      }
    }
    stage("running on CentOS") {
      agent{
        label 'centos'
      }
      steps{
        sh "wget http://skandi-dtcc-com1.mylabserver.com/rectangles/all/${env.BRANCH_NAME}/rectangle_${env.BUILD_NUMBER}.jar"
        sh "java -jar rectangle_${env.BUILD_NUMBER}.jar 3 4"
      }
    }
    stage("running on debian") {
      agent{
        docker 'openjdk:8u131-jre'
      }
      steps{
        sh "wget http://skandi-dtcc-com1.mylabserver.com/rectangles/all/${env.BRANCH_NAME}/rectangle_${env.BUILD_NUMBER}.jar"
        sh "java -jar rectangle_${env.BUILD_NUMBER}.jar 4 5"
        sh "java -jar rectangle_${env.BUILD_NUMBER}.jar 3 3"
      }
    }
    stage('promote to green'){
      agent {
        label 'apache'
      }
      when {
        branch 'master'
      }
      steps{
        sh "cp /var/www/html/rectangles/all/rectangle_${env.BUILD_NUMBER}.jar /var/www/html/rectangles/green/rectangle_${env.BUILD_NUMBER}.jar"
      }
    }
    stage('promote development branch to master') {
      agent{
        label 'apache'
      }
      when {
        branch 'development'
      }
      steps{
        echo "stash local changes"
        sh 'git stash'
        echo " cehcking out development"
        sh 'git checkout development'
        echo "checkout master"
        sh 'git checkout master'
        echo 'merge dev to master'
        sh 'git merge development'
        echo 'pushing to master'
        sh 'git push origin master'
      }
    }
  }

}
