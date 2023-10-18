INSERT INTO Sach (MaSach, TenSach, MoTa, TrangThai, SoLuongTonKho, GiaBan, LinkAnh1, LinkAnh2, LinkAnh3, LinkAnh4, LinkAnh5, MaVach)
VALUES 
('MS003', 'Sách Tiểu Thuyết A', 'Mô tả sách A', 1, 50, 29.99, 'sachA_1.jpg', 'sachA_2.jpg', 'sachA_3.jpg', 'sachA_4.jpg', 'sachA_5.jpg', '9876543210'),
('MS004', 'Sách Khoa Học B', 'Mô tả sách B', 1, 60, 19.99, 'sachB_1.jpg', 'sachB_2.jpg', 'sachB_3.jpg', 'sachB_4.jpg', 'sachB_5.jpg', '1234567890'),
('MS005', 'Sách Học Lập Trình C', 'Mô tả sách C', 1, 70, 14.99, 'sachC_1.jpg', 'sachC_2.jpg', 'sachC_3.jpg', 'sachC_4.jpg', 'sachC_5.jpg', '5555555555'),
('MS006', 'Sách Lịch Sử D', 'Mô tả sách D', 1, 30, 9.99, 'sachD_1.jpg', 'sachD_2.jpg', 'sachD_3.jpg', 'sachD_4.jpg', 'sachD_5.jpg', '4444444444'),
('MS007', 'Sách Văn Học E', 'Mô tả sách E', 1, 80, 12.99, 'sachE_1.jpg', 'sachE_2.jpg', 'sachE_3.jpg', 'sachE_4.jpg', 'sachE_5.jpg', '1111111111');

INSERT INTO TacGia (Email, HoVaTen, LinkAnhTacGia, TrangThai, SDT)
VALUES 
('author3@email.com', 'Tác Giả A', 'tacgiaA.jpg', 1, '111-222-3333'),
('author4@email.com', 'Tác Giả B', 'tacgiaB.jpg', 1, '222-333-4444'),
('author5@email.com', 'Tác Giả C', 'tacgiaC.jpg', 1, '333-444-5555'),
('author6@email.com', 'Tác Giả D', 'tacgiaD.jpg', 1, '444-555-6666'),
('author7@email.com', 'Tác Giả E', 'tacgiaE.jpg', 1, '555-666-7777');
INSERT INTO TheLoai (TenTheLoai, MoTa, TrangThai)
VALUES
('Kỹ Năng Sống', 'Sách về phát triển kỹ năng', 'Active'),
('Lãnh Đạo', 'Sách về nghệ thuật lãnh đạo', 'Active'),
('Khoa Học', 'Sách khoa học tự nhiên', 'Active'),
('Lịch Sử', 'Sách lịch sử thế giới', 'Active'),
('Văn Học', 'Sách văn học kinh điển', 'Active');
