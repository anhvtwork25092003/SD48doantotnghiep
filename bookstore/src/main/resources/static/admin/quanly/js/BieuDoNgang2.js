var data = [
    ['Sản phẩm', 'Số lượng bán'],
    ['Sản phẩm A', 50],
    ['Sản phẩm B', 45],
    ['Sản phẩm C', 30],
    ['Sản phẩm D', 25],
    ['Sản phẩm E', 20]
];

// Load thư viện Google Charts
google.charts.load('current', {'packages':['bar']});

// Khi thư viện đã được load, tạo và vẽ biểu đồ
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
    var dataTable = google.visualization.arrayToDataTable(data);

    var options = {
        chart: {
            title: 'Sản phẩm Bán chạy nhất',
        },
        legend: { position: 'none' },
        bars: 'horizontal' // Chọn kiểu biểu đồ cột ngang
    };

    var chart = new google.charts.Bar(document.getElementById('chart_ngang2'));

    chart.draw(dataTable, google.charts.Bar.convertOptions(options));
}