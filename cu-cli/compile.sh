
mvn clean install eclipse:eclipse -Dmaven.test.skip=true

mvn install:install-file -DgroupId=fr.treeptik -DartifactId=cu-cli -Dversion=1.9 -Dpackaging=jar -Dfile=target/CloudUnitCLI.jar
