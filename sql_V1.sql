create database qlsanbongV_1;
use qlsanbongV_1;
drop database qlsanbongv_1;
#tao bang
create table Khach (
	maKhach char(6) not null primary key,
	hoten varchar(50),
    sdt varchar(10)
    );

create table ThanhVien (
	maTV char(6) not null primary key,
    hoten varchar(50),
    sdtDK varchar(10),
    namsinh date,
    loaiTV boolean,
    tileCK float
    );
drop table ThanhVien;
create table HoaDon ( -- lich dat san cua thanh vien
	STT int primary key auto_increment,
    maND char(6),
	hoten varchar(50),
    sdt varchar(10),
    maSan char(6),
    tgBDDa datetime,
    thoigianDa int,
    thanhtien float
    );
    
create table San (
	maSan char(6) not null primary key,
    tenSan char(6),
    loaiSan char(6),
    tienSan int -- 1 tieng
    );
    
create table QLTaiKhoan (
    sdtDK varchar(10) not null primary key,
    mk varchar(10)
    );
    
#tao rang buoc
alter table HoaDon add foreign key (maND) references Khach(maKhach);
alter table HoaDon add foreign key (maND) references Thanhvien(maTV);
alter table HoaDon add foreign key (maSan) references San(maSan);
alter table ThanhVien add foreign key (sdtDK) references QLTaiKhoan(sdtDK);
alter table ThanhVien alter column loaiTV set default false;
alter table ThanhVien alter column tileCK set default 0.1;

#chen du lieu
insert into san values("San1", "San 1", "5", 130000);
insert into san values("San2", "San 2", "5", 130000);
insert into san values("San3", "San 3", "5", 130000);
insert into san values("San4", "San 4", "7", 150000);

insert into QLTaiKhoan values("0867923813", "au1234de");
#tao thu tuc

delimiter /
#them khach hang moi
create procedure THEM_KH(in maKhach char(6), in hoten varchar(50), in sdt varchar(10))
begin
	insert into Khach values (maKhach, hoten, sdt);
end/
drop procedure THEM_KHACh/
#them thanh vien
create procedure THEM_TV(in maTV char(6), in hoten varchar(50), in sdtDK varchar(10), in namsinh date)
begin
	insert into ThanhVien(maTV, hoten, sdtDK, namsinh) values(maTV, hoten, sdtDK, namsinh);
end/
drop procedure THEM_TV/
#tinh tien san
create procedure TINH_TIEN(in thoigianDA int, in maSan char(6), out thanhtien float)
begin
	set thanhtien = thoigianDa * (select tienSan from San where San.maSan = maSan);
end/

#them lich da
create procedure TAO_LICH_DAT(in maND char(6), in hoten varchar(50), in sdt varchar(10), in maSan char(6), in tgBDDa datetime, in thoigianDa int, in thanhtien float)
begin
	set @tt = 0;
    call TINH_TIEN(thoigianDa, maSan, @tt);
	insert into HoaDon(maND, hoten, maSan, tgBDDa, thoigianDa, thanhtien)
		values(maND, hoten, sdt, maSan, tgBDDa, thoigianDam, @tt);
end/

#lich da cua san
create procedure LICH_DA(in tenSan char(6), in ngayda datetime)
begin
	select maSan, tgBDDa, thoigianDa
		from HoaDon
        where ngayDa = thoigianDa
			and maSan = (select maSan from San where tenSan = San.tenSan);
end/

#them tai khoan
create procedure THEM_TK(in sdtDK varchar(10), in mk varchar(10))
begin
	insert into QLTaiKhoan values(sdtDK, mk);
end/

#kiem tra so dien thoai
create procedure KTR_sdt(in sdt varchar(10))
begin
	#select sdtDK from QLTaiKhoan where QLTaiKhoan.sdtDk = sdt; 
    select count(*) as slsdt from QLTaiKhoan where QLTaiKhoan.sdtDK = sdt;
end/
drop procedure KTR_sdt/
#truy van bang
select * from QLTaiKhoan/
select * from Thanhvien/
select * from Khach/
delete from ThanhVien where maTV = "TV2"/
delete from QLTaiKhoan where sdtDK like "0867%"/
call THEM_TV("TV2", "thien", "0867923814", "2001-01-21", "au1234de")/ 
call KTR_sdt("0867923813")/