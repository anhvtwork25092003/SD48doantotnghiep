select* from khachhang
alter table diachi
add idTinh int

alter table diachi
add idHuyen int

alter table diachi
add idXa int


-- thêm idkhachhang vao bảng giỏ hàng
alter table giohang 
add idKhachHang int

-- xóa cột idgiohang ở bảng khachhnag
ALTER TABLE khachhang
DROP CONSTRAINT FK_KhachHang_GioHang;
ALTER TABLE KhachHang
DROP CONSTRAINT UQ__KhachHan__CCE77A1EFF8D8F34;
alter table khachhang
drop column idGiohang
-- thêm lại mối quân hệ giữa giỏ hàng và khách hàng thông quá khóa ngoại 
-- Tạo khóa ngoại
ALTER TABLE GioHang
ADD CONSTRAINT FK_GioHang_KhachHang
FOREIGN KEY (idKhachhang)
REFERENCES KhachHang(idKhachhang);

-- sửa db ngày 19/11/2023
alter table donhangchitiet
ADD CONSTRAINT FK_DonHangChiTiet_Sach
FOREIGN KEY (IdSach)
REFERENCES Sach(idSach);
-- sửa db ngày 21-11
ALTER TABLE DiaChi
add  tenNguoiNhan nvarchar(100)
ALTER TABLE DiaChi
add  sdtNguoiNhanHang nvarchar(100)
select* from PhuongThucThanhToan
-- sửa db ngày 22
alter table donhang
add trangThaiThanhToan int

--sửa db tiếp
alter table DonHangChiTiet
ADD CONSTRAINT FK_DonHangChiTiet_KhuyenMai
FOREIGN KEY (idKhuyenMai)
REFERENCES KhuyenMai(idKhuyenMai);

-- sua ngay 23
alter table donhang
drop column tongtienhanggoc

alter table donhang
drop column tongtienkhuyenmai

alter table donhang
drop column tongtiencanthanhtoan

-- sửa tiếp
alter table donhangchitiet
alter column phantramgiam float