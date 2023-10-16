use SD48_CoSoDuLieuCuaHangSachAnhVT
go
create table KhachHang(
IdKhachHang 	int identity PRIMARY KEY,
IdGioHang int unique,
MaKhachHang varchar(250) unique,
HoVaTen nvarchar(250),
SDT varchar(50) unique,
NgaySinh date,
TrangThai int,
MatKhau varchar(250),
Email varchar(250),
LoaiKhachHang nvarchar(250),
GioiTinh int,
)
go
create table PhuongThucThanhToan(
IdPhuongThucThanhToan int identity PRIMARY KEY,
TenPhuongThucThanhToan nvarchar(250),
TrangThai int
)
go
create table ThongTinGiaoHang(
IdThongTinGiaoHang  int identity PRIMARY KEY,
TenNguoiNhan nvarchar(250),
SoDienThoai varchar(50),
ThanhPho nvarchar(50),
QuanHuyen nvarchar(50),
PhuongXa nvarchar(50),
DiaChiCuThe nvarchar(250),
)
go
create table DonViVanChuyen(
IdDonViVanChuyen int identity PRIMARY KEY,
TenDonViVanChuyen nvarchar(250),
PhiVanChuyen MONEY,
email varchar(30)
)
go
create table DanhGia(
IdDanhGia int identity PRIMARY KEY,
MaSach varchar(250),
MaKhachHang varchar(250),
MucDanhGia int,
BinhLuan nvarchar(300),
NgayTao date,
TrangThai varchar(50),
)
go 
create table DonHang(
IdDonhang int identity PRIMARY KEY,
MaKhachHang varchar(250),
MaDonHang varchar(250) unique,
NgayTao date,
NgayThanhToan date,
IdPhuongThucThanhToan int,
IdThongTinGiaoHang int,
TongTienHangGoc money,
TongTienKhuyenMai money,
PhiVanChuyen int,
TongTienCanThanhToan money,
TrangThai nvarchar(50),
MaNhanvien varchar(250),
IdDonViVanChuyen int,
GhiChu nvarchar(250)
)
go
create table DonHangChiTiet(
IdDonHangChiTiet int identity PRIMARY KEY,
MaSach varchar(250),
MaDonHang  varchar(250),
SoLuong int,
MaKhuyenMai varchar(250),
DonGiaThoiDiemMua int,
ThanhTien money
)

go
create table NhanVien(
IdNhanVien int identity PRIMARY KEY,
MaNhanVien varchar(250)unique,
HoVaTen nvarchar(250),
SDT varchar(50) unique,
NgaySinh date,
TrangThai int,
MatKhau nvarchar(250),
Email varchar(250),
ChucVu nvarchar(50),
)
go
create table GioHang(
IdGioHang int identity PRIMARY KEY,
TongSoLuongSach int ,
TongThanhTien int ,
)

go
create table GioHangChiTiet(
IdGioHangChiTiet int identity PRIMARY KEY,
IdGioHang  int ,
MaSach varchar(250)unique ,
SoLuong int ,
NgayChinhSua Date
)
go
create table Sach(
IdSach int identity PRIMARY KEY,
MaSach varchar(250)unique,
TenSach nvarchar(250),
MoTa nvarchar(250),
TrangThai int,
SoLuongTonKho int,
GiaBan money,
LinkAnh1 varchar(100),
LinkAnh2 varchar(100),
LinkAnh3 varchar(100),
LinkAnh4 varchar(100),
LinkAnh5 varchar(100),
MaVach varchar(50),
)
go
create table TacGia(
IdTacGia int identity PRIMARY KEY,
Email varchar(50),
HoVaTen nvarchar(250),
LinkAnhTacGia nvarchar(200),
TrangThai int,
SDT varchar(50),
)
go
create table SachTacGia(
id_Sach_tacGia int identity PRIMARY KEY,
MaSach varchar(250),
IdTacGia  int,
);
go
create table TheLoai(
IdTheLoai int identity PRIMARY KEY,
TenTheLoai nvarchar(250),
MoTa nvarchar(250),
TrangThai NVARCHAR(250),
)
go
create table SachTheLoai(
id_Sach_TheLoai int identity PRIMARY KEY,
MaSach varchar(250),
IdTheLoai  int,
);
go
create table KhuyenMai(
IdKhuyenMai int identity PRIMARY KEY,
TenKhuyenMai nvarchar(250),
NgayBatDau date,
NgayKetThuc date,
TrangThai nvarchar(250),
LinkBannerKhuyenMai  varchar(200),
LinkAnhKhuyenMai  varchar(200),
)
go
create table SachKhuyenMai(
IdKhuyenMai_Sach int identity PRIMARY KEY,
MaSach varchar(250),
IdKhuyenMai int,
SoPhanTramGiamGia int
);
go
create table DanhSachYeuThich(
IdDanhSachYeuThich int identity PRIMARY KEY,
MaKhachHang int,
MaSach int,
)
go
create table TroChuyen(
IdTroChuyen int identity PRIMARY KEY,
MaKhachHang int,
MaNhanVien int,
NoiDung nvarchar(350),
NgayGui Datetime,
)
go
create table ThuDienTu(
IdThuDienTu int identity PRIMARY KEY,
TieuDe nvarchar(350),
NoiDung nvarchar(350),
NgayGui Datetime,
EmailNGuoiNhan varchar(40),
idNhanVien int,
)
go
create table ThongTin(
IdThongTin int identity PRIMARY KEY,
LinkBannerTrangChu nvarchar(350),
DiaChi nvarchar(350),
SoDienThoai1 varchar(20),
SoDienThoai2 varchar(20),
LinkLogo varchar(40),
Email varchar(40),
)
go
cREATE TABLE ThongBao (
    IdThongBao int identity PRIMARY KEY,
    NoiDung TEXT,
    NgayGui DATETIME,
)
go
CREATE TABLE ThongBao_KhachHang (
	IdThongBao_KhachHang int identity PRIMARY KEY,
    IdThongBao INT,
    IdKhachHang INT,
);
-- bổ sung chi tiết đơn hàng
go
ALTER TABLE donhangchitiet
ADD PhanTramGiamGia int,
 GiaGoc DECIMAL(10, 2);
go
alter table donhangchitiet
alter column DonGiathoiDiemMua money




