package v1_qlsb;

import java.sql.SQLException;
import java.util.Scanner;

public class mainSQL {
	public void menuStartup() {
		System.out.print("");
	}
	public static void main(String args[]) throws SQLException {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		San san = new San();
		Khach kh = new Khach();
		ThanhVien tv = new ThanhVien();
		while(true) {
			System.out.print("chao mung den voi he thong quan li san bong\n");
			System.out.print("1: su dung voi tu cach khach\n");
			System.out.print("2: dang ki tai khoan\n");
			System.out.print("3: dang nhap\n");
			System.out.print("4: lich da cua san san\n");
			System.out.print("lua chon cua ban: ");
			int choice = sc.nextInt();
			if(choice == 1){
				kh.nhapTTK();
				kh.menu_KH();
			}
			if(choice == 2){
				tv.nhapTTDK();
				tv.menu_TV();
			}
			if(choice == 3){
				tv.dangNhap();
				tv.menu_TV();
			}
			if(choice == 4){
				san.dsSan();
			}
		}
		// System.out.print(tv.getMaTV("0867923814"));
	}

}
