drop database qlsanbong;
create database qlsanbong;
use qlsanbong;
create table Khach (
	maKhach char(6) not null primary key,
	hoten varchar(50),
    sdt varchar(10)
    );

create table ThanhVien (
	maTV char(6) not null primary key,
    hoten varchar(50),
    sdt varchar(10),
    namsinh date,
    loaiTV boolean,
    tileCK float
    );
    
create table LDSThanhVien ( -- lich dat san cua thanh vien
	STT int primary key auto_increment,
    maTV char(6),
	hoten varchar(50),
    sdt varchar(10),
    maSan char(6),
    tgBDDa datetime,
    thoigianDa int,
    thanhtien float
    );
    
create table LDSKhach ( -- lich dat san cua khach ( chua co tai khoan)
	STT int primary key auto_increment,
    maKhach char(6),
	hoten varchar(50),
    sdt varchar(10),
    maSan char(6),
    tgBDDa datetime,
    thoigianDa int,
    thanhtien float
    );
 -- 2 bang khac nhau nhung luc in ra se chung 1 bang
 
create table San (
	maSan char(6) not null primary key,
    tenSan char(6),
    loaiSan char(6),
    tienSan int -- 1 tieng
    );
    
create table QLTaiKhoan (
	maTV char(6) not null primary key,
    sdt varchar(10),
    tenDN varchar(10),
    mk varchar(10)
    );

alter table LDSKhach add foreign key (maKhach) references Khach(maKhach);
alter table LDSThanhVien add foreign key (maTV) references ThanhVien(maTV);
alter table LDSKhach add foreign key (maSan) references San(maSan);
alter table LDSThanhVien add foreign key (maSan) references San(maSan);
alter table QLTaiKhoan add foreign key (maTV) references ThanhVien(maTV);

insert into san values("San1", "San 1", "5", 130000);
insert into san values("San2", "San 2", "5", 130000);
insert into san values("San3", "San 3", "5", 130000);
insert into san values("San4", "San 4", "7", 150000);

delimiter /
-- Them tai khoan
create procedure THEM_TK(in maTK char(6), in sdt varchar(10), in tenDN varchar(10), in mk varchar(10))
begin
	if not exists (select sdt, tenDN from QLTaiKhoan where sdt = QLTaiKhoan.sdt and tenDN = QLTaiKhoan.tenDN) then
		insert into QLTaiKhoan
		values (maTK, sdt, tenDN, mk);
	end if;
end/
drop procedure THEM_TK/
select * from QLTaiKhoan/  
call THEM_TK("TV001", "01222333", "thien", "aaaaaa")/
-- Them thanh vien
create procedure THEM_TV(in maTV char(6), in hoten varchar(50), in sdt char(10), in namsinh date, in loaiTV boolean, in tileCK float)
begin
	if not exists (select sdt from QLTaiKhoan where sdt = QLTaiKhoan.sdt) then
		insert into ThanhVien
        values (maTV, hoten, sdt, namsinh, loaiTV, tileCK);
	end if;
end/
drop procedure THEM_TV/
select * from ThanhVien/ 
call THEM_TV("TV006", "thien", "01233445", "2001-01-21", true, 0.1)/
-- them lich san thanh vien
create procedure THEM_LDSTV(in hoten varchar(5), in sdt char(10), in maSan char(6), in tgBDDa datetime, in thoigianDa int)
begin
	set @maTV = (select maTK from QLTaiKhoan where sdt = QLTaiKhoan.sdt);
    -- insert into LDSThanhVien(maTV) values (maTV); 
    insert into LDSThanhVien(maTV, hoten, sdt, maSan, tgBDDa, thoigianDa)
    values(@maTV, hoten, sdt, maSan, tgBDDa, thoigianDa);
end/
-- them khach
create procedure THEM_KH(in maKhach char(6), in hoten varchar(50), in sdt char(10))
begin
	insert into Khach values(maKhach, hoten, sdt);
end/
drop procedure THEM_KH/
select * from Khach/
delete from Khach where maKhach like "KH%"/
call THEM_KH("KH001", "2001-01-21")/
-- them lich dat san khach
create procedure THEM_LDSK(in maKhach char(6), in hoten varchar(50), in sdt char(10), in maSan char(6), in tgBDDa datetime, in thoigianDa int)
begin
	insert into LDSKhach(maKhach, hoten, sdt, maSan, tgBDDa, thoigianDa)
    values (maKhach, hoten, sdt, maSan, tgBDDa, thoigianDa);
end/
select count(*) from LDSKhach/
select * from LDSKhach/

create procedure LD_CUA_SAN(in tenSan char(6))
begin
	select LDSKhach.maSan;
end/