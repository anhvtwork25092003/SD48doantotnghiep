use SD48_CoSoDuLieuCuaHangSachAnhVT
go
-- Thêm khóa ngoại vào bảng DanhSachYeuThich
ALTER TABLE DanhSachYeuThich
ADD CONSTRAINT FK_DanhSachYeuThich_KhachHang
FOREIGN KEY (MaKhachHang)
REFERENCES KhachHang(IdKhachHang);
go
-- Thêm khóa ngoại vào bảng DanhSachYeuThich để tham chiếu đến bảng Sach (sử dụng MaSach)
ALTER TABLE DanhSachYeuThich
ADD CONSTRAINT FK_DanhSachYeuThich_Sach
FOREIGN KEY (MaSach)
REFERENCES Sach(IdSach);
go
-- Thêm khóa ngoại để liên kết bảng TroChuyen với bảng KhachHang
ALTER TABLE TroChuyen
ADD CONSTRAINT FK_TroChuyen_KhachHang
FOREIGN KEY (MaKhachHang)
REFERENCES KhachHang(IdKhachHang);
go
-- Thêm khóa ngoại để liên kết bảng TroChuyen với bảng NhanVien
ALTER TABLE TroChuyen
ADD CONSTRAINT FK_TroChuyen_NhanVien
FOREIGN KEY (MaNhanVien)
REFERENCES NhanVien(IdNhanVien);

go
-- Thêm khóa ngoại để liên kết bảng SachKhuyenMai với bảng KhuyenMai
ALTER TABLE SachKhuyenMai
ADD CONSTRAINT FK_SachKhuyenMai_KhuyenMai
FOREIGN KEY (IdKhuyenMai)
REFERENCES KhuyenMai(IdKhuyenMai);
go
-- Thêm khóa ngoại để liên kết bảng SachKhuyenMai với bảng Sach
ALTER TABLE SachKhuyenMai
ADD CONSTRAINT FK_SachKhuyenMai_Sach
FOREIGN KEY (MaSach)
REFERENCES Sach(MaSach);
go
ALTER TABLE SachTacGia
ADD CONSTRAINT FK_SachTacGia_TacGia
FOREIGN KEY (IdTacGia)
REFERENCES TacGia(IdTacGia);

go
ALTER TABLE SachTacGia
ADD CONSTRAINT FK_SachTacGia_Sach
FOREIGN KEY (MaSach)
REFERENCES Sach(MaSach);
go
-- Thêm khóa ngoại để liên kết bảng SachTheLoai với bảng Sach
ALTER TABLE SachTheLoai
ADD CONSTRAINT FK_SachTheLoai_Sach
FOREIGN KEY (MaSach)
REFERENCES Sach(MaSach);
go
-- Thêm khóa ngoại để liên kết bảng SachTheLoai với bảng TheLoai
ALTER TABLE SachTheLoai
ADD CONSTRAINT FK_SachTheLoai_TheLoai
FOREIGN KEY (IdTheLoai)
REFERENCES TheLoai(IdTheLoai);
go
-- Thêm khóa ngoại để liên kết bảng DonHangChiTiet với bảng DonHang
ALTER TABLE DonHangChiTiet
ADD CONSTRAINT FK_DonHangChiTiet_DonHang
FOREIGN KEY (MaDonHang)
REFERENCES DonHang(MaDonHang);
	go
	-- Thêm khóa ngoại để liên kết bảng DonHang với bảng KhachHang qua cột MaKhachHang
ALTER TABLE DonHang
ADD CONSTRAINT FK_DonHang_KhachHang
FOREIGN KEY (MaKhachHang)
REFERENCES KhachHang(MaKhachHang);
go
-- Thêm khóa ngoại để liên kết bảng DonHang với bảng PhuongThucThanhToan qua cột IdPhuongThucThanhToan (assumed, thay thế bằng tên bảng thật)
ALTER TABLE DonHang
ADD CONSTRAINT FK_DonHang_PhuongThucThanhToan
FOREIGN KEY (IdPhuongThucThanhToan)
REFERENCES PhuongThucThanhToan(IdPhuongThucThanhToan);
go
-- Thêm khóa ngoại để liên kết bảng DonHang với bảng ThongTinGiaoHang qua cột IdThongTinGiaoHang (assumed, thay thế bằng tên bảng thật)
ALTER TABLE DonHang
ADD CONSTRAINT FK_DonHang_ThongTinGiaoHang
FOREIGN KEY (IdThongTinGiaoHang)
REFERENCES ThongTinGiaoHang(IdThongTinGiaoHang);
go
-- Thêm khóa ngoại để liên kết bảng DonHang với bảng NhanVien qua cột MaNhanvien
ALTER TABLE DonHang
ADD CONSTRAINT FK_DonHang_NhanVien
FOREIGN KEY (MaNhanvien)
REFERENCES NhanVien(MaNhanVien);
go
-- Thêm khóa ngoại để liên kết bảng DonHang với bảng DonViVanChuyen qua cột IdDonViVanChuyen (assumed, thay thế bằng tên bảng thật)
ALTER TABLE DonHang
ADD CONSTRAINT FK_DonHang_DonViVanChuyen
FOREIGN KEY (IdDonViVanChuyen)
REFERENCES DonViVanChuyen(IdDonViVanChuyen);
go
-- danhgia_sach
ALTER TABLE DanhGia
ADD CONSTRAINT FK_DanhGia_Sach
FOREIGN KEY (MaSach)
REFERENCES Sach(MaSach);

go
-- danhgia-khachhang
-- Thêm khóa ngoại để liên kết bảng KhachHang với bảng GioHang
ALTER TABLE KhachHang
ADD CONSTRAINT FK_KhachHang_GioHang
FOREIGN KEY (IdGioHang)
REFERENCES GioHang(IdGioHang);
go
-- Thêm khóa ngoại để liên kết bảng GioHangChiTiet với bảng GioHang
ALTER TABLE GioHangChiTiet
ADD CONSTRAINT FK_GioHangChiTiet_GioHang
FOREIGN KEY (IdGioHang)
REFERENCES GioHang(IdGioHang);
go
-- Thêm khóa ngoại để liên kết bảng GioHangChiTiet với bảng Sach
ALTER TABLE GioHangChiTiet
ADD CONSTRAINT FK_GioHangChiTiet_Sach
FOREIGN KEY (MaSach)
REFERENCES Sach(MaSach);
go
-- Thêm khóa ngoại để liên kết bảng ThuDienTu với bảng NhanVien
ALTER TABLE ThuDienTu
ADD CONSTRAINT FK_ThuDienTu_NhanVien
FOREIGN KEY (idNhanVien)
REFERENCES NhanVien(IdNhanVien);



