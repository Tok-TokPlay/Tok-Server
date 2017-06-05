package com.example.dkdk6.toktokplay.Activity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MusicDataBase {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	
	// SQL Database`s Data.
	private String driverName = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/o2";
	private String user = "root";
	private String password = "111111";
	
	public MusicDataBase() throws ClassNotFoundException, SQLException{
		Class.forName(driverName);
		// Add class driverName into RAM.
		// We can use this class after this code.
		connection = DriverManager.getConnection(url, user, password);
		statement = connection.createStatement();
		// Make connection with Database finished.
	}
	
	public String getMusicInfo(String key){
		// Get music name with key value ( fileName ).
		String sql = "SELECT * FROM toktok WHERE musicKey=" + key + ";";
		String musicKey = "";
		String musicName = "";
		String musicSinger = "";
		String musicInfo = "";
		try {
			// Get Value with given SQL Query at statement.
			resultSet = statement.executeQuery(sql);
			// Get music name from result.
			while(resultSet.next()){
				musicKey = resultSet.getString("musicKey");
				musicName = resultSet.getString("music");
				musicSinger = resultSet.getString("singer");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		musicInfo = musicName + ":" + musicSinger;
		return musicInfo;
	}
	
	/*public String getMusicSinger(String key){
		// Get music singer with key value ( fileName ).
		ResultSet resultSet;
		String musicSinger = "";
		String sql = "SELECT * FROM toktok WHERE musicKey=" + key + ";";
		try {
			// Get Value with given SQL Query at statement.
			resultSet = statement.executeQuery(sql);
			// Get music singer from result.
			musicSinger = resultSet.getString("singer");
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return musicSinger;
	}*/
	
	/*public void addMusic(String musicName, String musicSinger, String fileName ) {
		// append new music table to toktok entity.
		// entity have 3 attribute, name, singer, file name ( which is key value for musics. )
		String sql = "INSERT INTO toktok(music, singer, file) VALUES ('" + musicName + "', '" + musicSinger + "', '" + fileName + "')";
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/
	
	public void initTable()	{
		// Initialize table if beatract python module`s version is changed.
	}
	
	public void closeDatabase()
	{
		// Closing opened database controller.
		try{
			// under values are always on.
			connection.close();
			statement.close();
		}catch (SQLException e){
			e.getStackTrace();
		}
	}
}
