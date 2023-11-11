document.addEventListener("DOMContentLoaded", function() {
    var sachContainer = document.getElementById("sachContainer");
    var themSachButton = document.getElementById("themSachButton");
    var counter = 1;

    themSachButton.addEventListener("click", function(event) {
        event.preventDefault();

        var newSelect = document.createElement("select");
        newSelect.className = "form-control";
        newSelect.name = "sachKM";
        newSelect.id = "sachKM";

        var option = document.createElement("option");
        option.value = "";
        option.text = "Chọn sách";
        newSelect.appendChild(option);

        var xoaSachButton = document.createElement("button");
        xoaSachButton.className = "xoaSachButton";
        xoaSachButton.textContent = "Xóa sách";

        newSelect.addEventListener("change", function() {
            xoaSachButton.removeAttribute("disabled");
        });

        xoaSachButton.addEventListener("click", function() {
            sachContainer.removeChild(newSelect);
            sachContainer.removeChild(xoaSachButton);
        });

        var originalSelect = document.getElementById("sachKM");
        for (var i = 0; i < originalSelect.options.length; i++) {
            var clonedOption = originalSelect.options[i].cloneNode(true);
            newSelect.appendChild(clonedOption);
        }

        sachContainer.appendChild(newSelect);
        sachContainer.appendChild(xoaSachButton);

        counter++;
    });
});


document.addEventListener("DOMContentLoaded", function() {
    var sachContainer = document.getElementById("sachContainer1");
    var themSachButton = document.getElementById("themSachButton1");
    var counter = 1;

    themSachButton.addEventListener("click", function(event) {
        event.preventDefault();

        var newSelect = document.createElement("select");
        newSelect.className = "form-control";
        newSelect.name = "sachKM2";
        newSelect.id = "sachKM2";

        var option = document.createElement("option");
        option.value = "";
        option.text = "Chọn sách";
        newSelect.appendChild(option);

        var xoaSachButton = document.createElement("button");
        xoaSachButton.className = "xoaSachButton";
        xoaSachButton.textContent = "Xóa sách";

        newSelect.addEventListener("change", function() {
            xoaSachButton.removeAttribute("disabled");
        });

        xoaSachButton.addEventListener("click", function() {
            sachContainer.removeChild(newSelect);
            sachContainer.removeChild(xoaSachButton);
        });

        var originalSelect = document.getElementById("sachKM2");
        for (var i = 0; i < originalSelect.options.length; i++) {
            var clonedOption = originalSelect.options[i].cloneNode(true);
            newSelect.appendChild(clonedOption);
        }

        sachContainer.appendChild(newSelect);
        sachContainer.appendChild(xoaSachButton);

        counter++;
    });
});


// document.addEventListener("DOMContentLoaded", function() {
//     var sachContainer = document.getElementById("sachContainer1");
//     var themSachButton = document.getElementById("themSachButton1");
//
//     themSachButton.addEventListener("click", function() {
//         themSach();
//     });
//
//     function themSach() {
//         var originalSelect = document.getElementById("sachKM");
//
//         var newSelect = originalSelect.cloneNode(true);
//         newSelect.id = "sachKM" + Date.now(); // Unique ID for each new select
//
//         var xoaSachButton = document.createElement("button");
//         xoaSachButton.className = "xoaSachButton";
//         xoaSachButton.textContent = "Xóa sách";
//
//         newSelect.addEventListener("change", function() {
//             xoaSachButton.removeAttribute("disabled");
//         });
//
//         xoaSachButton.addEventListener("click", function() {
//             sachContainer.removeChild(newSelect);
//             sachContainer.removeChild(xoaSachButton);
//         });
//
//         sachContainer.appendChild(newSelect);
//         sachContainer.appendChild(xoaSachButton);
//     }
// });







