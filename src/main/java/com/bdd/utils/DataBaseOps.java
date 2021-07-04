package com.bdd.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import static com.bdd.variables.GlobalVariables.*;

public class DataBaseOps {
	/**
	 * Method to make connection to the DB
	 * 
	 * @author triveni
	 * @since 2021-06-23
	 * @return connection
	 * @throws SQLException
	 * @throws throwable
	 */

	public static Connection connectToDataBase() throws SQLException {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp", "root", "root");
			System.out.println("Connected to database successfully");
			getdbConnection=con;
		} catch (SQLException sqlE) {
			sqlE.printStackTrace();
		}
		;
		return con;
	}

	/**
	 * Method to check connect
	 * 
	 * @author triveni
	 * @since 2021-06-23
	 * @return connection
	 * @throws SQLException
	 * @throws throwable
	 */

	public static void checkDBconnection() throws SQLException {
		if (!getdbConnection.isValid(0)) {
			Log.info("DB connection expired, reopening...");
			getdbConnection = connectToDataBase();
		}
	}

	/**
	 * Method to get db values from the select querry
	 * 
	 * @author triveni
	 * @since 2021-06-23
	 * @return connection
	 * @throws SQLException
	 * @throws throwable
	 */
	public static String getDBResultValue(String query) {
		String value = null;
		Statement stm = null;
		ResultSet result = null;
		try {
			checkDBconnection();
			stm = getdbConnection.createStatement();
			result = stm.executeQuery(query);
			while (result.next()) {
				value = result.getString(1);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			try {
				if (stm != null)
					stm.close();
				if (result != null)
					result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return value;
	}

	/**
	 * Method to get result set
	 * 
	 * @author triveni
	 * @since 2021-06-23
	 * @return connection
	 * @throws SQLException
	 * @throws throwable
	 */

	public static LinkedHashMap getResultSet(String sql) {
		ResultSet resultset = null;
		 resultvalues = new LinkedHashMap<>();
		 LinkedList<String> list;
		 ResultSetMetaData resultsetMedata = null;
		try {
			checkDBconnection();
			Statement stm = getdbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					resultset.CONCUR_READ_ONLY);
			resultset = stm.executeQuery(sql);
			resultsetMedata =resultset.getMetaData();
			resultset.last();
			int rowscount=resultset.getRow();
			 int columns = resultsetMedata.getColumnCount();
			 resultset.beforeFirst();
			while(resultset.next()) {
				for(int i=1;i<=columns;i++) {
					String colname=resultsetMedata.getColumnLabel(i);
					list= new LinkedList<>();
					for(int j=1;j<=rowscount;j++) {
						
						list.add(resultset.getString(colname))	;
					}
					resultvalues.put(colname,list );
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return resultvalues;
	}

	/**
	 * Method to close connection
	 * 
	 * @author triveni
	 * @since 2021-06-23
	 * @return connection
	 * @throws SQLException
	 * @throws throwable
	 */

	public static void closeConnection() {
		if (getdbConnection != null) {
			try {
				getdbConnection.close();
			} catch (Exception e) {
				System.out.println("exception in closing db");
			}
		}
	}

}
