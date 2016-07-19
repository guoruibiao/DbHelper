/**
 * @Date 2016年7月19日
 *
 * @author Administrator
 */
package handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 主接口， 方便其实现类能处理各种类型的数据
 * 
 * @author 郭瑞彪
 *
 */
public interface Handler<T> {

	public T handle(ResultSet rs) throws SQLException;

}
