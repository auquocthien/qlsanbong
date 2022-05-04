package v1_qlsb;
import java.util.Scanner;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Khach {
	private String maKhach;
	private String hoten;
	private String sdt;
	private String namSinh;
	private String maSan;
	private String tgBDDa;
	private int thoigianDa;
	private double thanhTien;
	
	public Khach() {
		maKhach = new String();
		hoten = new String();
		sdt = new String();
		namSinh = new String();
		maSan = new String();
		tgBDDa = new String();
		thoigianDa = 0;
		thanhTien = 0.0; 
	}
	public static final String CreateKey(String str, String table) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost/qlsanbongV_1?" + "user=root");
		stmt = conn.createStatement();
		rs = stmt.executeQuery("select count(*) as sldong from " + table);
		String key = "";
		while(rs.next()) {
			if(rs.getInt("sldong") == 0) {
				key = str + 1;
			}
			else if(rs.getInt("sldong") != 0) {
				key = str + (rs.getInt("sldong") + 1);
			}
		}
		return key;
	}
	public static final String nhapNgay() {
		Scanner sc = new Scanner(System.in);
		System.out.print("nhap nam: ");
		int nam = sc.nextInt();
		System.out.print("nhap thang: ");
		int thang = sc.nextInt();
		System.out.print("nhap ngay: ");
		int ngay = sc.nextInt();
		System.out.print("nhap gio: ");
		int h = sc.nextInt();
		System.out.print("nhap phut: ");
		int m = sc.nextInt();
		int s = 0;
		return nam + "-" + thang + "-" + ngay + " " + h + ":" + m + ":" + s;
	}
	public static final String nhapNgayDK() {
		Scanner sc = new Scanner(System.in);
		System.out.print("nhap nam: ");
		int nam = sc.nextInt();
		System.out.print("nhap thang: ");
		int thang = sc.nextInt();
		System.out.print("nhap ngay: ");
		int ngay = sc.nextInt();
		int h = 0;
		int m = 0;
		int s = 0;
		return nam + "-" + thang + "-" + ngay + " " + h + ":" + m + ":" + s;
	}
	public void taoLichDa() throws SQLException {
			Scanner sc = new Scanner(System.in);
			maKhach = CreateKey("KH", "Khach");
			System.out.print("chon san: ");
			maSan = sc.next();
			System.out.println("thoi gian bat dau da: ");
			tgBDDa = nhapNgay();
			System.out.print("thoi gian da: ");
			thoigianDa = sc.nextInt();
			sc.close();
		try {
			Connection conn = null;
			CallableStatement stmt = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost/qlsanbongV_1?" + "user=root");
			stmt = conn.prepareCall("{call TAO_LICH_DAT(?,?,?,?,?,?)}");
			stmt.setString(1, maKhach);
			stmt.setString(2, hoten);
			stmt.setString(3, sdt);
			stmt.setString(4, maSan);
			stmt.setString(5, tgBDDa);
			stmt.setInt(6, thoigianDa);
			stmt.execute();
			System.out.print("thanh cong");
		}
		catch(SQLException ex){
			System.out.print("loi: " + ex);
			
		}
	}
	public void nhapTTK() throws SQLException{
		Connection conn = null;
		CallableStatement stmt = null;
		Scanner sc = new Scanner(System.in);
		maKhach = CreateKey("KH", "Khach");
		System.out.print("nhap ten: ");
		hoten = sc.next();
		System.out.print("nhap so dien thoai: ");
		sdt = sc.next();
		conn = DriverManager.getConnection("jdbc:mysql://localhost/qlsanbongV_1?" + "user=root");
		stmt = conn.prepareCall("{call THEM_KH(?,?,?)}");
		stmt.setString(1, maKhach);
		stmt.setString(2, hoten);
		stmt.setString(3, sdt);
		stmt.execute();
	}
	public void nhapTTKDK() throws SQLException{
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/qlsanbongV_1?" + "user=root");
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		Scanner sc = new Scanner(System.in);
		System.out.print("nhap ho ten: ");
		hoten = sc.next();
		System.out.println("nhap nam sinh: ");
		namSinh = nhapNgayDK();
	}
	public String getHoten(){
		return hoten;
	}
	public String getNamsinh(){
		return namSinh;
	}
	public void menu_KH() throws SQLException{
		San san = new San();
		Scanner sc = new Scanner(System.in);
		int choice = 0;
		while(true){
			System.out.println("chao mung " + hoten + " den voi he thong quan li san bong");
			System.out.println("1: xem lich san");
			System.out.println("2: xem san");
			System.out.println("3: tao lich dat san");
			System.out.print("lua chon cua ban: ");
			choice = sc.nextInt();
			switch(choice){
				case 1:
					san.lich_da_cua_san();
					break;
				case 2:
					san.dsSan();
					break;
				case 3:
					taoLichDa();
					break;
			}
		}

	}
}

