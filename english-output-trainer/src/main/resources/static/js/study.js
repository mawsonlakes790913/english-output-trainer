function showAnswer() {
    console.log("showAnswer called");


    document.getElementById("answerArea").style.display = "block";

    document.getElementById("evaluationArea").style.display = "flex";

    document.getElementById("answerButton").style.display = "none";
    
}


const favoriteButton = document.getElementById("favoriteButton");

if (favoriteButton) {
    favoriteButton.addEventListener("click", function () {

        const questionId = favoriteButton.dataset.questionId;

        console.log(questionId);

    });
}