
document.getElementById("notification-icon").addEventListener("click", function () {
    var notificationPopup = document.getElementById("notification-popup");
    if (notificationPopup.style.display === "block") {
        notificationPopup.style.display = "none";
    } else {
        notificationPopup.style.display = "block";
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
showTab("tat-ca");

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