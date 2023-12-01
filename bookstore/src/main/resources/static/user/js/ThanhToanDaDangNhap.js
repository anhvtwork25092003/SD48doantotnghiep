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
            const elements = document.querySelectorAll('.form-check');
            elements.forEach( async function (element) {
                const spanElement = document.getElementById('spann'); // Chọn thẻ span bên trong element
                const thongtins= document.getElementById('thongtinnhan')
                // Lấy giá trị từ thẻ span sử dụng innerText
                const addressInfo = spanElement.innerText;
console.log(addressInfo);
                // Tách giá trị thành mảng sử dụng dấu ' | ' làm dấu phân cách
                const addressArray = addressInfo.split(' | ');

                // Lấy giá trị tương ứng
                const hoVaTen = addressArray[0];
                const tinhThanhPho = addressArray[1];
                const huyenQuan = addressArray[2];
                const xaPhuong = addressArray[3];
                const diaChiCuThe = addressArray[4];
                const sdtNguoiNhan = addressArray[5];

                const provinceData = await findProvinceById(parseInt(tinhThanhPho,10));
                const tinhtp = provinceData.ProvinceName;

                const dtristData = await finddistrictById(parseInt(huyenQuan,10));
                const huyenq = dtristData.DistrictName;

                const wardData = await findwardById(xaPhuong,parseInt(huyenQuan,10));
                const phuongx = wardData.WardName;
                // Xử lý giá trị theo nhu cầu của bạn
                const concatenatedString= hoVaTen +','+tinhtp +','+ huyenq + ','  + phuongx + ',' + diaChiCuThe + ',' + sdtNguoiNhan ;
                console.log(concatenatedString)
                thongtins.textContent=concatenatedString;
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
