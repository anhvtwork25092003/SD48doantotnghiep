
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

