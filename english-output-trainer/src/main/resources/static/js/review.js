document.addEventListener("DOMContentLoaded", () => {

    // 評価・難易度のチェックボックス取得
    //const checkboxes = document.querySelectorAll(
    //    "input[name='evaluations'], input[name='difficulties']"
    //);
    
    const conditions = document.querySelectorAll(
    "input[name='evaluations'], " +
    "input[name='difficulties'], " +
    "input[name='favoriteCondition']"
	);

    // 出題数表示
    const countArea = document.getElementById("countReviewQuestions");

    // 件数取得
    async function updateCount() {

        const params = new URLSearchParams();

        // 評価
        document
            .querySelectorAll("input[name='evaluations']:checked")
            .forEach(cb => {
                params.append("evaluations", cb.value);
            });

        // 難易度
        document
            .querySelectorAll("input[name='difficulties']:checked")
            .forEach(cb => {
                params.append("difficulties", cb.value);
            });
            
        // お気に入り条件
		params.append(
		    "favoriteCondition",
		    document.querySelector(
		        "input[name='favoriteCondition']:checked"
		    ).value
		);

        const response = await fetch("/review/count?" + params);

        const count = await response.text();

        countArea.textContent = count + "問";
    }

    // チェック変更時
    //checkboxes.forEach(cb => {
    //    cb.addEventListener("change", updateCount);
    //});
    
    conditions.forEach(input => {
	    input.addEventListener("change", updateCount);
	});
    
        // 初回表示時にも件数を取得
    updateCount();

});