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
