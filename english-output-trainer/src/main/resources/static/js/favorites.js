document.addEventListener("DOMContentLoaded", function () {

    const detailButtons =
        document.querySelectorAll(".detailButton");

    detailButtons.forEach(function(button) {

        button.addEventListener("click", function() {

            document.getElementById("modalJapanese").textContent =
                button.dataset.japanese;

            document.getElementById("modalEnglish").textContent =
                button.dataset.english;

            const alternativeArea =
                document.getElementById("modalAlternativeArea");

            if (button.dataset.alternative) {

                document.getElementById("modalAlternative").textContent =
                    button.dataset.alternative;

                alternativeArea.style.display = "";

            } else {

                document.getElementById("modalAlternative").textContent = "";

                alternativeArea.style.display = "none";

            }

        });

    });

});