导入本jar包之后应该在项目的src目录下创建一个db.cfg.xml文件。格式如下：
<?xml version="1.0" encoding="UTF-8" ?>
<project>
	<database name="mysql">
		<driver>com.mysql.jdbc.Driver</driver>
		<url>jdbc:mysql://localhost:3306/fams</url>
		<user>root</user>
		<password>mysql</password>
	</database>

</project>

然后就可以正常的使用了