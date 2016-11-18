# DbHelper
一个比Apache-commons-dbutils更加好用，更加简洁轻便的微型框架
![镇楼图](http://img.blog.csdn.net/20160719164640373)
---

## 如何使用

- Step 1：
	首先在项目的src目录下创建一个db.cfg.xml的文件，格式如下：
```
<?xml version="1.0" encoding="UTF-8" ?>
<project>
	<database name="mysql">
		<driver>com.mysql.jdbc.Driver</driver>
		<url>jdbc:mysql://localhost:3306/fams</url>
		<user>root</user>
		<password>mysql</password>
	</database>
	···
</project>
```

- Step 2:
	通过注册的方式实现配置文件中的数据源的配置
	`Dbhelper.register()`即可


- Step 3：
	主要的业务逻辑操作类`QueryRunner`，封装了对数据库JDBC操作的增删改查等一系列的操作。
	对于update方法的JDBC操作，我们无需手动的关闭数据库连接，仅仅简单的通过DbHelper的重载的release方法来实现对数据库连接对象，查询语句以及数据集的关闭操作。是不是感觉很省心啊。


---

## 测试实例

这里先根据数据库中的表结构来创建一个对应的Bean对象吧。
```
/**
 * @Date 2016年7月19日
 *
 * @author Administrator
 */

/**
 * @author 郭瑞彪
 *
 */
public class DateTest {

	private int id;
	private String name;
	private Date date;

	@Override
	public String toString() {
		return "DateTest [id=" + id + ", name=" + name + ", date=" + date + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}


```

然后是测试代码：

```

import java.sql.Connection;
import java.util.List;

import dbhelper.DbHelper;
import dbhelper.QueryRunner;
import handlers.BeanListHandler;

/**
 * @Date 2016年7月19日
 *
 * @author Administrator
 */

/**
 * @author 郭瑞彪
 *
 */
public class Main {
	public static void main(String[] args) throws Exception {
		String sql = "select * from datetest";
		QueryRunner runner = new QueryRunner();
		DbHelper.register();
		Connection conn = DbHelper.getConn();
		List<DateTest> dates = runner.query(conn, sql, new BeanListHandler<DateTest>(DateTest.class));
		for (DateTest date : dates) {
			System.out.println(date.toString());
		}
		System.out.println("------------------------------------------------------");
	}
}

```

结果如下：
```
DateTest [id=1, name=dlut, date=Wed Jul 06 00:00:00 CST 2016]
DateTest [id=2, name=清华大学, date=Sun Jul 03 00:00:00 CST 2016]
DateTest [id=3, name=北京大学, date=Thu Jul 28 00:00:00 CST 2016]
------------------------------------------------------
```

通用的使用方式大致如下

> 本人在@BeforeClass 方法里面已经声明过DbHelper.register();   如果您的测试代码里面没有这个语句，就会报：未注册数据源的错误啦。请注意哦。

 获取集合形式的封装结果集
```
// 对于无参的查询封装操作
@Test
public void test() throws Exeption {
	Connection conn = DbHelper.getConn();
	String sql = "select * from yourtablename";
	QueryRunner runner = new QUeryRunner();
	List<YourBean> yourbaen =  (List<YourBean>) runner.query(conn,sql,new BeanListHandler<YourBean>(YourBean.class));
	System.out.println(yourbean.toString());
	// 可以手动的进行数据库连接对象的关闭操作
	DbHelper.release(conn);
}

```
使用不定参数的查询语句，返回ResultSet的对应的JavaBean对象的实例

```
@Test
public void test1() throws Exception {
	Connection conn = DbHelper.getConn();
	String sql = "select * from youtablename where columnname=?";
	Object[] params = {'admin'};
	QueryRunner runner = new QueryRuner();
	YourBean yourbean = runner.query(conn,sql, new BeanHandler<YourBean>(YourBean.class),params);
	DbHelper.release(conn);
	System.out.println(yourbean.toString());
}
```

使用不定参数的演示实例
```
@Test
public void test2() throws Exception {
	Connection conn = DbHelper.getConn();
	String sql = "select ? from yourtablename  where id=?";
	Object[] params = {"admin",2};
	QueryRunner runner = new QueryRunner();
	YourBean yourbean = runner.query(conn,sql,new BeanHandler<YourBean>(YourBean.class),params);
	System.out.println(yourbean.toString());
	DbHelper.release(conn);
}
```

  更新Update方式
```
@Test
public void test3() throws Exception {
	Connection conn = DbHelper.getConn();
	String sql = "update yourtablename set columnname=? where columnname=?";
	Object[] params = {"admin","anothervalue"};
	QueryRunner runner = new QueryRunner();
	runner.update(conn,sql,params);
	System.out.println("Update database success!");
}

```

## 延伸

由于源代码可以随意更改，所以你可以根据自己的需求来实现私人定制。仅仅需要实现自己的handler接口就可以了。
如果你看到了这篇文章，欢迎给我提issue。当然也可以修改成你自己的工具。


## 快速应用

不妨参照下面的这篇文章，相信会给您些许灵感。http://blog.csdn.net/marksinoberg/article/details/53163704
