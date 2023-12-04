document.getElementById("notification-icon").addEventListener("click", function () {
    var notificationPopup = document.getElementById("notification-popup");
    if (notificationPopup.style.display === "block") {
        notificationPopup.style.display = "none";
    } else {
        notificationPopup.style.display = "block";

        // Gọi AJAX khi hiển thị popup để lấy dữ liệu thông báo
        $.ajax({
            type: "GET",
            url: "/api/thong-bao/load-thong-bao",
            success: function(data) {
                // Hiển thị thông báo với dữ liệu từ AJAX
                displayNotifications(data);
                showTab("thong-bao");
            },
            error: function(xhr, status, error) {
                console.log("AJAX error: " + status + ", " + error);
            }
        });
    }
});

var tabs = document.getElementsByClassName("notification-content");
function showTab(tabId) {
    for (var i = 0; i < tabs.length; i++) {
        tabs[i].style.display = "none";
    }

    var selectedTab = document.getElementById(tabId);
    if (selectedTab) {
        selectedTab.style.display = "block";
    }
}

function displayNotifications(data) {
    data.reverse();
    // Xử lý dữ liệu và thêm vào tab "Thông báo"
    var thongBaoList = document.getElementById("thong-bao-list");
    thongBaoList.innerHTML = ""; // Xóa nội dung cũ trước khi thêm mới
    data.forEach(function(thongBao) {
        console.log(thongBao.noiDung)
        var li = document.createElement("li");
        var p = document.createElement("p");
        p.textContent = thongBao.noiDung;
        li.appendChild(p);
        thongBaoList.appendChild(li);
    });
}


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