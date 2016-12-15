package connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Semaphore;

import konstanty.Konstanty;




public class Database {
	protected String database;
	private String stringConnection;
	private Connection connection;
	protected ResultSet rs;
	private static final Semaphore semafor = new Semaphore(Konstanty.maximalnyPocetConnectionov);
	
	protected Database(){
		setStringConnection(Konstanty.adresa, Konstanty.databaza_Frisbee, Konstanty.user, Konstanty.password);
	}
	
	protected void setStringConnection(String port, String database, String user, String password){
		stringConnection = String.format("jdbc:mysql://%s/%s?user=%s&password=%s&characterEncoding=UTF-8&useUnicode=true", port, database, user, password);
		this.database = database;
	}
	
	
	protected void CreateConnection() throws SQLException, ClassNotFoundException{
		try {semafor.acquire();} catch (InterruptedException e) {e.printStackTrace();}
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(stringConnection);
	}
	
	protected void DisconectConnection() throws SQLException{
		if (connection != null){
			if (rs != null){
				rs.close();
			}
			connection.close();
			semafor.release();
		}
	}
	
	protected String upravSelectStlpceFormat(String[] columns){
		StringBuilder sqlColumns = new StringBuilder();
		
		for (String column: columns){
			sqlColumns.append(column);
			sqlColumns.append(',');
		}

		sqlColumns.delete(sqlColumns.length()-1, sqlColumns.length());
		
		return sqlColumns.toString();
	}
	
	
	
	
	protected void getQuery(String SQL) throws SQLException{
		rs = null;
		if (connection != null){
			rs = connection.createStatement().executeQuery(SQL);
		}
	}

	public void executeUpdate(String SQL) throws SQLException{
		if (connection != null){
			connection.createStatement().executeUpdate(SQL);
		}
	}

	public void execute(String SQL) throws SQLException {
		if (connection != null){
			connection.createStatement().execute(SQL);
		}
	}

	protected int getCountQueryOneColumn(String table, String column) throws SQLException, NullPointerException{
		String SQL = String.format("SELECT COUNT(%s) FROM %s.%s;", column, database, table);
		
		getQuery(SQL);
		rs.next();
		
    return rs.getInt(1);
	}
	
	
	protected void getQueryOneColumn(String table, String column) throws SQLException{
		String SQL = String.format("SELECT %s FROM %s.%s;", column, database, table);		
		
		getQuery(SQL);
	}
	
	protected void getQueryOneColumnDistinct(String table, String column) throws SQLException{
		String SQL = String.format("SELECT DISTINCT %s FROM %s.%s ORDER BY %s ASC;", column, database, table, column);		
		
        getQuery(SQL);
	}
	
	protected void getQueryMoreColumns(String table, String[] columns) throws SQLException{
		String SQL = String.format("SELECT %s FROM %s.%s;", upravSelectStlpceFormat(columns),database, table);
	
		getQuery(SQL);
	}
	
	/**
	 * Moze vratit aj viac stlpcov ak je columns
	 * @throws SQLException 
	 */
	protected void getConditionQuery(String table, String[] columns, String condition) throws SQLException{
		String SQL = String.format("SELECT %s FROM %s.%s WHERE %s;", upravSelectStlpceFormat(columns), database, table, condition);		
		
        getQuery(SQL);
	}
	
	protected void getConditionQueryOneColumn(String table, String column, String condition) throws SQLException{
		String SQL = String.format("SELECT %s FROM %s.%s WHERE %s;", column, database, table, condition);		
		
        getQuery(SQL);
	}
	
	protected void StratTransaction() throws SQLException{
		if (connection != null){
			connection.setAutoCommit(false);
		}else{
			throw new SQLException("Connection nebol najdeny.");
		}
	}
	
	
	
	private void StopTransaction() throws SQLException{
		try{
			if (connection != null){
				connection.commit();				
			}
		}finally{
			if (connection != null){
				connection.setAutoCommit(true);
			}else{
				throw new SQLException("Connection nebol najdeny.");
			}
		}
	}
	
	protected boolean EndTransaction() throws SQLException {
		try{
			StopTransaction();
		}catch (SQLException e){
			if (connection != null){
				connection.rollback();
			}
		}
		return true;
	}
	
	protected PreparedStatement getPreparedStatement(String SQL) throws SQLException{
		if (connection != null){
			return connection.prepareStatement(SQL);
		}
		throw new SQLException("Nenasiel sa connection.");
	}
	
	protected boolean InsertUpdateDeleteQuery(String SQL) throws SQLException{
		if (connection != null){
			Statement stmt = connection.createStatement();
			boolean vysledok = stmt.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS) > 0 ? true : false;
			rs = stmt.getGeneratedKeys();
			return vysledok;
		}
		return false;
	}
}

