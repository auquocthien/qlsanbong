package v1_qlsb;
import v1_qlsb.mainSQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;
import java.sql.PreparedStatement;


import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.sql.DriverManager;
// import static v1_qlsb.Khach.CreateKey;

public class ThanhVien extends Khach {
    private String maTV;
    private String sdtDK;
    private boolean loaiTV;
    private double tileCK;
    private String mk;
    public ThanhVien(){
        super();
        String maTV = new String();
        String sdtDK = new String();
        loaiTV = false;
        tileCK = 0.0;
    }
    public int ktr_sdt(String sdtDK) throws SQLException{
        Connection conn = null;
        CallableStatement stmt = null;
        int check = 0;
        conn = DriverManager.getConnection("jdbc:mysql://localhost/qlsanbongV_1?" + "user=root");
        stmt = conn.prepareCall("{call KTR_sdt(?)}");
        stmt.setString(1, sdtDK);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            if(rs.getInt("slsdt") == 0){
                check = 1;
            }
        }
        return check;
    }
    public void nhapTTDK() throws SQLException{
        Scanner sc = new Scanner(System.in);
        Connection conn = null;
        CallableStatement cstmt = null; 
        conn = DriverManager.getConnection("jdbc:mysql://localhost/qlsanbongV_1?" + "user=root");
        super.nhapTTKDK();
        System.out.print("nhap so dien thoai: ");
        sdtDK = sc.next();
        while(ktr_sdt(sdtDK) == 0){
            System.out.println("so dien thoai da dang ki tai khoan");
            System.out.print("nhap so dien thoai: ");
            sdtDK = sc.next();
        }
        // do{
        //     System.out.println("so dien thoai da dang ki tai khoan");
        //     System.out.print("nhap so dien thoai: ");
        //     sdtDK = sc.next();
        // }
        // while(ktr_sdt(sdtDK) == 0);
        System.out.print("nhap mat khau: ");
        mk = sc.next();
        maTV = CreateKey("TV", "Thanhvien");
        themTK(sdtDK, mk);
        if(sdtDK.length() == 10){
            cstmt = conn.prepareCall("{call THEM_TV(?, ?, ?, ?)}");
            cstmt.setString(1, maTV);
            cstmt.setString(2, getHoten());
            cstmt.setString(3, sdtDK);
            cstmt.setString(4, getNamsinh());
            cstmt.execute();
        }
        System.out.println("chuc mung ban da dang ki thanh cong");
    }
    public void themTK(String sdt, String mk) throws SQLException{
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/qlsanbongV_1?" + "user=root");
        CallableStatement stmt = conn.prepareCall("{call THEM_TK(?,?)}");
        stmt.setString(1, sdt);
        stmt.setString(2, mk);
        stmt.execute();
    }
    public void dangNhap() throws SQLException{
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/qlsanbongV_1?" + "user=root");
        ResultSet rs = null;
        CallableStatement stmt = null;
        int hopLe = 0;
        do{
            Scanner sc = new Scanner(System.in);
            System.out.print("nhap so dien thoai dang ki: "); 
            sdtDK = sc.next();
            System.out.print("nhap ma khau: ");
            String mk = sc.next();
            stmt = conn.prepareCall("{call KTR_DN(?, ?)}");
            stmt.setString(1, sdtDK);
            stmt.setString(2, mk);
            rs = stmt.executeQuery();
            while(rs.next()){
               hopLe = rs.getInt("hopLe");
            }
        }
        while(hopLe == 0);
    }
    public void xem_lich_su(String maTV) throws SQLException{
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/qlsanbongV_1?" + "user=root");
        PreparedStatement stmt = conn.prepareStatement("select * from HoaDon where HoaDon.maTV = ?");
        stmt.setString(1, maTV);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            System.out.println("ma san: " + rs.getString("maSan"));
            System.out.println("thoi gian da: " + rs.getString("tgBDDa"));
            System.out.println("thoi luong da: " + rs.getInt("thoigianDa"));
        }
    }
    public double xem_ti_le_CK(String maTV) throws SQLException{
        double CK = 0;
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/qlsanbongV_1?" + "user=root");
        PreparedStatement stmt = conn.prepareStatement("select tileCK from Thanhvien where ThanhVien.maTV = ?");
        stmt.setString(1, maTV);
        ResultSet rs =stmt.executeQuery();
        while(rs.next()){
            CK = rs.getDouble("tileCK");
        }
        return CK;
    }
    public void cap_nhat_ti_le_CK(String maTV) throws SQLException{
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/qlsanbongV_1?" + "user=root");
        PreparedStatement stmt = conn.prepareStatement("update ThanhVien set tileCK = tileCK + 0.1 where ThanhVien.maTV = ?");
        stmt.setString(1, maTV);
        stmt.execute();
    }
    public String getMaTV(String sdtDK) throws SQLException{
		String mtv = "";
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/qlsanbongV_1?" + "user=root");
		CallableStatement stmt = conn.prepareCall("{call LAY_MATV(?,?)}");
		stmt.setString(1, sdtDK);
		stmt.registerOutParameter(2, Types.CHAR);
		stmt.executeQuery();
		mtv = stmt.getString(2);
		return mtv;
	}
    public void menu_TV() throws SQLException{
        String maTV = getMaTV(sdtDK);
        Scanner sc = new Scanner(System.in);
        San san = new San();
        while(true){
            System.out.println("1: xem lich su dat san");
            System.out.println("2: xem ti le chiet khau");
            System.out.println("3: xem san");
            System.out.println("4: xem lich da cua san");
            System.out.println("5: dat san");
            System.out.println("6: dang xuat");
            System.out.print("lua chon cua ban: ");
            int choice = sc.nextInt();
            switch(choice){
                case 1:
                    xem_lich_su(maTV);
                    break;
                case 2:
                    System.out.println("ti le chiet khau: " + xem_ti_le_CK(maTV));
                    break;
                case 3:
                    san.dsSan();
                    break;
                case 4:
                    san.lich_da_cua_san();
                    break;
                case 5:
                    taoLichDa(maTV);
                    cap_nhat_ti_le_CK(maTV);
                case 6:
                    break;
            }
            if(choice == 6){
                break;
            }
        }
    }
}
