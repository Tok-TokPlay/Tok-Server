import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTableInit{
	Connection connection;
	Statement statement;
	ResultSet resultSet;
	
	String driverName = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/o2";
	String user = "root";
	String password = "js1112";
	
	public DBTableInit(){
		try{
			// ① 로드
			Class.forName(driverName);
			System.out.println("Driver loading success");
			// ② 연결
			connection = DriverManager.getConnection(url, user, password);
			System.out.println("DB server connection success");
			
			//Statement 얻기
			statement = connection.createStatement();
			System.out.println("Receiving Statement success");
			
			//Drop table
			String sql = "DROP TABLE IF EXISTS toktok;";
			statement.executeUpdate(sql);
			sql = "CREATE TABLE `toktok` ( `musicKey` int(11) NOT NULL AUTO_INCREMENT, `music` varchar(30) NOT NULL, `singer` varchar(30) NOT NULL, PRIMARY KEY(musicKey)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
			statement.executeUpdate(sql);
			
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