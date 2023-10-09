$('a.remove').click(function () {
    event.preventDefault();
    $(this).closest('.items').hide(400);
    calculateTotal();
});

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

var incrementButtons = document.querySelectorAll('.increment');
for (var i = 0; i < incrementButtons.length; i++) {
    incrementButtons[i].addEventListener('click', function () {
        var quantityInput = this.parentNode.querySelector('.qty');
        var currentQuantity = parseInt(quantityInput.value);
        quantityInput.value = currentQuantity + 1;
        updateSubtotal(this.closest('.items'));
        calculateTotal();
    });
}

var decrementButtons = document.querySelectorAll('.decrement');
for (var i = 0; i < decrementButtons.length; i++) {
    decrementButtons[i].addEventListener('click', function () {
        var quantityInput = this.parentNode.querySelector('.qty');
        var currentQuantity = parseInt(quantityInput.value);
        if (currentQuantity > 1) {
            quantityInput.value = currentQuantity - 1;
            updateSubtotal(this.closest('.items'));
            calculateTotal();
        }
    });
}

function updateSubtotal(item) {
    var price = parseFloat(item.querySelector('.price').textContent.slice(1));
    var quantity = parseInt(item.querySelector('.qty').value);
    var subtotal = price * quantity;
    item.querySelector('.subtotal').textContent = '$' + subtotal.toFixed(2);
}

var itemCheckboxes = document.querySelectorAll('.item-checkbox');
for (var i = 0; i < itemCheckboxes.length; i++) {
    itemCheckboxes[i].addEventListener('change', function () {
        calculateTotal();
    });
}

function calculateTotal() {
    var checkboxes = document.querySelectorAll('.item-checkbox');
    var subtotal = 0;

    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            var row = checkboxes[i].parentNode.parentNode;
            var price = parseFloat(row.querySelector('.price').textContent.slice(1));
            var quantity = parseInt(row.querySelector('.qty').value);
            var rowSubtotal = price * quantity;
            subtotal += rowSubtotal;
        }
    }
    document.querySelector('.subtotal .value').textContent = '$' + subtotal.toFixed(2);
}
