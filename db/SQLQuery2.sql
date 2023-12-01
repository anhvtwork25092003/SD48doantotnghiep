SELECT name
FROM sys.indexes
WHERE object_id = OBJECT_ID('khachhang') AND is_unique_constraint = 1;
ALTER TABLE khachhang
DROP CONSTRAINT UQ__KhachHan__CA1930A51981DA86;
insert into KhachHang(HoVaTen, SDT)
values('vu tuan anh','0979198789')
select* from donhang
select* from KhachHang