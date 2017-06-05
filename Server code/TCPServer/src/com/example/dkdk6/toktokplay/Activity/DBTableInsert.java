package com.example.dkdk6.toktokplay.Activity;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTableInsert{
	Connection connection;
	Statement statement;
	ResultSet resultSet;
	
	String driverName = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/o2";
	String user = "root";
	String password = "111111";
	
	public DBTableInsert(String music, String singer){
		try{
			// ① 로드
			Class.forName(driverName);
			// ② 연결
			connection = DriverManager.getConnection(url, user, password);
			
			//Statement 얻기
			statement = connection.createStatement();
					
			//insert 실행
			String sql = "INSERT INTO toktok(music, singer) VALUES ('" + music + "', '" + singer + "')";
			statement.executeUpdate(sql);
			System.out.println("insert success");
			
			
		}catch (ClassNotFoundException e){
			System.out.println("[Load Error]\n" + e.getStackTrace());
		}
		catch (SQLException e){
			System.out.println("[Connection Error]\n" +  e.getStackTrace());
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	
	public void closeDatabase()
	{
		try{
			if( connection != null ){
				connection.close();
			}
			
			if( statement != null ){
				statement.close();
			}
			
			if( resultSet != null ){
				resultSet.close();
			}
		}catch (SQLException e){
			System.out.println("[Closing Error]\n" +  e.getStackTrace());
		}
	}
}
