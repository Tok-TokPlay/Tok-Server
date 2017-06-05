import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class JDBCexam {
	Connection connection;
	Statement statement;
	ResultSet resultSet;
	
	String driverName = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/o2";
	String user = "root";
	String password = "js1112";
	
	public JDBCexam(String funcName){
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
			
			//insert 실행
			if(funcName.equals("INSERT")){
				Scanner scan = new Scanner(System.in);
				String music;
				String singer;
				
				System.out.println("Insert the music name");
				music = scan.nextLine();
				
				System.out.println("Insert the singer name");
				singer = scan.nextLine();
				
				String sql = "INSERT INTO toktok(music, singer) VALUES ('" + music + "', '" + singer + "')";
				statement.executeUpdate(sql);
				System.out.println("insert success");
			}	
			
			//Delete 실행
			else if(funcName.equals("DELETE")){
				Scanner scan = new Scanner(System.in);
				System.out.println("몇 번쨰 키값을 삭제할래?");
				String musicKey = scan.nextLine();
				String sql = "DELETE FROM toktok WHERE musicKey=" + musicKey + ";";
				statement.executeUpdate(sql);
				System.out.println(musicKey + " 번쨰 값이 삭제되었습니다.");
			}
			
			//select 실행
			else if(funcName.equals("SELECT")){
				String sql = "SELECT * FROM toktok";
				resultSet = statement.executeQuery(sql);
				while(resultSet.next()){
					String musicKey = resultSet.getString("musicKey");
					String music = resultSet.getString("music");
					String singer = resultSet.getString("singer");
					System.out.print("musicKey value ; " + musicKey);
					System.out.print("\tmusicName value ; " + music);
					System.out.println("\tsingerName value ; " + singer);
				}
			}
			
			
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
