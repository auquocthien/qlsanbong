package v1_qlsb;
import java.util.Scanner;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class San {
	public void dsSan() throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost/qlsanbongV_1?" + "user=root");
		stmt = conn.createStatement();
		rs = stmt.executeQuery("select * from San");
		while(rs.next()){
			System.out.print("ma san: " + rs.getString("maSan") + "\t");
			System.out.print("ten san: " + rs.getString("tenSan") + "\t");
			System.out.print("loai san: " + rs.getString("loaiSan") + "\t");
			System.out.print("tien san: " + rs.getInt("tienSan") + "\n");
		}
	}
	public void lich_da_cua_san() throws SQLException{
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost/qlsanbongV_1?" + "user=root");
		System.out.print("nhap ten san can tim: ");
		String tenSan = sc.nextLine();
		stmt = conn.prepareCall("{call LICH_DA(?)}");
		stmt.setString(1, tenSan);
		rs = stmt.executeQuery();
		while(rs.next()){
			System.out.print("ma san: " + rs.getString("maSan") + "\n");
			System.out.print("thoi gian da: " + rs.getString("tgBDDa") + "\n");
			System.out.print("thoi gian da: " + rs.getString("thoigianDa") + "\n");
		}
	}
}
