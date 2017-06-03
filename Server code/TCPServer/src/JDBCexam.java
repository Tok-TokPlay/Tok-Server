import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JDBCexam {
	Connection connection;
	Statement statement;
	ResultSet resultSet;
	
	String driverName = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/o2";
	String user = "root";
	String password = "js1112";
	
	private String retuVal="";
	
	public JDBCexam(String musicNum){
		try{
			// ① 로드
			Class.forName(driverName);
			//System.out.println("로드 성공");
			// ② 연결
			connection = DriverManager.getConnection(url, user, password);
			//System.out.println("연결 성공");
			
			//Statement 얻기
			statement = connection.createStatement();
			//System.out.println("statement 받음");
			
			/*insert 실행
			if(funcName.equals("INSERT")){
				Scanner scan = new Scanner(System.in);
				String music;
				String singer;
				
				System.out.println("music명을 입력해주세요");
				music = scan.nextLine();
				
				System.out.println("가수명을 입력해주세요");
				singer = scan.nextLine();
				
				String sql = "INSERT INTO toktok(music, singer) VALUES ('" + music + "', '" + singer + "')";
				statement.executeUpdate(sql);
				System.out.println("insert 실행");
			}	
			
			//Delete 실행
			else if(funcName.equals("DELETE")){
				Scanner scan = new Scanner(System.in);
				System.out.println("몇 번쨰 키값을 삭제할래?");
				String musicKey = scan.nextLine();
				String sql = "DELETE FROM toktok WHERE musicKey=" + musicKey + ";";
				statement.executeUpdate(sql);
				System.out.println(musicKey + " 번쨰 값이 삭제되었습니다.");
			}*/
			
			//select 실행
			//else if(funcName.equals("SELECT")){//
				String sql = "SELECT * FROM toktok WHERE musicKey=" + musicNum + ";";
				resultSet = statement.executeQuery(sql);
				while(resultSet.next()){
					String musicKey = resultSet.getString("musicKey");
					String music = resultSet.getString("music");
					String singer = resultSet.getString("singer");
					retuVal = music + "66" + singer;
					
				}
		//	}//
			
			
		}catch (ClassNotFoundException e){
			System.out.println("[로드 오류]\n" + e.getStackTrace());
		}
		catch (SQLException e){
			System.out.println("[연결 오류]\n" +  e.getStackTrace());
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	
	public String getRetuVal() {
		return retuVal;
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
			System.out.println("[닫기 오류]\n" +  e.getStackTrace());
		}
	}
}
