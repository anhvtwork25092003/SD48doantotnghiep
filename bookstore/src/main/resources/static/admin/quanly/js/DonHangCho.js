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
            const elements = document.querySelectorAll('.form-check');
            elements.forEach(async function (element) {
                const tinhThanhPhoc = document.getElementById('tinhThanhPhoc');

                const tinhThanhPhoid = parseInt(tinhThanhPhoc.value, 10);
                console.log(tinhThanhPhoid);
                const huyenQuanc = document.getElementById('huyenQuanc');
                const huyenQuanid = parseInt(huyenQuanc.value .trim(), 10);

                const xaPhuongc = document.getElementById('xaPhuongc');
                const xaPhuongid = xaPhuongc.value .trim();

                const provinceData = await findProvinceById(tinhThanhPhoid);
                tinhThanhPhoc.value  = provinceData.ProvinceName;
                console.log(tinhThanhPhoc.textContent )
                const dtristData = await finddistrictById(huyenQuanid);
                huyenQuanc.value  = dtristData.DistrictName;

                const wardData = await findwardById(xaPhuongid, huyenQuanid);
                xaPhuongc.value  = wardData.WardName;

            });
        } catch (error) {
            console.error(error.message);
        }

    }

    main();
});
