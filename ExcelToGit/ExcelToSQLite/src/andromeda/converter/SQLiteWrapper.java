package andromeda.converter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLiteWrapper {

	private Connection dbConnection;
	private String tableName;

	public SQLiteWrapper(String databaseName, String tableName) {
		try {
			this.tableName = tableName;
			Class.forName("org.sqlite.JDBC");
			//database path, if it's new data base it will be created in project folder
			dbConnection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public void createTable(ArrayList<String> keys) {		

		String updateStatement = "create table " + tableName;

		updateStatement += "(";
		for(int i = 0; i < keys.size(); i++) {
			if(i == keys.size() - 1) 
				updateStatement+= keys.get(i).trim().toLowerCase().replaceAll(" ", "_") + " varchar(40)";
			else
				updateStatement += keys.get(i).trim().toLowerCase().replaceAll(" ", "_")  + " varchar(40), ";
		}
		updateStatement += ");";

		try {
			Statement dropStatement = dbConnection.createStatement();
			dropStatement.executeUpdate("drop table if exists " + tableName);

			Statement createStatement = dbConnection.createStatement();
			createStatement.executeUpdate(updateStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	int count = 0;
	public synchronized void insertRow(ArrayList<String> values) {
		try {
			System.out.println("count = " + ++count + " | " + values.toString());
			PreparedStatement prep = dbConnection.prepareStatement("insert into " + tableName + " values(?,?,?,?,?);");
			for(int i=0; i < values.size(); i ++) {
				prep.setString(i + 1, values.get(i).trim().replace(String.valueOf((char) 160), ""));
			}
			prep.execute();
		}
		catch(SQLException s) {
			s.printStackTrace();
		}
	}


	public void dump() {
		Statement dumpStatement;
		try {
			dumpStatement = dbConnection.createStatement();

			ResultSet res = dumpStatement.executeQuery("select * from weights");
			while (res.next()) {
				System.out.println(res.getString("id") + " " + res.getString("age")
						+ " " + res.getString("firstName") + " "
						+ res.getString("sex") + " " + res.getString("weight")
						+ " " + res.getString("height") + " "
						+ res.getString("idealweight"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
