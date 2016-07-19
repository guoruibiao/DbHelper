# DbHelper
一个比Apache-commons-dbutils更加好用，更加只能的微型框架

---

## 如何使用

- 首先在项目的src目录下创建一个db.cfg.xml的文件，格式如下：
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

- 然后是初始化工作，调用`DbHelper.register()`即可

- 再然后就可以使用`QueryRunner`来操作你的数据库结果集以及bean对象了。不妨看看下面的几个小实例。


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

## 延伸

由于源代码可以随意更改，所以你可以根据自己的需求来实现私人定制。仅仅需要实现自己的handler接口就可以了。
如果你看到了这篇文章，欢迎给我提issue。当然也可以修改成你自己的工具。
