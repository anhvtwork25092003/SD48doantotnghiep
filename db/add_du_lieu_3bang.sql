INSERT INTO Sach (MaSach, TenSach, MoTa, TrangThai, SoLuongTonKho, GiaBan, LinkAnh1, LinkAnh2, LinkAnh3, LinkAnh4, LinkAnh5, MaVach)
VALUES
/*
('MS003', 'Sách Tiểu Thuyết A', 'Mô tả sách A', 1, 50, 29.99, 'sachA_1.jpg', 'sachA_2.jpg', 'sachA_3.jpg', 'sachA_4.jpg', 'sachA_5.jpg', '9876543210'),
('MS004', 'Sách Khoa Học B', 'Mô tả sách B', 1, 60, 19.99, 'sachB_1.jpg', 'sachB_2.jpg', 'sachB_3.jpg', 'sachB_4.jpg', 'sachB_5.jpg', '1234567890'),
('MS005', 'Sách Học Lập Trình C', 'Mô tả sách C', 1, 70, 14.99, 'sachC_1.jpg', 'sachC_2.jpg', 'sachC_3.jpg', 'sachC_4.jpg', 'sachC_5.jpg', '5555555555'),
('MS006', 'Sách Lịch Sử D', 'Mô tả sách D', 1, 30, 9.99, 'sachD_1.jpg', 'sachD_2.jpg', 'sachD_3.jpg', 'sachD_4.jpg', 'sachD_5.jpg', '4444444444'),
('MS007', 'Sách Văn Học E', 'Mô tả sách E', 1, 80, 12.99, 'sachE_1.jpg', 'sachE_2.jpg', 'sachE_3.jpg', 'sachE_4.jpg', 'sachE_5.jpg', '1111111111');
*/
('MS008', N'Sách Tiểu Thuyết1 ', 'Mô tả sách 1', 1, 50, 29.99, '/image/anhsanpham/sanpham1.jpg', '/image/anhsanpham/sanpham1.jpg', '/image/anhsanpham/sanpham1.jpg', '/image/anhsanpham/sanpham1.jpg', '/image/anhsanpham/sanpham5.jpg', '9876543210'),
('MS009', N'Sách Khoa Học 2', 'Mô tả sách 2', 1, 60, 19.99, '/image/anhsanpham/sanpham1.jpg', '/image/anhsanpham/sanpham2.jpg', '/image/anhsanpham/sanpham3.jpg', '/image/anhsanpham/sanpham4.jpg', '/image/anhsanpham/sanpham5.jpg', '1234567890'),
('MS0010', N'Sách Học Lập Trình 3', 'Mô tả sách 3', 1, 70, 14.99, '/image/anhsanpham/sanpham1.jpg', '/image/anhsanpham/sanpham2.jpg', '/image/anhsanpham/sanpham3.jpg', '/image/anhsanpham/sanpham4.jpg', '/image/anhsanpham/sanpham5.jpg', '5555555555'),
('MS0011', N'Sách Lịch Sử 4', 'Mô tả sách 4', 1, 30, 9.99, '/image/anhsanpham/sanpham1.jpg', '/image/anhsanpham/sanpham2.jpg', '/image/anhsanpham/sanpham3.jpg', '/image/anhsanpham/sanpham4.jpg', '/image/anhsanpham/sanpham5.jpg', '4444444444'),
('MS0012', N'Sách Văn Học 5', 'Mô tả sách 5', 1, 80, 12.99, '/image/anhsanpham/sanpham1.jpg', '/image/anhsanpham/sanpham2.jpg', '/image/anhsanpham/sanpham3.jpg', '/image/anhsanpham/sanpham4.jpg', '/image/anhsanpham/sanpham5.jpg', '1111111111');


INSERT INTO TacGia (Email, HoVaTen, LinkAnhTacGia, TrangThai, SDT)
VALUES 
('author3@email.com', 'Tác Giả A', 'tacgiaA.jpg', 1, '111-222-3333'),
('author4@email.com', 'Tác Giả B', 'tacgiaB.jpg', 1, '222-333-4444'),
('author5@email.com', 'Tác Giả C', 'tacgiaC.jpg', 1, '333-444-5555'),
('author6@email.com', 'Tác Giả D', 'tacgiaD.jpg', 1, '444-555-6666'),
('author7@email.com', 'Tác Giả E', 'tacgiaE.jpg', 1, '555-666-7777');
INSERT INTO TheLoai (TenTheLoai, MoTa, TrangThai)
VALUES
('Kỹ Năng Sống', 'Sách về phát triển kỹ năng', 1),
('Lãnh Đạo', 'Sách về nghệ thuật lãnh đạo', 1),
('Khoa Học', 'Sách khoa học tự nhiên', 1),
('Lịch Sử', 'Sách lịch sử thế giới', 1),
('Văn Học', 'Sách văn học kinh điển', 1);
-- insert sachtheloai
INSERT INTO sachtheloai ( IdSach, IdTheLoai)
VALUES
(1, 6),
(1, 7),
(2, 6),
(3, 6),
(4, 6),
(5, 6),
(5, 7)

-- insert sachtacgia
INSERT INTO SachTacGia( IdSach, IdTacGia)
VALUES
(1,1),
(1, 2),
(2, 3),
(3, 4),
(4, 5),
(5, 1),
(5, 5)



INSERT INTO GioHang DEFAULT VALUES;
INSERT INTO GioHang DEFAULT VALUES;
INSERT INTO GioHang DEFAULT VALUES;
INSERT INTO GioHang DEFAULT VALUES;
INSERT INTO GioHang DEFAULT VALUES;
INSERT INTO GioHang DEFAULT VALUES;

select * from GioHang

-- Dữ liệu cho bảng KhachHang
INSERT INTO KhachHang (IdGioHang, MaKhachHang, HoVaTen, SDT, NgaySinh, TrangThai, MatKhau, Email, LoaiKhachHang, GioiTinh)
VALUES
(1, 'KH001', N'Nguyễn Văn A', '0123456789', '1990-05-15', 1, 'password1', 'nguyenvana@email.com', N'Loại 1', 1),
(2, 'KH002', N'Trần Thị B', '0987654321', '1985-08-20', 1, 'password2', 'tranthib@email.com', N'Loại 2', 2),
(3, 'KH003', N'Phạm Văn C', '0369841572', '2000-02-10', 1, 'password3', 'phamvanc@email.com', N'Loại 1', 1),
(4, 'KH004', N'Lê Thị D', '0765432198', '1998-11-30', 1, 'password4', 'lethid@email.com', N'Loại 3', 2),
(5, 'KH005', N'Trương Văn E', '0112233445', '1993-07-25', 1, 'password5', 'truongvane@email.com', N'Loại 2', 1),
(6, 'KH006', N'Ngô Thị F', '0928374651', '1995-04-18', 1, 'password6', 'ngothif@email.com', N'Loại 1', 2);
select * from KhachHang


-- Dữ liệu cho bảng DiaChi
INSERT INTO DiaChi (IdKhachHang, TinhThanhPho, HuyenQuan, XaPhuong, DiaChiCuthe)
VALUES
(14, N'TP Hồ Chí Minh', N'Quận 1', N'Phường Bến Nghé', N'123 Đường ABC'),
(15, N'TP Hà Nội', N'Quận Hoàn Kiếm', N'Phường Cửa Đông', N'456 Đường XYZ'),
(16, N'TP Đà Nẵng', N'Quận Sơn Trà', N'Phường Mân Thái', N'789 Đường LMN'),
(17, N'TP Cần Thơ', N'Quận Ninh Kiều', N'Phường An Khánh', N'101 Đường PQR'),
(18, N'TP Hải Phòng', N'Quận Lê Chân', N'Phường Đằng Giang', N'202 Đường UVW'),
(19, N'TP Hồ Chí Minh', N'Quận 7', N'Phường Tân Thuận Đông', N'303 Đường DEF');






