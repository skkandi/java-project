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
        sh "cp dist/rectangle_${env.BUILD_NUMBER}.jar /var/www/html/rectangles/all/"
      }
    }
    stage("running on CentOS") {
      agent{
        label 'centos'
      }
      steps{
        sh "wget http://skandi-dtcc-com1.mylabserver.com/rectangles/all/rectangle_${env.BUILD_NUMBER}.jar"
        sh "java -jar rectangle_${env.BUILD_NUMBER}.jar 3 4"
      }
    }
    stage("running on debian") {
      agent{
        docker 'openjdk:8u131-jre'
      }
      steps{
        sh "wget http://skandi-dtcc-com1.mylabserver.com/rectangles/all/rectangle_${env.BUILD_NUMBER}.jar"
        sh "java -jar rectangle_${env.BUILD_NUMBER}.jar 4 5"
        sh "java -jar rectangle_${env.BUILD_NUMBER}.jar 3 3"
      }
    }
    stage('promote to green'){
      agent {
        label 'apache'
      }
      when {
        branch 'development'
      }
      steps{
        sh "cp /var/www/html/rectangles/all/rectangle_${env.BUILD_NUMBER}.jar /var/www/html/rectangles/green/rectangle_${env.BUILD_NUMBER}.jar"
      }
    }
  }

}
