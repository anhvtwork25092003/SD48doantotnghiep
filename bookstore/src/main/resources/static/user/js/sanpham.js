<!-- Đoạn mã JavaScript -->

function changeImage(index) {
    // Lấy đối tượng input file
    var fileInput = document.getElementById('imageInput' + index);

    // Lấy đối tượng ảnh hiện tại
    var image = document.getElementById('sachImage' + index);

    // Đảm bảo người dùng đã chọn file
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
    var fileInputs = document.querySelectorAll('[id^="editlinkAnh1-"]');

    fileInputs.forEach(function (fileInput) {
        var sachId = fileInput.id.replace('editlinkAnh1-', '');
        var imageElement = document.getElementById('editviewLinkAnh1-' + sachId);
        var deleteButton = document.getElementById('deleteButton1-' + sachId);

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
    var fileInputs = document.querySelectorAll('[id^="editlinkAnh2-"]');

    fileInputs.forEach(function (fileInput) {
        var sachId = fileInput.id.replace('editlinkAnh2-', '');
        var imageElement = document.getElementById('editviewLinkAnh2-' + sachId);
        var deleteButton = document.getElementById('deleteButton1-' + sachId);

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
    var fileInputs = document.querySelectorAll('[id^="editlinkAnh3-"]');

    fileInputs.forEach(function (fileInput) {
        var sachId = fileInput.id.replace('editlinkAnh3-', '');
        var imageElement = document.getElementById('editviewLinkAnh3-' + sachId);
        var deleteButton = document.getElementById('deleteButton1-' + sachId);

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
    var fileInputs = document.querySelectorAll('[id^="editlinkAnh4-"]');

    fileInputs.forEach(function (fileInput) {
        var sachId = fileInput.id.replace('editlinkAnh4-', '');
        var imageElement = document.getElementById('editviewLinkAnh4-' + sachId);
        var deleteButton = document.getElementById('deleteButton1-' + sachId);

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
    var fileInputs = document.querySelectorAll('[id^="editlinkAnh5-"]');

    fileInputs.forEach(function (fileInput) {
        var sachId = fileInput.id.replace('editlinkAnh5-', '');
        var imageElement = document.getElementById('editviewLinkAnh5-' + sachId);
        var deleteButton = document.getElementById('deleteButton1-' + sachId);

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













