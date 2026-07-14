document.addEventListener("DOMContentLoaded", function () {

    const beginnerRange =
        document.getElementById("beginnerRange");

    const intermediateRange =
        document.getElementById("intermediateRange");

    const advancedRange =
        document.getElementById("advancedRange");

    beginnerRange.addEventListener("change", function () {

        if (beginnerRange.value !== "") {
            intermediateRange.value = "";
            advancedRange.value = "";
        }

    });

    intermediateRange.addEventListener("change", function () {

        if (intermediateRange.value !== "") {
            beginnerRange.value = "";
            advancedRange.value = "";
        }

    });

    advancedRange.addEventListener("change", function () {

        if (advancedRange.value !== "") {
            beginnerRange.value = "";
            intermediateRange.value = "";
        }

    });

});