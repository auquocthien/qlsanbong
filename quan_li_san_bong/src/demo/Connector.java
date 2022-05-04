package demo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Connector {
	public static void main(String args[]) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost/qlsanbong?" + "user=root");
		stmt = conn.createStatement();
		rs = stmt.executeQuery("select count(*) as sldong from San");
		System.out.print("ket noi thanh cong \n");
		rs = stmt.getResultSet();
		while(rs.next()) {
			
		}
		
	}
}
