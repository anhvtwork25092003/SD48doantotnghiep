var dailyData = [
    ['Ngày', 'Doanh thu'],
    ['2023-10-01', 1000],
    ['2023-10-02', 1200],
    ['2023-10-03', 800],
    // ...
];

var monthlyData = [
    ['Tháng', 'Doanh thu'],
    ['2023-10', 25000],
    ['2023-11', 28000],
    ['2023-12', 30000],
    // ...
];

// Load thư viện Google Charts
google.charts.load('current', {'packages':['corechart']});

// Khi thư viện đã được load, tạo và vẽ biểu đồ
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
    var chartData;
    var dateRange = document.getElementById('dateRange').value;

    if (dateRange === 'ngay') {
        chartData = dailyData;
    } else {
        chartData = monthlyData;
    }

    var dataTable = google.visualization.arrayToDataTable(chartData);

    var options = {
        chart: {
            title: 'Biểu đồ Doanh thu',
        },
        legend: { position: 'none' },
        bars: 'vertical' // Chọn kiểu biểu đồ cột đứng
    };

    var chart = new google.visualization.ColumnChart(document.getElementById('chart_cot'));

    chart.draw(dataTable, options);
};


////
