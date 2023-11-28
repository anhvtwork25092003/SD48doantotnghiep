document.addEventListener('DOMContentLoaded', function () {
    const apiKey = 'c3e9d4a4-6352-11ee-a59f-a260851ba65c';
    const apiUrl = 'https://online-gateway.ghn.vn/shiip/public-api/master-data/';

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

            const elements = document.querySelectorAll('tr');

            elements.forEach(async function (element) {
                const tinhThanhPhoc = element.querySelector('#tinhThanhPhoht');
                const tinhThanhPhoid = parseInt(tinhThanhPhoc.textContent.trim(), 10);

                const huyenQuanc = element.querySelector('#huyenQuanht');
                const huyenQuanid = parseInt(huyenQuanc.textContent.trim(), 10);

                const xaPhuongc = element.querySelector('#xaPhuonght');
                const xaPhuongid = xaPhuongc.textContent.trim();

                const provinceData = await findProvinceById(tinhThanhPhoid);
                tinhThanhPhoc.textContent = provinceData.ProvinceName;

                const dtristData = await finddistrictById(huyenQuanid);
                huyenQuanc.textContent = dtristData.DistrictName;

                const wardData = await findwardById(xaPhuongid, huyenQuanid);
                xaPhuongc.textContent = wardData.WardName;

            });
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

    main();
});
