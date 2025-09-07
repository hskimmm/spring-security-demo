const csrfToken = $("meta[name='_csrf']").attr("content");
const csrfHeader = $("meta[name='_csrf_header']").attr("content");

const authUL = $(".authList");

function addButtonEvent() {
    authUL.on("click", ".authChangeBtn", function (e) {
        e.preventDefault();

        const mnoData = $(this).data('mno');
        const selectObj = $(this).closest('td').find('select'); //closest -> 해당 요소의 부모 요소를 찾음
        const originalRoleValue = selectObj.data('original-value');
        const newRoleValue = selectObj.val();

        if (originalRoleValue === newRoleValue) {
            alert("사용자 권한을 변경 후 버튼을 누르세요.");
            return false;
        }

        authUpdate(mnoData, newRoleValue);
    });

    authUL.on("click", ".enableChangeBtn", function (e) {
        e.preventDefault();

        const mnoData = $(this).data('mno');
        const selectObj = $(this).closest('td').find('select');
        const originalValue = selectObj.data('original-enabled');
        const newValue = selectObj.val();
        const newValueBoolean = (newValue === "1");

        if (originalValue === newValueBoolean) {
            alert("권한을 변경 후 변경 버튼을 눌러주세요");
            return false;
        }

        enableUpdate(mnoData, newValue);
    });
}

//권한 목록 조회
function loadAuthList() {
    $.ajax({
        url : '/auth',
        method : 'GET',
        success : function (response) {
            if (response.success) {
                const authList = response.data;
                authListHTML(authList);
            }
        },
        error : function (xhr, status, error) {
            let response;

            console.log(xhr.responseText);
            try {
                response = JSON.parse(xhr.responseText);
            } catch(e) {
                alert("응답 데이터 처리 중 오류가 발생하였습니다");
                return false;
            }

            const errorMessage = response.message;

            if (xhr.status === 400) {
                alert(errorMessage);
                history.back();
            } else if (xhr.status === 500) {
                alert(errorMessage);
            }
        }
    });
}

//권한 목록 동적 생성
function authListHTML(authList) {
    let str = '';
    for (const auth of authList) {
        console.log(auth);
        str +=
            `<tr>
                        <td>${auth.username}</td>
                        <td>${auth.role === 'ROLE_ADMIN' ? '관리자' : '사용자'}</td>
                        <td>
                            <select id="role-select-1" name="authType" data-original-value="${auth.role}">
                                <option value="ROLE_USER" ${auth.role === 'ROLE_USER' ? 'selected' : ''}>일반 사용자</option>
                                <option value="ROLE_ADMIN" ${auth.role === 'ROLE_ADMIN' ? 'selected' : ''}>관리자</option>
                            </select>
                            <button class="change-role-btn authChangeBtn" data-mno="${auth.mno}">변경</button>
                        </td>
                        <td>
                            <select id="status-select-1" name="enalbeType" data-original-enabled="${auth.enabled}">
                                <option value="1" ${auth.enabled ? 'selected' : ''}>활성화</option>
                                <option value="0" ${!auth.enabled ? 'selected' : ''}>정지</option>
                            </select>
                            <button class="disable-btn enableChangeBtn" data-mno="${auth.mno}">변경</button>
                        </td>
                     </tr>`;
    }
    authUL.html(str);
}

//권한 업데이트
function authUpdate(mnoData, newRoleValue) {
    console.log(mnoData);
    console.log(newRoleValue);

    const data = {
        mno : mnoData,
        role : newRoleValue
    }

    $.ajax({
        url : '/auth',
        method : 'PUT',
        contentType : 'application/json',
        data : JSON.stringify(data),
        beforeSend : function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken)
        },
        success : function (response) {
            if (response.success) {
                alert(response.message);
                loadAuthList();
            }
        },
        error : function (xhr, status, error) {
            let response;

            //400, 404, 500
            try {
                response = JSON.parse(xhr.responseText);
            } catch (e) {
                alert("응답 데이터 처리 중 오류가 발생하였습니다");
                return false;
            }

            const errorMessage = response.message;

            if (xhr.status === 400) {
                const errors = response.items;

                if (errors['mno']) {
                    alert(errors['mno']);
                    return false;
                } else if (errors['role']) {
                    alert(errors['role']);
                    return false;
                }
            } else if (xhr.status === 404) {
                alert(errorMessage);
            } else if (xhr.status === 500) {
                alert(errorMessage);
            }
        }
    });
}

//권한 정지
function enableUpdate(mnoData, newValue) {
    console.log(mnoData);
    console.log(newValue);

    const data = {
        mno : mnoData,
        enabled : newValue
    }

    $.ajax({
        url : '/auth/enable',
        method : 'PUT',
        contentType: 'application/json',
        data : JSON.stringify(data),
        beforeSend : function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken)
        },
        success : function (response) {
            if (response.success) {
                alert(response.message);
                loadAuthList();
            }
        },
        error : function (xhr, status, error) {
            let response;

            try {

            } catch (e) {
                alert("응답 데이터 처리 중 오류가 발생하였습니다");
                return false;
            }

            const errorMessage = response.message;

            if (xhr.status === 400) {
                alert(errorMessage);
            } else if (xhr.status === 404) {
                let errors = response.items;

                if (errors['mno']) {
                    alert(errors['mno']);
                    return false;
                } else if (errors['enabled']) {
                    alert(errors['enabled']);
                    return false;
                }
            } else if (xhr.status === 500) {
                alert(errorMessage);
            }
        }
    });
}

$(function () {
    addButtonEvent();
    loadAuthList();
});