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

function myFunction(smallImg){
    var fullImg =document.getElementById("imageBox")
    fullImg.src =smallImg.src;
}