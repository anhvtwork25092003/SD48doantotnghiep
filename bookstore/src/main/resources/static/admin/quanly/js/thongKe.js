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
