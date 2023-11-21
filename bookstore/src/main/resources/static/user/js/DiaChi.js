document.addEventListener('DOMContentLoaded', function () {
    const apiKey = 'c3e9d4a4-6352-11ee-a59f-a260851ba65c';
    const provinceApiUrl = 'https://online-gateway.ghn.vn/shiip/public-api/master-data/province';
    const districtApiUrl = 'https://online-gateway.ghn.vn/shiip/public-api/master-data/district';
    const wardApiUrl = 'https://online-gateway.ghn.vn/shiip/public-api/master-data/ward';

    const provinceSelect = document.getElementById('tinhThanhPho');
    const districtSelect = document.getElementById('huyenQuan');
    const wardSelect = document.getElementById('xaPhuong');

    const modalProvinceSelect = document.getElementById('modalTinhThanhPho');
    const modalDistrictSelect = document.getElementById('modalHuyenQuan');
    const modalWardSelect = document.getElementById('modalXaPhuong');

    // Hàm fetch danh sách Tỉnh/Thành phố
    function fetchProvinces(apiUrl, array, selectId) {
        fetch(apiUrl, {
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
                array.length = 0;
                data.data.forEach((province) => {
                    array.push({ value: province.ProvinceID, text: province.ProvinceName });
                });
                populateSelect(selectId, array);
            })
            .catch((error) => {
                console.error('Lỗi khi lấy danh sách Tỉnh/Thành phố:', error);
            });
    }

// Hàm fetch danh sách Quận/Huyện
    function fetchDistricts(apiUrl, array, selectId, provinceId) {
        fetch(`${apiUrl}?province_id=${provinceId}`, {
            method: 'GET',
            headers: {
                'token': apiKey,
                'Content-Type': 'application/json',
            },
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`Yêu cầu API Quận/Huyện thất bại. Mã lỗi: ${response.status}`);
                }
                return response.json();
            })
            .then((data) => {
                array.length = 0;
                data.data.forEach((district) => {
                    array.push({ value: district.DistrictID, text: district.DistrictName });
                });
                populateSelect(selectId, array);
            })
            .catch((error) => {
                console.error('Lỗi khi lấy danh sách Quận/Huyện:', error);
            });
    }

// Hàm fetch danh sách Phường/Xã
    function fetchWards(apiUrl, array, selectId, districtId) {
        fetch(`${apiUrl}?district_id=${districtId}`, {
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
                array.length = 0;
                data.data.forEach((ward) => {
                    array.push({ value: ward.WardID, text: ward.WardName });
                });
                populateSelect(selectId, array);
            })
            .catch((error) => {
                console.error('Lỗi khi lấy danh sách Phường/Xã:', error);
            });
    }


    function populateSelect(selectId, data) {
        const selectElement = document.getElementById(selectId);
        selectElement.innerHTML = '';
        data.forEach((item) => {
            const option = document.createElement('option');
            option.value = item.value;
            option.textContent = item.text;
            selectElement.appendChild(option);
        });
    }

    const provinces = [];
    const districts = [];
    const wards = [];

    fetchProvinces(provinceApiUrl, provinces, 'tinhThanhPho');
    provinceSelect.addEventListener('change', function () {
        const selectedProvinceId = provinceSelect.value;
        console.log(selectedProvinceId);
        fetchDistricts(districtApiUrl, districts, 'huyenQuan',selectedProvinceId);
    });

    districtSelect.addEventListener('change', function () {
        const selectedDistrictId = districtSelect.value;
        console.log(selectedDistrictId);
        fetchWards(wardApiUrl, wards, 'xaPhuong',selectedDistrictId);
    });





    fetchProvinces(provinceApiUrl, provinces, 'modalTinhThanhPho');

    modalProvinceSelect.addEventListener('change', function () {
        const selectedProvinceId = modalProvinceSelect.value;
        fetchDistricts(districtApiUrl, districts, 'huyenQuan',selectedProvinceId);
    });

    modalDistrictSelect.addEventListener('change', function () {
        const selectedDistrictId = modalDistrictSelect.value;
        fetchWards(wardApiUrl, wards, 'xaPhuong',selectedDistrictId);
    });
});