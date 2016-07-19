/**
 * @Date 2016年7月19日
 *
 * @author Administrator
 */
package handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 郭瑞彪
 *
 */
public interface ListHandler<T> {

	/**
	 * 没办法了，貌似不能继承，只好再创建一个接口了
	 * 
	 * @param rs
	 *            数据库结果集对象
	 * @return 返回一个泛型的封装了查询结果的集合
	 * @throws SQLException
	 */
	List<T> handle(ResultSet rs) throws SQLException;
}
