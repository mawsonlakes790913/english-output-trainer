document.addEventListener("DOMContentLoaded", function () {

    const detailButtons = document.querySelectorAll(".btn-outline-primary");

    detailButtons.forEach(function(button) {

        button.addEventListener("click", function() {

            const japanese = button.dataset.japanese;
            const english = button.dataset.english;
            const alternative = button.dataset.alternative;

            document.getElementById("modalJapanese").textContent = japanese;
            document.getElementById("modalEnglish").textContent = english;

            const alternativeArea = document.getElementById("modalAlternativeArea");

            if (alternative) {
                document.getElementById("modalAlternative").textContent = alternative;
                alternativeArea.style.display = "";
            } else {
                alternativeArea.style.display = "none";
            }

        });

    });

});