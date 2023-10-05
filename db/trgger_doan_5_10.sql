use Sd48_DuLieuCuaHangSach
go
CREATE TRIGGER AutoGenerateMaKH
ON KhachHang
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @NewIDKhachHang int;
    DECLARE @NewMaKhachHang VARCHAR(250);
    
    -- Tìm số lượng khách hàng hiện tại
    DECLARE @SoLuongKhachHang INT;
    SELECT @SoLuongKhachHang = COUNT(*) FROM KhachHang;
    
    -- Lấy ID của bản ghi vừa thêm vào
    SELECT @NewIDKhachHang = IdKhachHang
    FROM inserted;
    
    -- Tạo mã khách hàng mới
    SET @NewMaKhachHang = 'KH' + CAST(@SoLuongKhachHang + 1 AS VARCHAR(10));
    
    -- Kiểm tra xem mã đã tồn tại chưa và cập nhật cho bản ghi mới
    WHILE EXISTS (SELECT 1 FROM KhachHang WHERE MaKhachHang = @NewMaKhachHang)
    BEGIN
        SET @SoLuongKhachHang = @SoLuongKhachHang + 1;
        SET @NewMaKhachHang = 'KH' + CAST(@SoLuongKhachHang + 1 AS VARCHAR(10));
    END
    
    -- Cập nhật mã khách hàng cho bản ghi vừa thêm vào
    UPDATE KhachHang
    SET MaKhachHang = @NewMaKhachHang
    WHERE IdKhachHang = @NewIDKhachHang;
END;


go 
CREATE TRIGGER AutoGenerateMaNV
ON NhanVien
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @NewIDNhanVien int;
    DECLARE @NewMaNhanVien VARCHAR(250);
    
    -- Tìm số lượng nhân viên hiện tại
    DECLARE @SoLuongNhanVien INT;
    SELECT @SoLuongNhanVien = COUNT(*) FROM NhanVien;
    
    -- Lấy ID của bản ghi vừa thêm vào
    SELECT @NewIDNhanVien = IdNhanVien
    FROM inserted;
    
    -- Tạo mã nhân viên mới
    SET @NewMaNhanVien = 'NV' + CAST(@SoLuongNhanVien + 1 AS VARCHAR(10));
    
    -- Kiểm tra xem mã đã tồn tại chưa và cập nhật cho bản ghi mới
    WHILE EXISTS (SELECT 1 FROM NhanVien WHERE MaNhanVien = @NewMaNhanVien)
    BEGIN
        SET @SoLuongNhanVien = @SoLuongNhanVien + 1;
        SET @NewMaNhanVien = 'NV' + CAST(@SoLuongNhanVien + 1 AS VARCHAR(10));
    END
    
    -- Cập nhật mã nhân viên cho bản ghi vừa thêm vào
    UPDATE NhanVien
    SET MaNhanVien = @NewMaNhanVien
    WHERE IdNhanVien = @NewIDNhanVien;
END;

go
-- Tạo biến đếm
CREATE TRIGGER AutoGenerateMaSach
ON Sach
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @NewIDSach int;
    DECLARE @NewMaSach VARCHAR(250);
    
    -- Tìm số lượng sách hiện tại
    DECLARE @SoLuongSach INT;
    SELECT @SoLuongSach = COUNT(*) FROM Sach;
    
    -- Lấy ID của bản ghi vừa thêm vào
    SELECT @NewIDSach = IdSach
    FROM inserted;
    
    -- Tạo mã sách mới
    SET @NewMaSach = 'SACH' + CAST(@SoLuongSach + 1 AS VARCHAR(10));
    
    -- Kiểm tra xem mã đã tồn tại chưa và cập nhật cho bản ghi mới
    WHILE EXISTS (SELECT 1 FROM Sach WHERE MaSach = @NewMaSach)
    BEGIN
        SET @SoLuongSach = @SoLuongSach + 1;
        SET @NewMaSach = 'SACH' + CAST(@SoLuongSach + 1 AS VARCHAR(10));
    END
    
    -- Cập nhật mã sách cho bản ghi vừa thêm vào
    UPDATE Sach
    SET MaSach = @NewMaSach
    WHERE IdSach = @NewIDSach;
END;

go
CREATE TRIGGER AutoGenerateMaDonHang
ON DonHang
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @NewIDDonHang int;
    DECLARE @NewMaDonHang VARCHAR(250);
    
    -- Tìm số lượng đơn hàng hiện tại
    DECLARE @SoLuongDonHang INT;
    SELECT @SoLuongDonHang = COUNT(*) FROM DonHang;
    
    -- Lấy ID của bản ghi vừa thêm vào
    SELECT @NewIDDonHang = IdDonHang
    FROM inserted;
    
    -- Tạo mã đơn hàng mới
    SET @NewMaDonHang = 'DH' + CAST(@SoLuongDonHang + 1 AS VARCHAR(10));
    
    -- Kiểm tra xem mã đã tồn tại chưa và cập nhật cho bản ghi mới
    WHILE EXISTS (SELECT 1 FROM DonHang WHERE MaDonHang = @NewMaDonHang)
    BEGIN
        SET @SoLuongDonHang = @SoLuongDonHang + 1;
        SET @NewMaDonHang = 'DH' + CAST(@SoLuongDonHang + 1 AS VARCHAR(10));
    END
    
    -- Cập nhật mã đơn hàng cho bản ghi vừa thêm vào
    UPDATE DonHang
    SET MaDonHang = @NewMaDonHang
    WHERE IdDonHang = @NewIDDonHang;
END;

