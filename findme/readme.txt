SELECT * FROM tbuser t  where username='00/1xo' order by id desc
发布时，输出为WAR包，在服务器上解压，可避免在网上运行异常！
发布前一定要删除log4j的配置文件或jar文件，否则网站因屏蔽log4j而dwr出错
//////////////////////////////////////////////////////////////////////////////
1、CGLIB Enhancement failed 
环境：Spring2.0 Hibernate3.1.x/Hibernate3.2 

在使用Spring的AOP编程时，会用到这几个lib： 
asm-2.2.2.jar 
asm-commons-2.2.2.jar 
asm-util-2.2.2.jar 

Hibernate使用如果lib： 
asm.jar 
asm-attrs.jar 

net start mysql

其中asm-2.2.2.jar与asm.jar存在类上的冲突！！！ 
使用其中之一或两者都使用，可能会出现如下错误： 
java.lang.NoClassDefFoundError: org/objectweb/asm/CodeVisitor 
java.lang.NoClassDefFoundError: org/objectweb/asm/commons/EmptyVisitor 
java.lang.NoSuchMethodError: org.objectweb.asm.ClassVisitor.visit。。。。。。 


解决办法是： 
1.去掉类路径上的关于Hibernate的3个lib 
asm.jar 
asm-attrs.jar 
cglib-2.1.3.jar 

2.加入Spring中的以下4个lib 
在myeclipse preferences project capablities hibernate中加入以下lib
asm-2.2.2.jar 
asm-commons-2.2.2.jar 
asm-util-2.2.2.jar 
cglib-nodep-2.1_3.jar
//////////////////////////////////////////////////////////////////////////////
MyEclipse项目部署Deploy location不显示部署路径（图文）
项目右键——>Properties——>MyEclipse——>Web——>Context Root加上“/项目名称”，如下图
 如果发布不正常，该位置会出现错误提示。修改完成后如果不起作用就重启一下myeclipse 
 
2、部署按钮变成了灰色的,metadata文件有问题。
http://queue19.javaeye.com/blog/346850
Tomcat Undefined exploded archive location 项目不能部署
原因：
          在工程转移过程中，导致工程的配置文件出错；


解决方法：
          1.在工程目录下的.mymetadata文件中可能webrootdir被改无效了(把下面内容拷到你的.mymetadate文件中的相应位置上)；或者有可能少了这context-root这个属性；添加上这个属性即可，内容如下：
            context-root="/上面的name属性值"
          2.关掉Eclipse，再启动Eclipse，接着发布工程，发布成功！

例如:
Xml代码 
<?xml version="1.0" encoding="UTF-8"?>  
<project-module  
  type="WEB"  
  name="program_name"  
  id="myeclipse.1235376033685"  
  context-root="/program_name"  
  j2ee-spec="1.4"  
  archive="program_name.war">  
  <attributes>  
    <attribute name="webrootdir" value="WebRoot" />  
  </attributes>  
</project-module>  

3、js乱码问题
Javascript中文乱码的解决办法. 
在window--preferences--content type--
找到javascript一项把默认的字符集设置成UTF-8就可以啦


4、调试时断点与代码不相符
删除myeclipse6.5
装jdk1.65
装tomcat
装myelcipse6.5

5、mysql导入sql脚本
例如：我的用户名是root 密码是123 sql脚本存在C盘 名字为test.sql 数据库为test 
有两种方法可以执行脚本

1：打开CMD输入以下命令（不需要转换目录）
>mysql -u root -p123
进入MYSQL后
mysql>use test;
mysql> source c:/test.sql

ok执行完了，你可以用show tables;查看有哪写表（记得语句后有个分号）
还可以用desc tableName;查看你的表结构


2：打开CMD输入以下命令（进入mysql的bin目录）
还原
d:\mysql\bin>mysql -u root -p123 test < E:\WorkSpace\51AnyGo\backup20101208.sql
mysql -u root -p719799 udb_findme  < E:\WorkSpace\51AnyGo\findme\backup20101208.sql

mysql -u root -p
备份
cd C:\Program Files\MySQL\MySQL Server 5.1\bin
mysqldump -ufindme -pgoodbye -h mysql.jhost.cn udb_findme --skip-lock-tables > d:/backup20101208.sql

3: Caused by: java.lang.NoClassDefFoundError: ActionForm  解决

servlet-api.jar 这个文件报错，进行删除E:\WorkSpace\51AnyGo\findme\WebRoot\WEB-INF\lib

drop database udb_findme;
CREATE DATABASE udb_findme DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

4: ubuntu 下部署tomcat 的 “The scratchDir you specified“ 问题
解决方案－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
注意了。。。
我们只要把tomcat目录内的work文件夹删就万事大吉啦，这个问题搞了我三四天的时候，终于在http://topic.csdn.net/t/20061006/14/5064630.html#找到了正确的解决方案