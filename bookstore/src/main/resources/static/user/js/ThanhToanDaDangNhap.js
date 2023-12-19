document.addEventListener('DOMContentLoaded', function () {
    const apiKey = 'c3e9d4a4-6352-11ee-a59f-a260851ba65c';
    const apiUrl = 'https://online-gateway.ghn.vn/shiip/public-api/master-data/';
    main();

    async function fetchData(apiUrl, endpoint) {
        const response = await fetch(`${apiUrl}${endpoint}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Token': apiKey,
            },
        });

        if (!response.ok) {
            throw new Error(`Không thể lấy dữ liệu từ ${apiUrl}${endpoint}`);
        }

        const data = await response.json();
        return data.data;
    }

    async function getAllProvinces() {

        const provinces = await fetchData(apiUrl, 'province');
        return provinces;
    }

    async function getAlldistricts() {
        const districts = await fetchData(apiUrl, 'district');
        return districts;
    }

    async function getAllward(districtid) {
        const wards = await fetchData(apiUrl, 'ward?district_id=' + districtid);
        return wards;
    }

    async function findProvinceById(provinceId) {
        const allProvinces = await getAllProvinces();
        const foundProvince = allProvinces.find(province => province.ProvinceID === provinceId);

        if (!foundProvince) {
            throw new Error(`Không tìm thấy tỉnh/thành phố với ID ${provinceId}`);
        }

        console.log("Found province:", foundProvince);
        return foundProvince;
    }

    async function finddistrictById(ditristId) {
        const allditrists = await getAlldistricts();
        const foundditrist = allditrists.find(district => district.DistrictID === ditristId);
        if (!foundditrist) {
            throw new Error(`Không tìm thấy Quận Huyện với ID ${ditristId}`);
        }
        return foundditrist;
    }

    async function findwardById(wardCode, ditristId) {
        const allWards = await getAllward(ditristId);
        const foundWard = allWards.find(ward => ward.WardCode === wardCode);
        if (!foundWard) {
            throw new Error(`Không tìm thấy Xã Phường với WardCode ${wardCode}`);
        }
        return foundWard;
    }

    async function main() {
        try {
            const allProvinces = await getAllProvinces();
            const tinhThanhPhoSelect = document.getElementById('tinhThanhPho');
            allProvinces.forEach(province => {
                const option = document.createElement('option');
                option.value = province.ProvinceID;
                option.text = province.ProvinceName;
                tinhThanhPhoSelect.add(option);
            });
            // updateDistricts();
            // updateWards();
            const elements = document.querySelectorAll('.form-checked');
            elements.forEach(async function (element) {
                const spanElement = element.querySelector('span');
                const thongtins = element.querySelector('h6')
                // Lấy giá trị từ thẻ span sử dụng innerText
                const addressInfo = spanElement.innerText;
                // Tách giá trị thành mảng sử dụng dấu ' | ' làm dấu phân cách
                const addressArray = addressInfo.split(' | ');

                // Lấy giá trị tương ứng
                const hoVaTen = addressArray[0];
                const tinhThanhPho = addressArray[1];
                const huyenQuan = addressArray[2];
                const xaPhuong = addressArray[3];
                const diaChiCuThe = addressArray[4];
                const sdtNguoiNhan = addressArray[5];

                const provinceData = await findProvinceById(parseInt(tinhThanhPho, 10));
                const tinhtp = provinceData.ProvinceName;

                const dtristData = await finddistrictById(parseInt(huyenQuan, 10));
                const huyenq = dtristData.DistrictName;

                const wardData = await findwardById(xaPhuong, parseInt(huyenQuan, 10));
                const phuongx = wardData.WardName;
                // Xử lý giá trị theo nhu cầu của bạn
                const concatenatedString = hoVaTen + ',' + tinhtp + ',' + huyenq + ',' + phuongx + ',' + diaChiCuThe + ',' + sdtNguoiNhan;
                thongtins.textContent = concatenatedString;
                console.log(thongtins.textContent);
            });

            // ...
        } catch (error) {
            console.error(error.message);
        }

    }

    document.getElementById('tinhThanhPho').addEventListener('change', function () {
        updateDistricts();
        updateWards();
    });

    document.getElementById('huyenQuan').addEventListener('change', function () {
        updateWards();
    });

    async function updateDistricts() {
        const selectedProvinceId = document.getElementById('tinhThanhPho').value;
        const allDistricts = await fetchData(apiUrl, `district?province_id=${selectedProvinceId}`);
        const huyenQuanSelect = document.getElementById('huyenQuan');
        huyenQuanSelect.innerHTML = ''; // Xóa các option cũ
        allDistricts.forEach(district => {
            const option = document.createElement('option');
            option.value = district.DistrictID;
            option.text = district.DistrictName;
            huyenQuanSelect.add(option);
        });
    }

    async function updateWards() {
        const selectedDistrictId = document.getElementById('huyenQuan').value;
        const allWards = await fetchData(apiUrl, `ward?district_id=${selectedDistrictId}`);
        const xaPhuongSelect = document.getElementById('xaPhuong');
        xaPhuongSelect.innerHTML = ''; // Xóa các option cũ
        allWards.forEach(ward => {
            const option = document.createElement('option');
            option.value = ward.WardCode;
            option.text = ward.WardName;
            xaPhuongSelect.add(option);
        });
    }
});

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

    // kiểm tra định dạng số điện thoại
    var soDienThoaiNhanHang = document.getElementsByName("soDienThoaiNhanHang")[0].value.trim();

    // Kiểm tra định dạng số điện thoại Việt Nam
    var phoneNumberRegex = /^(0[1-9]|10|11|12|13|14|15|16|17|18|19|2[0-9]|3[0-9]|4[0-9]|5[0-9]|6[0-9]|7[0-9]|8[0-9]|9[0-9])+([0-9]{8})\b/;
    if (!phoneNumberRegex.test(soDienThoaiNhanHang)) {
        alert("Số điện thoại không hợp lệ. Vui lòng nhập theo định dạng Việt Nam.");
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


