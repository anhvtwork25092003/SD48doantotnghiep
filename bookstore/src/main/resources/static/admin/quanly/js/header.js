
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

