const pageForm = $("#pageForm");

const pageUL = $(".pagination");


function addButtonEvent() {
    pageUL.on("click", "a", function (e) {
        e.preventDefault();
        const pageNum = $(this).attr('href');
        $("input[name='pageNum']").val(pageNum);

        pageForm.attr("action", "/board/list");
        pageForm.submit();
    });

    $(".btnSearch").on("click", function (e) {
        e.preventDefault();

        const selectObj = $("select[name='selectType']");
        const selectValue = selectObj.val();
        const arr = selectValue.split("");

        let str = '';
        for (const type of arr) {
            str += `<input type="hidden" name="types" value="${type}">`;
        }

        const keywordValue = $("input[name='keyword']").val();
        str += `<input type="hidden" name="keyword" value="${keywordValue}">`;

        str += `<input type="hidden" name="pageNum" value="1">`;
        str += `<input type="hidden" name="amount" value="10">`;

        pageForm.html(str);
        pageForm.submit();
    });
}


$(function () {
    addButtonEvent();
});