cd /d %~dp0..
call mvn clean package -DskipTests

call autoconfig %~dp0..\target\sellerAdmin.war -u %~dp0test-third.properties
@pause