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
    maKhach char(6),
    maTV char(6),
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
alter table HoaDon add foreign key (maKhach) references Khach(maKhach);
alter table HoaDon add foreign key (maTV) references Thanhvien(maTV);
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
create procedure TINH_TIEN(in thoigianDa int, in maSan char(6), out thanhtien double)
begin
	set thanhtien = thoigianDa * (select tienSan from San where San.maSan = maSan);
end/
drop procedure TINH_TIEN/
#them lich da
create procedure TAO_LICH_DAT(in maND char(6), in hoten varchar(50), in sdt varchar(10), in tenSan char(6), in tgBDDa datetime, in thoigianDa int)
begin
	set @maSan = (select maSan from San where San.tenSan = tenSan);
    call TINH_TIEN(thoigianDa, @maSan, @tt);
    if maND like 'TV%' then
		insert into HoaDon(maTV, hoten, sdt, maSan, tgBDDa, thoigianDa, thanhtien)
			values(maND, hoten, sdt, @maSan, tgBDDa, thoigianDa, @tt);
	else
		insert into HoaDon(maKhach, hoten, sdt, maSan, tgBDDa, thoigianDa, thanhtien)
			values(maND, hoten, sdt, @maSan, tgBDDa, thoigianDa, @tt);
	end if;
end/
drop procedure TAO_LICH_DAT/

#lich da cua san
create procedure LICH_DA(in tenSan char(6))
begin
	select maSan, tgBDDa, thoigianDa
		from HoaDon
        where maSan = (select maSan from San where tenSan = San.tenSan);
end/
drop procedure LICH_DA/

#them tai khoan
create procedure THEM_TK(in sdtDK varchar(10), in mk varchar(10))
begin
	insert into QLTaiKhoan values(sdtDK, mk);
end/

#lay ma thanh vien
create procedure LAY_MATV(in sdtDK char(10), out maTV char(6))
begin
	set maTV = (select maTV from ThanhVien where ThanhVien.sdtDK = sdtDK);
end/

#kiem tra so dien thoai
create procedure KTR_sdt(in sdt varchar(10))
begin
	#select sdtDK from QLTaiKhoan where QLTaiKhoan.sdtDk = sdt; 
    select count(*) as slsdt from QLTaiKhoan where QLTaiKhoan.sdtDK = sdt;
end/
drop procedure KTR_sdt/

#kiem tra dang nhap hop le
create procedure KTR_DN(in sdtDK char(10), in mk char(10))
begin
	select count(*) as hopLe from QLTaiKhoan where QLTaiKhoan.sdtDK = sdtDK and QLTaiKhoan.mk = mk;
end/
drop procedure KTR_DN/
call KTR_DN('0931096574', 'au1234de')/
#truy van bang
select * from QLTaiKhoan/
select * from Thanhvien/
select * from Khach/
select * from HoaDon/	
delete from ThanhVien where maTV = "TV2"/
delete from QLTaiKhoan where sdtDK like "0867%"/
call THEM_TV("TV2", "thien", "0867923814", "2001-01-21", "au1234de")/ 
call KTR_sdt("0867923813")/
call LICH_DA('San 1')/
call TAO_LICH_DAT('TV1', 'thienchanrau', '0355806617', 'San 1', '2022-01-21', 90)/
SET @SUM = 0/
CALL TINH_TIEN(90, 'San1', @SUM)/
SELECT @SUM/
select maSan from San where San.tenSan = 'San 2'/