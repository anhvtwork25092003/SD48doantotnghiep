
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
    function displayImage(input, imageId) {
        var selectedImage = document.getElementById(imageId);
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                selectedImage.src = e.target.result;
                selectedImage.style.display = 'block';
            };
            reader.readAsDataURL(input.files[0]);
        } else {
            selectedImage.style.display = 'none';
        }
    }
// Hàm để lưu và hiển thị hình ảnh






