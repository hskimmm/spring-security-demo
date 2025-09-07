const csrfToken = $("meta[name='_csrf']").attr("content");
const csrfHeader = $("meta[name='_csrf_header']").attr("content");

const regForm = $("#actionForm");

function addButtonEvent() {
    $(".registerBtn").on("click", function (e) {
        e.preventDefault();
        regBoard();
    });

    $(".listBtn").on("click", function (e) {
        e.preventDefault();
        window.location.href = "/board/list";
    });
}

//유효성검사
function validateInput(formData) {
    const fileSuffix = ['jpg', 'jpeg', 'png', 'gif', 'bmp'];

    for (let [key, value] of formData.entries()) {
        if (value instanceof File) {

            if (value.size === 0 || value.name === '') {
                continue;
            }

            const suffix = value.name.split(".").pop().toLowerCase();

            if (!fileSuffix.includes(suffix)) {
                alert("허용되지 않는 파일입니다. \n (jpg, jpeg, png, gif, bmp 파일만 업로드 가능합니다.)");
                return false;
            }
            continue;
        }

        const trimmedValue = value.trim();

        if (value !== trimmedValue) {
            formData.set(key, trimmedValue);
        }

        if (key === 'title' && trimmedValue === '') {
            alert("제목을 입력하세요");
            $("#title").focus();
            return false;
        } else if (key === 'content' && trimmedValue === '') {
            alert("내용을 입력하세요");
            $("textarea[name='content']").focus();
            return false;
        } else if (key === 'writer' && trimmedValue === '') {
            alert("로그인 후 게시글을 등록하세요")
            return false;
        }
    }
    return true;
}

//게시글등록
function regBoard() {
    const formData = new FormData(regForm[0]);
    if (validateInput(formData)) {
        $.ajax({
            url : '/board',
            method : 'POST',
            processData : false,
            contentType : false,
            data : formData,
            beforeSend : function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken)
            },
            success : function (response) {
                if (response.success) {
                    alert(response.message);
                    window.location.href = '/board/list';
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
                } else if (xhr.status === 500){
                    alert(errorMessage);
                } else {
                    alert("게시글 등록 처리 중 오류가 발생하였습니다");
                }
            }
        });
    }
}

$(function() {
    addButtonEvent();
});