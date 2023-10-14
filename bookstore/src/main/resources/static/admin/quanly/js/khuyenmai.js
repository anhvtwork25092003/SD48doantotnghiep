// var contentVisible = false; // Ban đầu, nội dung là ẩn

// // Hàm để hiển thị/ẩn nội dung và cập nhật trạng thái
// function showContent(contentId) {
//     var contentElement = document.getElementById(contentId);
//     if (contentVisible) {
//         contentElement.style.display = "none";
//         contentVisible = false;
//     } else {
//         contentElement.style.display = "block";
//         contentVisible = true;
//     }
// }

// // Kiểm tra trạng thái ban đầu khi tải trang
// if (!contentVisible) {
//     document.querySelectorAll("#content div").forEach(function (element) {
//         element.style.display = "none";
//     });
// }
function openModal1() {
    $('#modalTrang1').modal('show');
  }

  function openModal2() {
    $('#modalTrang2').modal('show');
  }

  function closeModal1() {
    $('#modalTrang1').modal('hide');
  }

  function closeModal2() {
    $('#modalTrang2').modal('hide');
  }




// Dữ liệu sản phẩm mẫu
const thongTins = [
  { id: 1, ma:"1" , ten: "Khuyến mại A", batdau: "10/10/2023", ketthuc:"20/10/2023", hinhthuc:"Mua hàng KM", trangthai: "Kích Hoạt" },
  { id: 2, ma:"2" , ten: "Khuyến mại B", batdau: "10/10/2023", ketthuc:"20/10/2023", hinhthuc:"Mua hàng KM", trangthai: "Kích Hoạt" },
  { id: 3, ma:"3" , ten: "Khuyến mại C", batdau: "10/10/2023", ketthuc:"20/10/2023", hinhthuc:"Mua hàng KM", trangthai: "Kích Hoạt" },
  { id: 4, ma:"4" , ten: "Khuyến mại D", batdau: "10/10/2023", ketthuc:"20/10/2023", hinhthuc:"Mua hàng KM", trangthai: "Kích Hoạt" },
];

// Lấy các phần tử HTML
const maChuongTrinh = document.getElementById("maChuongTrinh");
const tenChuongTrinh = document.getElementById("tenChuongTrinh");
const ngayBatDau = document.getElementById("ngayBatDau");
const ngayKetThuc = document.getElementById("ngayKetThuc");
const hinhThuc = document.getElementById("hinhThuc");
const trangThai = document.getElementById("trangThai");
const thongTin = document.getElementById("thongTin");

// Hàm để hiển thị thông tin sản phẩm khi dòng bảng được click
function showProductDetails(id) {
  const thongtin = thongTins.find(thongtin => thongtin.id === id);
  if (thongtin) {
      maChuongTrinh.textContent = thongtin.ma;
      tenChuongTrinh.textContent = thongtin.ten;
      ngayBatDau.textContent = thongtin.batdau;
      ngayKetThuc.textContent = thongtin.ketthuc;
      hinhThuc.textContent = thongtin.hinhthuc;
      trangThai.textContent = thongtin.trangthai;
      thongTin.style.display = "block";
  }
}

// Bắt sự kiện click trên dòng trong bảng
document.querySelectorAll(".table tbody tr").forEach(row => {
  row.addEventListener("click", () => {
      const id = parseInt(row.cells[0].textContent);
      showProductDetails(id);
  });
});

