




mvn install:install-file -Dfile=target/maven-archiver/sketch-1.7.0-noarch.jar   -DgroupId=“ece.utexas.edu”      -DartifactId=“sketch-lib” -Dversion=0.1 -Dpackaging=“”

 mvn clean compile assembly:single

 assumption

 assume functions are independent except constructor func
