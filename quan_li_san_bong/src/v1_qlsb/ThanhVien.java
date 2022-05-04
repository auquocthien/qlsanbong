package v1_qlsb;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;



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
        
        do{
            System.out.println("so dien thoai da dang ki tai khoan");
            System.out.print("nhap so dien thoai: ");
            sdtDK = sc.next();
        } while(ktr_sdt(sdtDK) == 0);

        // if(ktr_sdt(sdtDK) == 0){
        //     System.out.println("so dien thoai da dang ki tai khoan");
        //     System.out.print("nhap so dien thoai: ");
        //     sdtDK = sc.next();
        // }
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
    }
    public void themTK(String sdt, String mk) throws SQLException{
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/qlsanbongV_1?" + "user=root");
        CallableStatement stmt = conn.prepareCall("{call THEM_TK(?,?)}");
        stmt.setString(1, sdt);
        stmt.setString(2, mk);
        stmt.execute();
    }
}
