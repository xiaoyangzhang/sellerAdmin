备注: Maven版本 3.0.X
#清空环境:
mvn clean


出包机器: 192.168.0.92
打包+装配+安装二进制：

测试环境
url: http://selleradmin.test.jiuxiulvxing.com/sellerAdmin/
mvn clean install  -Dmaven.test.skip=true

mvn deploy  -Dmaven.test.skip=true

autoconfig   /data/app/selleradmin/target/sellerAdmin.war -u  /data/app/selleradmin/run/latest.properties

开发联调
url: http://selleradmin.secondtest.jiuxiulvxing.com/sellerAdmin/
mvn clean install  -Dmaven.test.skip=true
mvn deploy  -Dmaven.test.skip=true
autoconfig   /data/app/selleradmin/target/sellerAdmin.war -u  /data/app/selleradmin/run/ladev.properties

预发环境

autoconfig   /data/pre/selleradmin/target/sellerAdmin.war -u  /data/pre/config/selleradmin.pre.properties




