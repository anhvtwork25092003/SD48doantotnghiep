document.addEventListener("DOMContentLoaded", function() {
    const thongBaoList = document.getElementById("thongbao-list");

    // Hàm để lấy thông báo từ server và cập nhật trang
    function fetchAndDisplayThongBao() {
        // Lấy giá trị userId từ thẻ ẩn
        const userId = document.getElementById("hiddenUserId").value;

        // Gọi API để lấy danh sách thông báo của người dùng
        fetch(`/api/danh-sach-thong-bao/${userId}`)
            .then(response => response.json())
            .then(data => {
                // Xóa thông báo cũ trên trang
                thongBaoList.innerHTML = "";

                // Hiển thị thông báo mới
                data.forEach(thongBao => {
                    const listItem = document.createElement("div");
                    listItem.textContent = thongBao.thongBao.noiDung;
                    thongBaoList.appendChild(listItem);
                });
            })
            .catch(error => console.error("Error:", error));
    }

    // Hàm tự động cập nhật thông báo sau mỗi khoảng thời gian (ví dụ: mỗi 5 giây)
    function autoRefreshThongBao() {
        fetchAndDisplayThongBao();
        setInterval(fetchAndDisplayThongBao, 5000); // Cập nhật mỗi 5 giây
    }

    // Gọi hàm tự động cập nhật
    autoRefreshThongBao();
});
