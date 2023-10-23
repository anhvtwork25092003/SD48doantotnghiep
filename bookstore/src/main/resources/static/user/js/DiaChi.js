// 1. what is API
// 2. How do I call API
// 3. Explain code
const host = "https://provinces.open-api.vn/api/";
var callAPI = (api) => {
    return axios.get(api)
        .then((response) => {
            renderData(response.data, "province");
        });
}
callAPI('https://provinces.open-api.vn/api/?depth=1');
var callApiDistrict = (api) => {
    return axios.get(api)
        .then((response) => {
            renderData(response.data.districts, "district");
        });
}
var callApiWard = (api) => {
    return axios.get(api)
        .then((response) => {
            renderData(response.data.wards, "ward");
        });
}

var renderData = (array, select) => {
    let row = ' <option disable value=""></option>';
    array.forEach(element => {
        row += `<option value="${element.code}">${element.name}</option>`
    });
    document.querySelector("#" + select).innerHTML = row
}

$("#province").change(() => {
    callApiDistrict(host + "p/" + $("#province").val() + "?depth=2");
    printResult();
});
$("#district").change(() => {
    callApiWard(host + "d/" + $("#district").val() + "?depth=2");
    printResult();
});
$("#ward").change(() => {
    printResult();
})

// var printResult = () => {
//     if ($("#district").val() != "" && $("#province").val() != "" &&
//         $("#ward").val() != "") {
//         let result = $("#province option:selected").text() +
//             " | " + $("#district option:selected").text() + " | " +
//             $("#ward option:selected").text();
//         $("#result").text(result)
//     }
// }


// document.getElementById("paymentButton").addEventListener("click", function() {
//     // Hiển thị hộp thoại xác nhận
//     var confirmation = confirm("Bạn có chắc chắn muốn thanh toán không?");
    
//     // Kiểm tra xem người dùng đã nhấn "OK" hay "Hủy"
//     if (confirmation) {
//         // Người dùng đã nhấn "OK", thực hiện hành động thanh toán ở đây
//         alert("Đã thanh toán!");
//     } else {
//         // Người dùng đã nhấn "Hủy", không thực hiện hành động nào
//         alert("Thanh toán đã bị hủy bỏ.");
//     }
// });