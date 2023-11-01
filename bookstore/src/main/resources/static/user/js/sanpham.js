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

document.addEventListener("DOMContentLoaded", function () {
    const bookList = document.getElementById("book-list");
    const paginationButtons = document.getElementById("pagination-buttons");

    paginationButtons.addEventListener("click", function (e) {
        if (e.target.classList.contains("page-link")) {
            // Lấy giá trị data-page từ nút
            const pageValue = e.target.getAttribute("data-page");

            // Kiểm tra nếu là nút "First"
            if (pageValue === "0") {
                loadBooks(0);
            }
            // Kiểm tra nếu là nút "Previous"
            else if (pageValue === "-1") {
                const currentPage = parseInt(paginationButtons.querySelector(".active").getAttribute("data-page"));
                loadBooks(currentPage - 1);
            }
            // Kiểm tra nếu là nút "Last"
            else if (pageValue === "last") {
                const lastPage = parseInt(paginationButtons.querySelector(".page-link:last-child").getAttribute("data-page"));
                loadBooks(lastPage);
            }
            // Nếu là nút bình thường
            else {
                // Lấy giá trị trang từ data-page
                const page = parseInt(pageValue);
                loadBooks(page);
            }
        }
    });

    // Hàm AJAX để tải dữ liệu sách
    function loadBooks(page) {
        const xhr = new XMLHttpRequest();
        xhr.open("GET", "/quan-ly/sach/hien-thi-test?page=" + page, true);

        xhr.onload = function () {
            if (xhr.status === 200) {
                // Cập nhật nội dung của div book-list với dữ liệu mới
                bookList.innerHTML = xhr.responseText;
            }
        };

        xhr.send();
    }
});














