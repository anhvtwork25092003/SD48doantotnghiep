
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
    //
    // function displayImage(input, imageId) {
    //     var selectedImage = document.getElementById(imageId);
    //     if (input.files && input.files[0]) {
    //         var reader = new FileReader();
    //         reader.onload = function (e) {
    //             selectedImage.src = e.target.result;
    //             selectedImage.style.display = 'block';
    //         };
    //         reader.readAsDataURL(input.files[0]);
    //     } else {
    //         selectedImage.style.display = 'none';
    //     }
    // }
// Hàm để lưu và hiển thị hình ảnh

    // Hàm để hiển thị nút "Xóa Ảnh" khi có hình ảnh được hiển thị
    // Hàm để hiển thị ảnh và lưu trạng thái hình ảnh vào Local Storage cho từng dữ liệu
    // window.onload = function() {
    //     // Kiểm tra trạng thái hình ảnh trong Local Storage
    //     var savedImageSrc = localStorage.getItem('imageSrc');
    //     var imageElement = document.getElementById('editviewLinkAnh1');
    //     var deleteButton = document.getElementById('deleteButton1');
    //     var fileInput = document.getElementById('editlinkAnh1');
    //
    //     if (savedImageSrc) {
    //         // Nếu có trạng thái hình ảnh đã được lưu, cập nhật hình ảnh và hiển thị nút "Xóa Ảnh"
    //         imageElement.src = savedImageSrc;
    //         deleteButton.style.display = "inline-block";
    //         fileInput.style.display = "none"; // Ẩn input "Chọn tệp"
    //     }
    //     else {
    //         // Nếu không có trạng thái hình ảnh, hiển thị input "Chọn tệp" và ẩn nút "Xóa Ảnh"
    //         deleteButton.style.display = "none";
    //         fileInput.style.display = "inline-block";
    //     }
    // };
    //
    // function displayImage(input, imageId) {
    //     var fileInput = input;
    //     var imageElement = document.getElementById(imageId);
    //
    //     if (fileInput.files && fileInput.files[0]) {
    //         var reader = new FileReader();
    //
    //         reader.onload = function (e) {
    //             imageElement.src = e.target.result;
    //             // Lưu trạng thái hình ảnh vào Local Storage
    //             localStorage.setItem('imageSrc', e.target.result);
    //
    //             // Hiển thị nút "Xóa Ảnh" và ẩn input "Chọn tệp"
    //             var deleteButton = document.getElementById('deleteButton1');
    //             deleteButton.style.display = "inline-block";
    //             fileInput.style.display = "none";
    //         };
    //
    //         reader.readAsDataURL(fileInput.files[0]);
    //     }
    // }
    //
    // function deleteImage(imageId, inputId, event) {
    //     // Ngăn sự kiện mặc định của nút xóa
    //     event.preventDefault();
    //
    //     // Xóa trạng thái hình ảnh từ Local Storage
    //     localStorage.removeItem('imageSrc');
    //
    //     // Xóa hình ảnh trong modal và ẩn nút "Xóa Ảnh", hiển thị input "Chọn tệp"
    //     var imageElement = document.getElementById(imageId);
    //     imageElement.src = 'default-image.jpg'; // Thay thế bằng ảnh mặc định hoặc ẩn ảnh
    //     var deleteButton = document.getElementById('deleteButton1');
    //     deleteButton.style.display = "none";
    //     var fileInput = document.getElementById(inputId);
    //     fileInput.style.display = "inline-block";
    // }


    window.onload = function() {
        var imageElement = document.getElementById('editviewLinkAnh1');
        var deleteButton = document.getElementById('deleteButton1');
        var fileInput = document.getElementById('editlinkAnh1');

        if (fileInput.files && fileInput.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                imageElement.src = e.target.result;

                // Hiển thị nút "Xóa Ảnh"
                deleteButton.style.display = "inline-block";
            };

            reader.readAsDataURL(fileInput.files[0]);
        }
    };

    function displayImage(input, imageId) {
        var fileInput = input;
        var imageElement = document.getElementById(imageId);

        if (fileInput.files && fileInput.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                imageElement.src = e.target.result;

                // Hiển thị nút "Xóa Ảnh"
                var deleteButton = document.getElementById('deleteButton1');
                deleteButton.style.display = "inline-block";
            };

            reader.readAsDataURL(fileInput.files[0]);
        }
    }

    function deleteImage(imageId, inputId, event) {
        // Ngăn sự kiện mặc định của nút xóa
        event.preventDefault();

        var imageElement = document.getElementById(imageId);
        imageElement.src = 'default-image.jpg'; // Thay thế bằng ảnh mặc định hoặc ẩn ảnh

        var deleteButton = document.getElementById('deleteButton1');
        deleteButton.style.display = "none";

        var fileInput = document.getElementById(inputId);
        fileInput.value = ''; // Đặt lại giá trị của input để xóa tệp đã chọn
    }



    function editImage(inputId) {
        // Lấy đối tượng input chứa ảnh
        var fileInput = document.getElementById(inputId);

        // Kiểm tra xem người dùng đã chọn tệp ảnh mới chưa
        if (fileInput.files && fileInput.files[0]) {
            // Người dùng đã chọn tệp ảnh mới, có thể thực hiện xử lý chỉnh sửa ảnh ở đây
            // Ví dụ: Hiển thị modal chỉnh sửa ảnh, tải ảnh lên, và lưu trạng thái
            // ...

            // Sau khi chỉnh sửa xong, bạn có thể cập nhật trạng thái, ví dụ:
            var imageStatus = document.getElementById('imageStatus');
            imageStatus.value = "dathaydoi";
        } else {
            // Người dùng chưa chọn tệp ảnh mới, có thể thực hiện xử lý khi không có tệp ảnh mới
            // ...
        }
    }









