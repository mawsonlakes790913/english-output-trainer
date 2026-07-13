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

// =========================
// お気に入り登録・解除
// =========================
const favoriteButtons =
    document.querySelectorAll(".favoriteButton");

favoriteButtons.forEach(function(button) {

    button.addEventListener("click", function() {

        const questionId =
            button.dataset.questionId;

        const favoriteIcon =
            button.querySelector("i");

        const csrfToken =
            document.querySelector(
                'meta[name="_csrf"]'
            ).content;

        const csrfHeader =
            document.querySelector(
                'meta[name="_csrf_header"]'
            ).content;

        fetch("/favorite/toggle", {

            method: "POST",

            headers: {
                "Content-Type":
                    "application/x-www-form-urlencoded",

                [csrfHeader]:
                    csrfToken
            },

            body:
                "questionId=" +
                encodeURIComponent(questionId)

        })
        .then(function(response) {

            if (!response.ok) {

                throw new Error(
                    "お気に入り更新失敗"
                );

            }

            return response.text();

        })
        .then(function(result) {

            if (result === "true") {

                favoriteIcon.classList.remove(
                    "bi-heart",
                    "text-secondary"
                );

                favoriteIcon.classList.add(
                    "bi-heart-fill",
                    "text-danger"
                );

            } else {

                favoriteIcon.classList.remove(
                    "bi-heart-fill",
                    "text-danger"
                );

                favoriteIcon.classList.add(
                    "bi-heart",
                    "text-secondary"
                );

            }

        })
        .catch(function(error) {

            console.error(error);

        });

    });

});