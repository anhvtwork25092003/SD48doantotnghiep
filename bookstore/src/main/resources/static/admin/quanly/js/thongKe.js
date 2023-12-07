// Gọi API để lấy dữ liệu doanh thu
fetch('/api/doanh-thu/thang-hien-tai')
    .then(response => response.json())
    .then(data => {
        // Chuyển đổi dữ liệu thành mảng các ngày và doanh thu tương ứng
        const labels = Object.keys(data);
        const values = Object.values(data);

        // Vẽ biểu đồ cột đứng
        const ctx = document.getElementById('doanhThuChart').getContext('2d');
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Doanh thu',
                    data: values,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    });
// chartScript.js

document.addEventListener("DOMContentLoaded", function () {
    var ctx = document.getElementById('topSanPhamChart').getContext('2d');

    fetch('/api/doanh-thu/ban-chay-nhat')
        .then(response => response.json())
        .then(data => {
            var tenSanPhamArray = data.map(function (item) {
                return item.tenSanPham;
            });

            var soLuongBanArray = data.map(function (item) {
                return item.soLuong;
            });

            var topSanPhamChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: tenSanPhamArray,
                    datasets: [{
                        label: 'Số Lượng Bán',
                        data: soLuongBanArray,
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        })
        .catch(error => console.error('Error fetching data:', error));
});

