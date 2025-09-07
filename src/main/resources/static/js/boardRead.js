const csrfToken = $("meta[name='_csrf']").attr("content");
const csrfHeader = $("meta[name='_csrf_header']").attr("content");

const commentRegModal = new bootstrap.Modal(document.getElementById('replyAddModal'));

const pageUL = $(".pagination");
const commentUL = $(".commentList");

const bnoData = $("input[name='bno']").val();
const mnoData = $("input[name='mno']").val();

let currentPageNum = 1;
let currentCno;

function addButtonEvent() {
    pageUL.on("click", "a", function (e) {
        e.preventDefault();
        const pageNumParam = $(this).attr('href');
        currentPageNum = pageNumParam;

        loadCommentList(pageNumParam)
    });

    commentUL.on("click", "li", function (e) {
        e.preventDefault();
        const cno = $(this).data('cno');
        currentCno = cno;
        commentRead(cno);
    });

    $(".commentRegBtn").on("click", function (e) {
        e.preventDefault();
        commentRegModal.show();
    });

    $("#replyAddModal").on("hidden.bs.modal", function (e) {
        e.preventDefault();

        $("input[name='content']").val('');
    });

    $("#replyRegisterBtn").on("click", function (e) {
        e.preventDefault();
        commentReg();
    });

    $("#replyModifyBtn").on("click", function (e) {
        e.preventDefault();
        commentModify();
    });

    $("#replyDeleteBtn").on("click", function (e) {
        e.preventDefault();
        commentDel();
    });
}

//유효성검사
function validateInput(content, writer) {
    if (content === '') {
        alert("댓글 내용을 입력하세요");
        $("#content").focus();
        return false;
    } else if (writer === '') {
        alert("로그인 후 이용하세요");
        window.location.href = '/member/login-form';
    }
    return true;
}

//댓글 목록 조회
function loadCommentList(pageNumParam, amountParam) {

    let pageNum = pageNumParam || 1;
    let amount = amountParam || 10;

    $.ajax({
        url : `/comment?pageNum=${pageNum}&amount=${amount}`,
        method : 'GET',
        data : {bno : bnoData},
        success : function (response) {
            if (response.success) {
                const responseData = response.items;
                commentListHTML(responseData);
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

            if (xhr.status === 500) {
                alert(errorMessage);
            } else {
                alert("댓글 목록을 로드 중 오류가 발생하였습니다");
            }
        }
    });
}

//댓글 목록 동적 생성
function commentListHTML(responseData) {
    //댓글 목록
    const commentList = responseData.commentList;
    let str = '';

    for (const comment of commentList) {
        str += `<li class="list-group-item d-flex justify-content-between align-items-center" data-cno="${comment.cno}">${comment.content}
                            <span class="badge badge-primary badge-pill">${comment.writer}</span>
                            </li>`;
    }
    commentUL.html(str);

    //댓글 페이징
    const page = responseData.page;
    const startPage = page.startPage;
    const endPage = page.endPage;
    const prev = page.prev;
    const next = page.next;
    const pageNum = page.cri.pageNum;

    let pageStr = '';

    if (prev) {
        pageStr += `<li class="page-item">
                                <a class="page-link" href="${startPage - 1}" tabindex="-1">Previous</a>
                                </li>`;
    }

    for (let i = startPage; i <= endPage; i++) {
        pageStr += `<li class="page-item ${i === pageNum ? 'active' : ''}">
                                <a class="page-link" href="${i}">${i}</a>
                                </li>`;
    }

    if (next) {
        pageStr += `<li class="page-item">
                                <a class="page-link" href="${endPage + 1}">Next</a>
                                </li>`;
    }
    pageUL.html(pageStr);
}

//댓글 상세조회
function commentRead(cno) {
    $.ajax({
        url : `/comment/${cno}`,
        method : 'GET',
        success : function (response) {
            if (response.success) {
                const content = response.data.content;
                $("input[name='content']").val(content);
                commentRegModal.show();
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
                alert(errorMessage);
            } else if (xhr.status === 500) {
                alert(errorMessage);
            }
        }
    });
}

//댓글 등록
function commentReg() {
    const contentInput = $("input[name='content']").val().trim();
    const writerInput = $("input[name='commentWriter']").val().trim();

    if (validateInput(contentInput, writerInput)) {
        const data = {
            bno : bnoData,
            mno : mnoData,
            content : contentInput,
            writer : writerInput
        }

        $.ajax({
            url : '/comment',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify(data),
            beforeSend : function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken)
            },
            success : function (response) {
                if (response.success) {
                    alert(response.message);
                    commentRegModal.hide();

                    loadCommentList();
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
                    let errors = response.items;

                    if (errors['content']) {
                        alert(errors['content']);
                        return false;
                    } else if (errors['writer']) {
                        alert(errors['writer']);
                        window.location.href = '/member/login-form';
                    }
                } else if (xhr.status === 500) {
                    alert(errorMessage);
                } else {
                    alert("댓글 등록 중 오류가 발생하였습니다");
                }
            }
        });
    }
}

//댓글 수정
function commentModify() {
    const contentInput = $("input[name='content']").val();
    const writerInput = $("input[name='commentWriter']").val();

    if (validateInput(contentInput, writerInput)) {
        const data = {
            cno : currentCno,
            content : contentInput,
            writer : writerInput
        }

        $.ajax({
            url : '/comment',
            method : 'PUT',
            contentType : 'application/json',
            data : JSON.stringify(data),
            beforeSend : function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken)
            },
            success : function (response) {
                if (response.success) {
                    alert(response.message);
                    commentRegModal.hide();

                    loadCommentList(currentPageNum);
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

                    if (errors['content']) {
                        alert(errors['content']);
                        return false;
                    } else if (errors['writer']) {
                        alert(errors['writer']);
                        window.location.href = '/member/login-form';
                    }
                } else if (xhr.status === 404) {
                    alert(errorMessage);
                } else if (xhr.status === 500) {
                    alert(errorMessage);
                } else {
                    alert("댓글 수정 중 오류가 발생하였습니다");
                }
            }
        });
    }
}

//댓글 삭제
function commentDel() {
    if (confirm("댓글을 삭제하시겠습니까?")) {
        const data = { cno : currentCno };

        $.ajax({
            url : '/comment',
            method : 'DELETE',
            contentType : 'application/json',
            data : JSON.stringify(data),
            beforeSend : function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken)
            },
            success : function (response) {
                if (response.success) {
                    alert(response.message);
                    commentRegModal.hide();

                    loadCommentList(currentPageNum);
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

                    if (errors['cno']) {
                        alert(errors['cno']);
                        return false;
                    }
                } else if (xhr.status === 404) {
                    alert(errorMessage);
                } else if (xhr.status === 500) {
                    alert(errorMessage);
                } else {
                    alert("댓글 삭제 중 오류가 발생하였습니다");
                }
            }
        });
    }
    return false;
}

$(function (){
    addButtonEvent();
    loadCommentList();
});