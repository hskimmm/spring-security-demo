const csrfToken = $("meta[name='_csrf']").attr("content");
const csrfHeader = $("meta[name='_csrf_header']").attr("content");

const bnoData = $("input[name='bno']").val();

const actionForm = $("#actionForm");
const pageForm = $("#pageForm");

function addButtonEvent() {
    $(".listBtn").on("click", function (e) {
        e.preventDefault();
        pageForm.attr("action", "/board/list");
        pageForm.submit();
    });

    $(".modBtn").on("click", function (e) {
        e.preventDefault();
        modifyBoard();
    });

    $(".removeBtn").on("click", function (e) {
        e.preventDefault();
        removerBoard();
    });

    $(".deleteImageBtn").on("click", function (e) {
        e.preventDefault();

        const fno = $(this).attr('data-fno');
        const fullname = $(this).attr('data-fullname');

        let str = '';

        str += `<input type="hidden" name="fnos" value="${fno}">`;
        str += `<input type="hidden" name="fullnames" value="${fullname}">`;

        $(".deleteImages").append(str);
        $(this).closest('div').remove();
    });
}

//유효성검사
function validateInput(formData) {
    const fileSuffix = ['jpg', 'jpeg', 'png', 'gif', 'bmp'];

    for (let [key, value] of formData.entries()) {
        if (value instanceof File) {

            if (value.size === 0 && value.name === '') {
                continue;
            }

            const suffix = value.name.split('.').pop().toLowerCase();

            if (!fileSuffix.includes(suffix)) {
                alert("업로드 불가능한 파일입니다. \n (jpg, jpeg, png, gif, bmp 파일만 업로드 가능합니다)");
                return false;
            }

            continue;
        }

        const trimmedValue = value.trim();

        if (value !== trimmedValue) {
            formData.set(key, trimmedValue);
        }

        if (key === 'title' && trimmedValue === '') {
            alert("제목을 입력하세요")
            $("#title").focus();
            return false;
        } else if (key === 'content' && trimmedValue === '') {
            alert("내용을 입력하세요")
            $("textarea[name='content']").focus();
            return false;
        } else if (key === 'writer' && trimmedValue === '') {
            alert("로그인 후 게시글을 삭제하세요");
            $("#writer").focus();
            return false;
        }
    }
    return true;
}

//게시글 수정
function modifyBoard() {
    const formData = new FormData(actionForm[0]);

    $.ajax({
        url : '/board',
        method : 'PUT',
        processData: false,
        contentType: false,
        data : formData,
        beforeSend : function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken)
        },
        success : function(response) {
            if (response.success) {
                alert(response.message);
                pageForm.attr("action", `/board/read/${bnoData}`);
                pageForm.submit();
            }
        },
        error : function (xhr, status, error) {
            let response;

            try {
                response = JSON.parse(xhr.responseText);
            } catch (e) {
                alert("응답 데이터 처리 중 오류가 발생하였습니다");
                return false;
            }

            const errorMessage = response.message;

            if (xhr.status === 400) {
                const errors = response.items;

                if (errors['title']) {
                    alert(errors['title']);
                    return false;
                } else if (errors['content']) {
                    alert(errors['content']);
                    return false;
                } else if (errors['writer']) {
                    alert(errors['writer']);
                    return false;
                }

            } else if (xhr.status === 404) {
                alert(errorMessage);
            } else if (xhr.status === 500) {
                alert(errorMessage);
            } else {
                alert("게시글 수정 처리 중 오류가 발생하였습니다");
            }
        }
    });

    // if (validateInput(formData)) {
    //
    // }
}

//게시글 삭제
function removerBoard() {
    if (confirm("게시글을 삭제하시겠습니까?")) {
        const buttonObj = $(".fileList button");

        if (buttonObj != null && buttonObj.length > 0) {

            let str = '';

            for (const btn of buttonObj) {
                const fno = $(btn).attr('data-fno');
                const fullname = $(btn).attr('data-fullname');

                str += `<input type="hidden" name="fnos" value="${fno}">`;
                str += `<input type="hidden" name="fullnames" value="${fullname}">`
            }
            $(".deleteImages").append(str);
        }

        const formData = new FormData(actionForm[0]);

        $.ajax({
            url : `/board/${bnoData}`,
            method : 'DELETE',
            processData : false,
            contentType : false,
            data : formData,
            beforeSend : function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken)
            },
            success : function (response) {
                if (response.success) {
                    alert(response.message);
                    pageForm.attr("action", "/board/list");
                    pageForm.submit();
                }
            },
            error : function (xhr, status, error) {
                let response;

                try {
                    response = JSON.parse(xhr.responseText);
                } catch (e) {
                    alert("응답 데이터 처리 중 오류가 발생하였습니다");
                    return false;
                }

                const errorMessage = response.message;

                if (xhr.status === 404) {
                    alert(errorMessage)
                } else if (xhr.status === 500) {
                    alert(errorMessage)
                } else {
                    alert("게시글 삭제 처리 중 오류가 발생하였습니다");
                }
            }
        });
    }
}

$(function (){
    addButtonEvent();
});