/**
 * @Date 2016年7月19日
 *
 * @author Administrator
 */
package dbhelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.XMLUtils;

/**
 * 提供实现数据库的连接获取以及各种连接释放工作
 * 
 * @author 郭瑞彪
 *
 */
public class DbHelper {

	/**
	 * 用于暂时的存储数据库信息的Map
	 */
	public static Map<String, String> map = new HashMap<String, String>();

	/**
	 * 获取单例模式下的数据库连接对象
	 */
	public static Connection conn = null;

	/**
	 * 通过XMLUtils实现对配置文件的信息读取，并实现DriverManager的使用
	 * 
	 * @throws Exception
	 */
	public static void register() throws Exception {

		Document document = XMLUtils.getDocument();
		NodeList nodes = document.getElementsByTagName("database");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);
			switch (element.getAttribute("name")) {
			case "mysql":
				NodeList childs = element.getChildNodes();
				for (int j = 0; j < childs.getLength(); j++) {
					Node node = childs.item(j);
					String nodename = node.getNodeName();
					String nodevalue = node.getTextContent();
					map.put(nodename, nodevalue);
				}
				break;
			case "sqlserver":
				break;
			}
		}
	}

	/**
	 * 清除从数据库配置中读取到的数据信息
	 */
	public static void unRegister() {
		map.clear();
	}

	/**
	 * 单例模式下的数据库连接对象获取方式
	 * 
	 * @return 返回单例模式下的数据库连接对象
	 */
	public static Connection getConn() {
		try {
			// 如果已经存在一个conn，则下面的代码不会执行。直接返回已经存在的数据库连接对象
			if (conn != null) {
				return conn;
			}
			String DRIVER = map.get("driver");
			String URL = map.get("url");
			String USER = map.get("user");
			String PASSWORD = map.get("password");
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			// 返回新创建的数据库连接对象
			if (conn != null) {
				return conn;
			}
		} catch (Exception e) {
			throw new RuntimeException("获取数据库连接时出错！ :\n" + e);
		}
		return null;
	}

	/**
	 * 释放数据库连接对象
	 * 
	 * @param conn
	 *            给定的数据库连接对象
	 */
	public static void release(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			throw new RuntimeException("释放数据库连接对象时出错！ :\n" + e);
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 释放数据库连接对象以及数据库查询对象
	 * 
	 * @param conn
	 *            数据库连接对象
	 * @param stmt
	 *            数据库查询语句
	 */
	public static void release(Connection conn, Statement stmt) {

		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (Exception e) {
			throw new RuntimeException("释放数据库查询对象Statement时出错！ :\n" + e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			throw new RuntimeException("释放数据库连接对象时出错！ :\n" + e);
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void release(Connection conn, Statement stmt, ResultSet rs) {

		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (Exception e) {
			throw new RuntimeException(" 关闭数据库结果集对象时出错！:\n" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (Exception e) {
			throw new RuntimeException("释放数据库查询对象Statement时出错！ :\n" + e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			throw new RuntimeException("释放数据库连接对象时出错！ :\n" + e);
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
