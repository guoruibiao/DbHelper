/**
 * @Date 2016年7月19日
 *
 * @author Administrator
 */
package handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dbhelper.Converter;

/**
 * @author 郭瑞彪
 *
 */
public class BeanListHandler<T> implements ListHandler<T> {

	/**
	 * The Class of beans produced by this handler.
	 */
	private final Class<T> type;

	/**
	 * Creates a new instance of BeanHandler.
	 *
	 * @param type
	 *            The Class that objects returned from <code>handle()</code> are
	 *            created from.
	 */

	public BeanListHandler(Class<T> type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see handlers.Handler#handle(java.sql.ResultSet)
	 */
	@Override
	public List<T> handle(ResultSet rs) throws SQLException {
		try {
			return rs != null ? Converter.convert2BeanList(rs, this.type) : null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
