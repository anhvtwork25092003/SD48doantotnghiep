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
    var fileInputs = document.querySelectorAll('[id^="editlinkAnhNhanVien-"]');

    fileInputs.forEach(function (fileInput) {
        var NhanVienId = fileInput.id.replace('editlinkAnhNhanVien-', '');
        var imageElement = document.getElementById('editviewLinkAnhNhanVien-' + NhanVienId);
        var deleteButton = document.getElementById('deleteButton1-' + NhanVienId);

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
    document.getElementById("checkthayDoiImage").value = newImageUrl;
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

            var checkthayDoiImage = fileInput.parentElement.parentElement.querySelector('[name="checkthayDoiImage"]');
            checkthayDoiImage.value = "DaThayDoi";
        };

        reader.readAsDataURL(fileInput.files[0]);
    }
}

function checkEmail() {
    var emailInput = document.getElementById('email');
    var emailError = document.getElementById('email-error');

    var emailValue = emailInput.value;

    if (emailValue.indexOf('@') === -1) {
        emailError.textContent = 'Email must contain the @ symbol.';
    } else {
        emailError.textContent = '';
    }
}

function checkTen() {
    var tenInput = document.getElementById('hoVaTen');
    var tenError = document.getElementById('ten-error');

    var tenValue = tenInput.value;

    if (tenValue.length == 0) {
        tenError.textContent = 'Tên không được để trống';
    } else {
        tenError.textContent = '';
    }
}