document.addEventListener("DOMContentLoaded", function () {

    // ======================
    // CSRF情報
    // ======================

    const csrfToken =
        document.querySelector(
            'meta[name="_csrf"]'
        ).content;

    const csrfHeader =
        document.querySelector(
            'meta[name="_csrf_header"]'
        ).content;


    // ======================
    // 詳細モーダル
    // ======================

    const detailButtons =
        document.querySelectorAll(".detailButton");

    detailButtons.forEach(function (button) {

        button.addEventListener("click", function () {

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


    // ======================
    // Evaluation変更
    // ======================

    let currentQuestionId = null;

    const evaluationButtons =
        document.querySelectorAll(".evaluationButton");

    evaluationButtons.forEach(function (button) {

        button.addEventListener("click", function () {

            currentQuestionId =
                button.dataset.questionId;

        });

    });

    const evaluationSelectButtons =
        document.querySelectorAll(".evaluationSelect");

    evaluationSelectButtons.forEach(function (button) {

        button.addEventListener("click", function () {

            const evaluation =
                button.dataset.evaluation;

            if (currentQuestionId === null) {

                console.error(
                    "問題IDを取得できませんでした"
                );

                return;

            }

            fetch("/evaluation/toggle", {

                method: "POST",

                headers: {
                    "Content-Type":
                        "application/x-www-form-urlencoded",

                    [csrfHeader]:
                        csrfToken
                },

                body:
                    "questionId=" +
                    encodeURIComponent(currentQuestionId) +
                    "&evaluation=" +
                    encodeURIComponent(evaluation)

            })
            .then(function (response) {

                if (!response.ok) {

                    throw new Error(
                        "理解度の更新に失敗しました: " +
                        response.status
                    );

                }

                location.reload();

            })
            .catch(function (error) {

                console.error(error);

            });

        });

    });


    // ======================
    // お気に入り登録・解除
    // ======================

    const favoriteButtons =
        document.querySelectorAll(".favoriteButton");

    favoriteButtons.forEach(function (button) {

        button.addEventListener("click", function () {

            const questionId =
                button.dataset.questionId;

            const favoriteIcon =
                button.querySelector("i");

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
            .then(function (response) {

                if (!response.ok) {

                    throw new Error(
                        "お気に入り更新失敗"
                    );

                }

                return response.text();

            })
            .then(function (result) {

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
            .catch(function (error) {

                console.error(error);

            });

        });

    });


    // ======================
    // 検索条件制御
    // ======================

    const studyCondition =
        document.getElementById("studyCondition");

    const evaluations =
        document.querySelectorAll(
            "input[name='evaluations']"
        );

    if (studyCondition) {

        function updateEvaluationState() {

            const unlearned =
                studyCondition.value ===
                "UNLEARNED_ONLY";

            evaluations.forEach(function (cb) {

                if (unlearned) {

                    cb.checked = false;
                    cb.disabled = true;

                } else {

                    cb.disabled = false;

                }

            });

        }

        studyCondition.addEventListener(
            "change",
            updateEvaluationState
        );

        updateEvaluationState();

    }

});