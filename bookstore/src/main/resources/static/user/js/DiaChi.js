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

});
