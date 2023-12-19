
$('a.btn.continue').click(function () {
    $('li.items').show(400);
    calculateTotal();
});

document.getElementById('selectAll').addEventListener('click', function () {
    var checkboxes = document.querySelectorAll('.item-checkbox');
    for (var i = 0; i < checkboxes.length; i++) {
        checkboxes[i].checked = this.checked;
    }
    calculateTotal();
});

var itemCheckboxes = document.querySelectorAll('.item-checkbox');
for (var i = 0; i < itemCheckboxes.length; i++) {
    itemCheckboxes[i].addEventListener('change', function () {
        calculateTotal();
    });
}
// Đợi tất cả các phần tử HTML được tải trước khi thực hiện các công việc JS
document.addEventListener("DOMContentLoaded", function () {
    // Lấy nút chọn tất cả
    var selectAllCheckbox = document.getElementById('selectAll');

    // Lắng nghe sự kiện khi nút chọn tất cả thay đổi
    selectAllCheckbox.addEventListener('change', function () {
        // Lấy tất cả các phần tử checkbox
        var checkboxes = document.querySelectorAll('.item-checkbox');

        // Đặt trạng thái của tất cả các checkbox giống với trạng thái của nút chọn tất cả
        checkboxes.forEach(function (checkbox) {
            checkbox.checked = selectAllCheckbox.checked;
        });

        // Tính toán giá tiền subtotal khi có sự thay đổi
        calculateSubtotal();
    });

    // Lấy tất cả các phần tử checkbox
    var checkboxes = document.querySelectorAll('.item-checkbox');

    // Lắng nghe sự kiện khi checkbox thay đổi
    checkboxes.forEach(function (checkbox) {
        checkbox.addEventListener('change', function () {
            // Tính toán giá tiền subtotal khi có sự thay đổi
            calculateSubtotal();
        });
    });

    // Tính giá tiền subtotal khi trang được tải
    calculateSubtotal();
});

function calculateSubtotal() {
    // Lấy tất cả các phần tử có class 'item-checkbox' và được chọn
    var selectedItems = document.querySelectorAll('.item-checkbox:checked');

    // Khởi tạo biến để lưu tổng giá tiền
    var subtotal = 0;

    // Lặp qua các phần tử được chọn và tính tổng giá tiền
    selectedItems.forEach(function (item) {
        // Lấy giá tiền của sản phẩm
        var price = parseFloat(item.closest('.items').querySelector('.pricesale p').textContent);

        // Lấy số lượng của sản phẩm
        var quantity = parseFloat(item.closest('.items').querySelector('.qty').value);

        // Tính giá tiền của sản phẩm và cộng vào tổng giá tiền
        subtotal += price * quantity;
    });

    // Hiển thị giá tiền subtotal trong phần tổng tiền
    document.querySelector('.subtotal .value').textContent = subtotal.toFixed(3);
}
// Khai báo biến để kiểm tra trạng thái chọn sản phẩm
var isProductSelected = false;

// Lắng nghe sự kiện khi checkbox thay đổi
var checkboxes = document.querySelectorAll('.item-checkbox');
checkboxes.forEach(function (checkbox) {
    checkbox.addEventListener('change', function () {
        // Cập nhật trạng thái của biến khi có sự thay đổi
        isProductSelected = checkIfProductsSelected();
        // Tính toán giá tiền subtotal khi có sự thay đổi
        calculateSubtotal();
    });
});

// Lắng nghe sự kiện khi nút Checkout được nhấn
document.getElementById('checkoutButton').addEventListener('click', function () {
    // Kiểm tra xem có sản phẩm được chọn hay không
    if (!isProductSelected) {
        alert('Vui lòng chọn ít nhất một sản phẩm để thanh toán.');
        // Ngăn chặn hành động mặc định của nút submit nếu không có sản phẩm được chọn
        event.preventDefault();
    }
});

// Hàm kiểm tra xem có sản phẩm nào được chọn hay không
function checkIfProductsSelected() {
    var selectedItems = document.querySelectorAll('.item-checkbox:checked');
    return selectedItems.length > 0;
}

