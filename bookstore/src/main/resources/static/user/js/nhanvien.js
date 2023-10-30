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
