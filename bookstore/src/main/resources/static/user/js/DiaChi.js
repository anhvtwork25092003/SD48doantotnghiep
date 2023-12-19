document.addEventListener('DOMContentLoaded', function () {
    const host = "https://provinces.open-api.vn/api/";
    var callAPI = (api) => {
        return axios.get(api)
            .then((response) => {
                renderData(response.data, "tinhThanhPho");
            });
    }
    callAPI('https://provinces.open-api.vn/api/?depth=1');
    var callApiDistrict = (api) => {
        return axios.get(api)
            .then((response) => {
                renderData(response.data.districts, "huyenQuan");
            });
    }
    var callApiWard = (api) => {
        return axios.get(api)
            .then((response) => {
                renderData(response.data.wards, "xaPhuong");
            });
    }

    var renderData = (array, select) => {
        let row = ' <option disable value="">chọn</option>';
        array.forEach(element => {
            row += `<option value="${element.code}">${element.name}</option>`
        });
        document.querySelector("#" + select).innerHTML = row
    }

    $("#tinhThanhPho").change(() => {
        callApiDistrict(host + "p/" + $("#tinhThanhPho").val() + "?depth=2");
        printResult();
    });
    $("#huyenQuan").change(() => {
        callApiWard(host + "d/" + $("#huyenQuan").val() + "?depth=2");
        printResult();
    });
    $("#xaPhuong").change(() => {
        printResult();
    })

    var printResult = () => {
        if ($("#huyenQuan").val() != "" && $("#tinhThanhPho").val() != "" &&
            $("#xaPhuong").val() != "") {
            let result = $("#xaPhuong option:selected").text() +
                " , " + $("#huyenQuan option:selected").text() + " , " +
                $("#tinhThanhPho option:selected").text();
            $("#diaChiChu").val(result);
            $("#diaChiChu").text(result);// Đặt giá trị cho trường input ẩn
        }
    }
    function validateFormChuaDangNhap() {
        var tenNguoiNhan = document.getElementsByName("tenNguoiNhan")[0].value;
        var soDienThoaiNhanHang = document.getElementsByName("soDienThoaiNhanHang")[0].value;
        var email = document.getElementsByName("email")[0].value;
        // Kiểm tra trống
        if (tenNguoiNhan.trim() === "" || soDienThoaiNhanHang.trim() === "" || email.trim() === "") {
            alert("Vui lòng điền đầy đủ thông tin.");
            return false;
        }
        // Kiểm tra ký tự đặc biệt
        var specialCharRegex = /[!@#$%^&*(),.?":{}|<>]/;
        if (specialCharRegex.test(tenNguoiNhan) || specialCharRegex.test(soDienThoaiNhanHang)
        ) {
            alert("Không được nhập ký tự đặc biệt.");
            return false;
        }
        // Kiểm tra email
        var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            alert("Email không hợp lệ. Vui lòng nhập email theo định dạng đúng.");
            return false;
        }
        return true;
    }

    function validateFormDagNhap() {
        // Lấy danh sách tất cả các ô radio có name là "diaChiRadio"
        var radioButtons = document.getElementsByName("diaChiRadio");
        // Kiểm tra xem có ô radio nào được chọn hay không
        var isAnyRadioButtonChecked = false;
        for (var i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i].checked) {
                isAnyRadioButtonChecked = true;
                break;
            }
        }
        // Hiển thị cảnh báo nếu không có ô radio nào được chọn
        if (!isAnyRadioButtonChecked) {
            alert("Vui lòng chọn một địa chỉ nhận hàng trước khi tiến hành thanh toán.");
            return false;
        }

        return true;
    }

    function validateFormDiaChi() {
        var tenNguoiNhan = document.getElementsByName("TenNguoiNhanThemMoi")[0].value;
        var soDienThoaiNhanHang = document.getElementsByName("sdtnhanHangThemMoi")[0].value;
        // Kiểm tra trống
        if (tenNguoiNhan.trim() === "" || soDienThoaiNhanHang.trim() === "") {
            alert("Vui lòng điền đầy đủ thông tin.");
            return false;
        }
        // Kiểm tra ký tự đặc biệt
        var specialCharRegex = /[!@#$%^&*(),.?":{}|<>]/;
        if (specialCharRegex.test(tenNguoiNhan) || specialCharRegex.test(soDienThoaiNhanHang)
        ) {
            alert("Không được nhập ký tự đặc biệt.");
            return false;
        }
        // kiểm tra định dạng số điện thoại
        var soDienThoaiNhanHang = document.getElementsByName("sdtnhanHangThemMoi")[0].value.trim();

        // Kiểm tra định dạng số điện thoại Việt Nam
        var phoneNumberRegex = /^(0[1-9]|10|11|12|13|14|15|16|17|18|19|2[0-9]|3[0-9]|4[0-9]|5[0-9]|6[0-9]|7[0-9]|8[0-9]|9[0-9])+([0-9]{8})\b/;
        if (!phoneNumberRegex.test(soDienThoaiNhanHang)) {
            alert("Số điện thoại không hợp lệ. Vui lòng nhập theo định dạng Việt Nam.");
            return false;
        }
        return true;
    }
});
