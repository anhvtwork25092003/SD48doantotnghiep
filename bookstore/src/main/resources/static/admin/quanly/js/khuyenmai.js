function changeImage(index) {
    // Lấy đối tượng input file
    var fileInput = document.getElementById('imageInput' + index);

    // Lấy đối tượng ảnh hiện tại
    var image = document.getElementById('SachImage' + index);

    // Đảm bảo người dùng đã chọn filenhanvien
    if (fileInput.files && fileInput.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            // Thay đổi đường dẫn ảnh và hiển thị ảnh mới
            image.src = e.target.result;
        };
        reader.readAsDataURL(fileInput.files[0]);
    }
}


window.onload = function () {
    var fileInputs = document.querySelectorAll('[id^="editlinkBannerKhuyenMai-"]');

    fileInputs.forEach(function (fileInput) {
        var KhuyenMaiId = fileInput.id.replace('editlinkBannerKhuyenMai-', '');
        var imageElement = document.getElementById('editviewLinkBannerKhuyenMai-' + KhuyenMaiId);
        var deleteButton = document.getElementById('deleteButton1-' + KhuyenMaiId);

        if (fileInput.files && fileInput.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                imageElement.src = e.target.result;

                // Hiển thị nút "Xóa Ảnh"
                deleteButton.style.display = "inline-block";
            };

            reader.readAsDataURL(fileInput.files[0]);
        }
    });
};

window.onload = function () {
    var fileInputs = document.querySelectorAll('[id^="editlinkAnhKhuyenMai-"]');

    fileInputs.forEach(function (fileInput) {
        var KhuyenMaiId = fileInput.id.replace('editlinkAnhKhuyenMai-', '');
        var imageElement = document.getElementById('editviewLinkAnhKhuyenMai-' + KhuyenMaiId);
        var deleteButton = document.getElementById('deleteButton1-' + KhuyenMaiId);

        if (fileInput.files && fileInput.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                imageElement.src = e.target.result;

                // Hiển thị nút "Xóa Ảnh"
                deleteButton.style.display = "inline-block";
            };

            reader.readAsDataURL(fileInput.files[0]);
        }
    });
};

function displayImage(input, imageId) {
    var fileInput = input;
    var imageElement = document.getElementById(imageId);
    var deleteButton = document.getElementById('deleteButton1');

    if (fileInput.files && fileInput.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            imageElement.src = e.target.result;
            imageElement.style.display = "inline"; // Hiển thị hình ảnh
            // Hiển thị nút "Xóa Ảnh"
            deleteButton.style.display = "inline-block";
        };

        reader.readAsDataURL(fileInput.files[0]);
    }

    // Đặt giá trị mới tại đây
    var newImageUrl = "DaThayDoi";
    document.getElementById("checkthayDoiBannerKhuyenMai").value = newImageUrl;
}

function deleteImage(imageId, inputId, tinhTrangThayDoi, event) {
    event.preventDefault();
    var newImageUrl = "DaThayDoi";
    document.getElementById(tinhTrangThayDoi).value = newImageUrl;
    var imageElement = document.getElementById(imageId);
    imageElement.src = 'default-image.jpg';

    var deleteButton = document.getElementById('deleteButton1'); // Đoạn này có vấn đề
    deleteButton.style.display = "none";

    var fileInput = document.getElementById(inputId);
    fileInput.value = '';
}

function editImage(inputId, tinhTrangThayDoi) {
    var fileInput = document.getElementById(inputId);
    var newImageUrl = "DaThayDoi";
    document.getElementById(tinhTrangThayDoi).value = newImageUrl;
    if (fileInput.files && fileInput.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            var imageElement = fileInput.previousElementSibling.querySelector('img');
            imageElement.src = e.target.result;

            var deleteButton = fileInput.nextElementSibling;
            deleteButton.style.display = "inline-block";

            var checkthayDoiImage = fileInput.parentElement.parentElement.querySelector('[name="checkthayDoiBannerKhuyenMai"]');
            checkthayDoiImage.value = "DaThayDoi";
        };

        reader.readAsDataURL(fileInput.files[0]);
    }
}

<!-- Trong trang Thymeleaf -->
function alertModal() {
    // Tạo cửa sổ modal
    var modal = document.createElement("div");
    var message = document.getElementById("thongBaoXoaID").innerText;
    modal.className = "modal";
    modal.innerHTML = '<div class="modal-content">' +
        '<span class="close" onclick="closeModal()">&times;</span>' +
        '<p>' + message + '</p>' +
        '</div>';

    // Thêm modal vào body
    document.body.appendChild(modal);
}

function closeModal() {
    // Đóng cửa sổ modal
    var modal = document.querySelector(".modal");
    if (modal) {
        document.body.removeChild(modal);
    }
}

function updateThymeleaf() {
    var ngayBatDau = document.getElementById("ngayBatDauThemMoi").value;
    var ngayKetThuc = document.getElementById("ngayKetThucThemMoi").value;

    // Gửi yêu cầu API để lấy danh sách sách chưa áp dụng khuyến mãi
    fetch('/api/books/haventsaleoff?batdau=' + ngayBatDau + '&ketthuc=' + ngayKetThuc)
        .then(response => response.json())
        .then(data => {
            // Cập nhật Thymeleaf fragment
            document.getElementById("list2").innerHTML = '';
            displayBooks(data);
        })
        .catch(error => console.error('Error:', error));
}

function displayBooks(books) {
    var danhsachsanpham = document.getElementById("list2");

    // Tạo checkbox cho mỗi sách
    books.forEach(function (sach) {
        var checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.id = 'sachCheckbox_' + sach.idSach;
        checkbox.name = 'sachKM';
        checkbox.value = sach.idSach;

        var label = document.createElement('label');
        label.htmlFor = 'sachCheckbox_' + sach.idSach;
        label.appendChild(document.createTextNode(sach.maSach + ' ' + sach.tenSach + ' ' + sach.giaBanVnd));

        var div = document.createElement('div');
        div.className = 'bookItem';
        div.appendChild(checkbox);
        div.appendChild(label);

        danhsachsanpham.appendChild(div);
    });
}

