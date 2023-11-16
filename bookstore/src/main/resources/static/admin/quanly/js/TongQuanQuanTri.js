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
$(document).ready(function() {
    var accountDropdown = $(".account-dropdown");

    $(".account-icon").on("click", function(e) {
        e.preventDefault();
        if (accountDropdown.is(":visible")) {
            accountDropdown.hide();
        } else {
            accountDropdown.show();
        }
    });

    // Đặt sự kiện để ẩn dropdown khi click bất kì nơi nào trên trang
    $(document).on("click", function(event) {
        if (!$(event.target).closest(".account-icon").length) {
            accountDropdown.hide();
        }
    });

    // Ngăn sự kiện click trên dropdown từ việc lan ra ngoài
    accountDropdown.on("click", function(event) {
        event.stopPropagation();
    });
});

////
