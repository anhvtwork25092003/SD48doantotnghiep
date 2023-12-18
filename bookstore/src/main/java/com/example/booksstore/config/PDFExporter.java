package com.example.booksstore.config;

import com.example.booksstore.entities.DonHang;
import com.example.booksstore.entities.DonHangChiTiet;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class PDFExporter {
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(8);

        Font font = FontFactory.getFont("DejaVuSans", "UTF-8", true);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("STT", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Tên sản phâm", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Số lượng", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Đơn giá", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Thành tiền", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table, DonHang donHang){
        int stt = 1;
        Font boldFont = FontFactory.getFont("DejaVuSans", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12, Font.BOLD);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        BigDecimal tongThanhTien = BigDecimal.ZERO;
        BigDecimal tienShip = donHang.getPhiVanChuyen();
        for (DonHangChiTiet dhct : donHang.getChiTietDonHang()) {
//            stt
            PdfPCell sttCell = new PdfPCell(new Phrase(String.valueOf(stt)));
            sttCell.setPadding(10f);
            sttCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(sttCell);

//            tên sản phẩm
            PdfPCell sanPhamCell = new PdfPCell(new Phrase(String.valueOf(dhct.getSach().getTenSach())));
            sanPhamCell.setPadding(10f);
            sanPhamCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(sanPhamCell);

//            số lượng
            PdfPCell soLuongCell = new PdfPCell(new Phrase(String.valueOf(dhct.getSoLuong())));
            soLuongCell.setPadding(10f);
            soLuongCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(soLuongCell);

//            đơn giá
            PdfPCell donGiaCell = new PdfPCell(new Phrase(currencyFormat.format(dhct.getDonGiaThoiDiemMua())));
            donGiaCell.setPadding(10f);
            donGiaCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(donGiaCell);

//            thành tiền
            BigDecimal thanhTien = BigDecimal.valueOf(dhct.getSoLuong())
                    .multiply(dhct.getDonGiaThoiDiemMua());
            PdfPCell thanhTienCell = new PdfPCell(new Phrase(currencyFormat.format(thanhTien)));
            thanhTienCell.setPadding(10f);
            thanhTienCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(thanhTienCell);

            tongThanhTien = tongThanhTien.add(thanhTien);
            stt++;
        }

//        tổng thành tiền
        PdfPCell tongTienCell = new PdfPCell(new Phrase("Tổng tiền"));
        tongTienCell.setColspan(4);
        tongTienCell.setPadding(10f);
        tongTienCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        PdfPCell tongTienValueCell = new PdfPCell(new Phrase(currencyFormat.format(tongThanhTien), boldFont));
        tongTienValueCell.setPadding(10f);
        tongTienValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(tongTienCell);
        table.addCell(tongTienValueCell);

//        tiền ship
        PdfPCell tienShipCell = new PdfPCell(new Phrase("Tiền ship"));
        tienShipCell.setColspan(4);
        tienShipCell.setPadding(10f);
        tienShipCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        PdfPCell tienShipValueCell = new PdfPCell(new Phrase(currencyFormat.format(tienShip), boldFont));
        tienShipValueCell.setPadding(10f);
        tienShipValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(tienShipCell);
        table.addCell(tienShipValueCell);

//      Tổng thanh toán
        PdfPCell tongThanhToanCell = new PdfPCell(new Phrase("Tổng thanh toán"));
        tongThanhToanCell.setColspan(4);
        tongThanhToanCell.setPadding(10f);
        tongThanhToanCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        PdfPCell tongThanhToanValueCell = new PdfPCell(new Phrase(currencyFormat.format(tongThanhTien.add(tienShip))));
        tongThanhToanValueCell.setPadding(10f);
        tongThanhToanValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(tongThanhToanCell);
        table.addCell(tongThanhToanValueCell);
    }

    public void export(HttpServletResponse response, DonHang donHang) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
//
        String imagePath = "src/main/resources/static/image/fahasa-logo.png";
        Image image = Image.getInstance(imagePath);
        image.scaleAbsolute(200, 100); // Điều chỉnh kích thước của ảnh

        Font fontTitle = FontFactory.getFont("Times New Roman","UTF-8",true,18, 1);
//        fontTitle.setColor(Color.BLUE);

        Font fontInfo = FontFactory.getFont("Times New Roman", "UTF-8", true, 14);

        Font fontInfoKhach = FontFactory.getFont("Times New Roman", "UTF-8",true,12, 1);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.0f, 2.0f, 2.0f, 3.0f, 3.0f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table, donHang);

//        logo - thông tin liên hệ
        PdfPTable headerTitleTable = new PdfPTable(2);
        headerTitleTable.setWidthPercentage(100);
        headerTitleTable.setWidths(new float[]{1.3f, 2.3f});

        PdfPCell imageCell = new PdfPCell(image);
        imageCell.setBorder(Rectangle.NO_BORDER);
        headerTitleTable.addCell(imageCell);

        PdfPCell infoCell = new PdfPCell();
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        Paragraph diaChi = new Paragraph("Địa Chỉ: 8,Ngõ 18 Phố Kiều Mai,Phường Phúc Diễn ,Quận Bắc Từ Liêm ,Thành Phố Hà Nội", fontInfo);
        Paragraph sdt = new Paragraph("Số điện thoại : 0876914222", fontInfo);
        Paragraph email = new Paragraph("Email : AnhVT.booker@gmail.com", fontInfo);

        diaChi.setAlignment(Element.ALIGN_RIGHT);
        sdt.setAlignment(Element.ALIGN_RIGHT);
        email.setAlignment(Element.ALIGN_RIGHT);

        infoCell.addElement(diaChi);
        infoCell.addElement(sdt);
        infoCell.addElement(email);

        headerTitleTable.addCell(infoCell);
//
//        Thông tin người nhận
        PdfPTable shipInfoTable = new PdfPTable(1);
        shipInfoTable.setWidthPercentage(100);
        shipInfoTable.setWidths(new float[]{1f});
        shipInfoTable.setSpacingBefore(10f);

        Paragraph columnName = new Paragraph("Thông tin giao hàng",fontInfoKhach);
        PdfPCell columnNameCell = new PdfPCell();
        columnNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
//        columnNameCell.setBorder(Rectangle.NO_BORDER);
        columnNameCell.addElement(columnName);
        columnNameCell.setPadding(10);

        Paragraph khachHang = new Paragraph("Khách hàng: ", fontInfoKhach);
        Paragraph sdtKhachHang = new Paragraph("Điện thoại:    " + donHang.getKhachHang().getSdt(), fontInfoKhach);
        Paragraph diaChiKhachHang = new Paragraph("Địa chỉ:         " + donHang.getThongTinGiaoHang().getDiaChiChu(), fontInfoKhach);

        PdfPCell shipInfoCell = new PdfPCell();
        shipInfoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        shipInfoCell.addElement(khachHang);
        shipInfoCell.addElement(sdtKhachHang);
        shipInfoCell.addElement(diaChiKhachHang);
        shipInfoCell.setPadding(10);

        shipInfoTable.addCell(columnNameCell);
        shipInfoTable.addCell(shipInfoCell);
//

        document.add(headerTitleTable);

        Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG", fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        document.add(shipInfoTable);

        document.add(table);

        document.close();

    }

}

