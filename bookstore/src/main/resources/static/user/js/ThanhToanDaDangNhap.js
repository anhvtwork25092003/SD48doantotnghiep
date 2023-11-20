document.addEventListener('DOMContentLoaded', function () {
    const apiKey = 'c3e9d4a4-6352-11ee-a59f-a260851ba65c';

    const provinceApiUrl = 'https://online-gateway.ghn.vn/shiip/public-api/master-data/province';
    const districtApiUrl = 'https://online-gateway.ghn.vn/shiip/public-api/master-data/district';
    const wardApiUrl = 'https://online-gateway.ghn.vn/shiip/public-api/master-data/ward';

    const provinceSelect = document.getElementById('tinhThanhPho');
    const districtSelect = document.getElementById('huyenQuan');
    const wardSelect = document.getElementById('xaPhuong');

// Thêm tùy chọn mặc định cho Tỉnh/Thành phố
    const defaultProvinceOption = document.createElement('option');
    defaultProvinceOption.value = '';
    defaultProvinceOption.textContent = 'Chọn Tỉnh/Thành phố...';
    provinceSelect.appendChild(defaultProvinceOption);

// Lấy danh sách Tỉnh/Thành phố từ API
    fetch(provinceApiUrl, {
        method: 'GET',
        headers: {
            'token': apiKey,
            'Content-Type': 'application/json',
        },
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error(`Yêu cầu API Tỉnh/Thành phố thất bại. Mã lỗi: ${response.status}`);
            }
            return response.json();
        })
        .then((data) => {
            data.data.forEach((province) => {
                const option = document.createElement('option');
                option.value = province.ProvinceID;
                option.textContent = province.ProvinceName;
                provinceSelect.appendChild(option);
            });
        })
        .catch((error) => {
            console.error('Lỗi khi lấy danh sách Tỉnh/Thành phố:', error);
        });

// Sự kiện khi chọn Tỉnh/Thành phố
    provinceSelect.addEventListener('change', function () {
        const selectedProvinceId = provinceSelect.value;

        // Xóa tất cả các tùy chọn hiện tại của Quận/Huyện và Phường/Xã
        districtSelect.innerHTML = '';
        wardSelect.innerHTML = '';

        // Thêm tùy chọn mặc định cho Quận/Huyện
        const defaultDistrictOption = document.createElement('option');
        defaultDistrictOption.value = '';
        defaultDistrictOption.textContent = 'Chọn Quận/Huyện...';
        districtSelect.appendChild(defaultDistrictOption);

        // Lấy danh sách Quận/Huyện từ API dựa trên Tỉnh/Thành phố được chọn
        fetch(`${districtApiUrl}?province_id=${selectedProvinceId}`, {
            method: 'GET',
            headers: {
                'token': apiKey,
                'Content-Type': 'application/json',
            }
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`Yêu cầu API Quận/Huyện thất bại. Mã lỗi: ${response.status}`);
                }
                return response.json();
            })
            .then((data) => {
                data.data.forEach((district) => {
                    const option = document.createElement('option');
                    option.value = district.DistrictID;
                    option.textContent = district.DistrictName;
                    districtSelect.appendChild(option);
                });
            })
            .catch((error) => {
                console.error('Lỗi khi lấy danh sách Quận/Huyện:', error);
            });
    });

// Sự kiện khi chọn Quận/Huyện
    districtSelect.addEventListener('change', function () {
        const selectedDistrictId = districtSelect.value;

        // Xóa tất cả các tùy chọn hiện tại của Phường/Xã
        wardSelect.innerHTML = '';

        // Thêm tùy chọn mặc định cho Phường/Xã
        const defaultWardOption = document.createElement('option');
        defaultWardOption.value = '';
        defaultWardOption.textContent = 'Chọn Phường/Xã...';
        wardSelect.appendChild(defaultWardOption);

        // Lấy danh sách Phường/Xã từ API dựa trên Quận/Huyện được chọn
        fetch(`${wardApiUrl}?district_id=${selectedDistrictId}`, {
            method: 'GET',
            headers: {
                'token': apiKey,
                'Content-Type': 'application/json',
            },
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`Yêu cầu API Phường/Xã thất bại. Mã lỗi: ${response.status}`);
                }
                return response.json();
            })
            .then((data) => {
                data.data.forEach((ward) => {
                    const option = document.createElement('option');
                    option.value = ward.WardID;
                    option.textContent = ward.WardName;
                    wardSelect.appendChild(option);
                });
            })
            .catch((error) => {
                console.error('Lỗi khi lấy danh sách Phường/Xã:', error);
            });
    });

});

