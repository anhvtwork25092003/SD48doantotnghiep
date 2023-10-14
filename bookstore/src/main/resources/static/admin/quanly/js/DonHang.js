var selectedEmployee = { maNhanVien: '', hoTen: '' };

$('tr[data-toggle="modal"]').on('click', function() {
  var maNhanVien = $(this).data('ma-nhan-vien');
  var hoTen = $(this).data('ho-ten');
  selectedEmployee = { maNhanVien: maNhanVien, hoTen: hoTen };
});

$('#saveChangesButton').on('click', function() {
  // Cập nhật nút "Open Modal" với thông tin nhân viên khi bạn nhấn nút "Save changes"
  $('.modal').modal('hide'); // Đóng modal hiện tại
  $('#openModalButton').text(selectedEmployee.maNhanVien + ' - ' + selectedEmployee.hoTen);
});