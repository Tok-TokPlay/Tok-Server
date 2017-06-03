import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MusicDataBase {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	
	private String driverName = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/o2";
	private String user = "root";
	private String password = "js1112";
	
	public MusicDataBase(String musicNum){
		try{
			Class.forName(driverName);
			connection = DriverManager.getConnection(url, user, password);
			
			statement = connection.createStatement();
			
			/*insert ����
			if(funcName.equals("INSERT")){
				Scanner scan = new Scanner(System.in);
				String music;
				String singer;
				
				music = scan.nextLine();
				
				singer = scan.nextLine();
				
				String sql = "INSERT INTO toktok(music, singer) VALUES ('" + music + "', '" + singer + "')";
				statement.executeUpdate(sql);
			}	
			
			else if(funcName.equals("DELETE")){
				Scanner scan = new Scanner(System.in);
				String musicKey = scan.nextLine();
				String sql = "DELETE FROM toktok WHERE musicKey=" + musicKey + ";";
				statement.executeUpdate(sql);
			}*/
			
			//else if(funcName.equals("SELECT")){//
				String sql = "SELECT * FROM toktok WHERE musicKey=" + musicNum + ";";
				resultSet = statement.executeQuery(sql);
				while(resultSet.next()){
					String musicKey = resultSet.getString("musicKey");
					String music = resultSet.getString("music");
					String singer = resultSet.getString("singer");
					//retuVal = music + "66" + singer;
					
				}
		//	}//
			
			
		}catch (ClassNotFoundException e){
			System.out.println("[�ε� ����]\n" + e.getStackTrace());
		}
		catch (SQLException e){
			System.out.println("[���� ����]\n" +  e.getStackTrace());
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}
	public String getMusicName(String key){
		String musicName = "";
		return musicName;
	}
	public String getMusicSinger(String key){
		String musicSinger = "";
		return musicSinger;
		
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
			System.out.println("[�ݱ� ����]\n" +  e.getStackTrace());
		}
	}
}
