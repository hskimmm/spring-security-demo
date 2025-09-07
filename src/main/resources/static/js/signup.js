const csrfToken = $("meta[name='_csrf']").attr("content");
const csrfHeader = $("meta[name='_csrf_header']").attr("content");

const joinForm = $("#joinForm");

let isUsernameAvailability = false;
let isEmailAvailability = false;

function addButtonEvent() {
    $("#username").blur(function (){ //아이디 입력 시 이벤트
        validateUsername();
    });

    $("#password").blur(function (){ //패스워드 입력 시 이벤트
        validatePassword();
    });

    $("#confirmPassword").blur(function (){ //패스워드 확인 입력 시 이벤트
        validateConfirmPassword();
    });

    $("#email").blur(function () { //이메일 입력 시 이벤트
        validateEmail();
    });

    $("#name").blur(function () { //이름 입력 시 이벤트
        validateName();
    });

    $("#checkUsernameBtn").on("click", function (e) { //아이디 중복 검사 버튼 클릭 시 이벤트
        e.preventDefault();
        checkUsernameAvailability();
    });

    $("#username").on("input", function () { //아이디 입력 란 클릭 시(?) 이벤트
        isUsernameAvailability = false;
        $("#usernameFeedback").text('').hide();
    });

    $("#checkEmailBtn").on("click", function (e) { //이메일 중복 검사 클릭 시 이벤트
        e.preventDefault();
        checkEmailAvailability();
    });

    $("#email").on("input", function () { //이메일 입력 란 클릭 시(?) 이벤트
        isEmailAvailability = false;
        $("#emailFeedback").text('').hide();
    });

    $(".joinBtn").on("click", function (e) { //회원가입 버튼 클릭 시 이벤트
        e.preventDefault();
        memberJoin();
    });
}

//회원가입
function memberJoin() {
    if (validateForm()) {
        const formData = joinForm.serialize();

        $.ajax({
            url : '/member/join',
            method : 'POST',
            data : formData,
            beforeSend : function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken)
            },
            success : function (response) {
                if (response.success) {
                    alert(response.message);
                    window.location.href = "/member/login-form";
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

                    if (errors['username']) {
                        showErrorMessage($("#usernameFeedback"), errors['username']);
                    }
                    if (errors['password']) {
                        showErrorMessage($("#passwordFeedback"), errors['password']);
                    }
                    if (errors['confirmPassword']) {
                        showErrorMessage($("#confirmPasswordFeedback"), errors['confirmPassword']);
                    }
                    if (errors['email']) {
                        showErrorMessage($("#emailFeedback"), errors['email']);
                    }
                    if (errors['name']) {
                        showErrorMessage($("#nameFeedback"), errors['name']);
                    }
                } else if (xhr.status === 500) {
                    alert(errorMessage);
                } else {
                    alert("회원가입 처리 중 오류가 발생하였습니다");
                }
            }
        });
    }
}

//아이디 중복체크
function checkUsernameAvailability() {
    if (validateUsername()) {
        const usernameInput = $("input[name='username']").val().trim();
        const data = { username : usernameInput };

        $.ajax({
            url : '/member/checkUsernameAvailability',
            method : 'GET',
            data : data,
            success : function (response) {
                if (response.success) {
                    const result = response.data;
                    if (result === 1) {
                        showErrorMessage($("#usernameFeedback"), response.message);
                        isUsernameAvailability =  false;
                    } else if (result === 0) {
                        showErrorMessage($("#usernameFeedback"), response.message);
                        isUsernameAvailability =  true;
                    }
                }
            },
            error : function (xhr, status, error) {
                let response;

                try {
                    response = JSON.parse(xhr.responseText);
                } catch (e) {
                    alert("응답 데이터 처리 중 오류가 발생하였습니다");
                }

                const errorMessage = response.message;

                if (xhr.status === 500) {
                    alert(errorMessage);
                } else {
                    alert("아이디 중복 검사 중 오류가 발생하였습니다");
                }

                isUsernameAvailability = false;
            }
        });
    }
}

//이메일 중복체크
function checkEmailAvailability() {
    if (validateEmail()) {
        const emailInput = $("input[name='email']").val().trim();
        const data = { email : emailInput };

        $.ajax({
            url : '/member/checkEmailAvailability',
            method: 'GET',
            data : data,
            success : function (response) {
                if (response.success) {
                    const result = response.data;
                    if (result === 1) {
                        showErrorMessage($("#emailFeedback"), response.message);
                        isEmailAvailability = false;
                    } else if (result === 0) {
                        showErrorMessage($("#emailFeedback"), response.message);
                        isEmailAvailability = true;
                    }
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
                    alert("이메일 중복 체크 중 오류가 발생하였습니다.");
                }

                isEmailAvailability = false;
            }
        });
    }
}

//아이디 유효성검사
function validateUsername() {
    const username = $("input[name='username']").val().trim();
    const usernameRegex = /^[a-zA-Z0-9]{5,20}$/; //5자 이상 20자 이하 영문 대소문자와 숫자

    if (username === '') {
        showErrorMessage($("#usernameFeedback"), "아이디를 입력하세요");
        return false;
    } else if (!usernameRegex.test(username)) {
        showErrorMessage($("#usernameFeedback"), "아이디 형식은 5자 이상 20자 이하로 영문 대소문자와 숫자를 포함해야합니다.");
        return false;
    } else { //유효성 검사 통과 시
        $("#usernameFeedback").text('');
        return true;
    }
}

//비밀번호 유효성검사
function validatePassword() {
    const password = $("input[name='password']").val().trim();
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/; // 비밀번호는 8자 이상, 20자 이하로 영문자, 숫자, 특수문자(@$!%*?&)

    if (password === '') {
        showErrorMessage($("#passwordFeedback"), "비밀번호를 입력하세요");
        return false;
    } else if (!passwordRegex.test(password)) {
        showErrorMessage($("#passwordFeedback"), "비밀번호는 8자 이상, 20자 이하로 영문자, 숫자, 특수문자(@$!%*?&)를 포함해야 합니다.");
        return false;
    } else {
        $("#passwordFeedback").text('');
        return true;
    }
}

//비밀번호 확인 유효성검사
function validateConfirmPassword() {
    const password = $("input[name='password']").val().trim();
    const confirmPassword = $("input[name='confirmPassword']").val().trim();

    if (confirmPassword === '') {
        showErrorMessage($("#confirmPasswordFeedback"), "비밀번호 확인란을 입력하세요");
        return false;
    } else if (confirmPassword !== password) {
        showErrorMessage($("#confirmPasswordFeedback"), "비밀번호가 일치하지 않습니다");
        return false;
    } else {
        $("#confirmPasswordFeedback").text('');
        return true;
    }

}

//이메일 유효성검사
function validateEmail() {
    const email = $("input[name='email']").val().trim();
    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/; //이메일 형식 검사

    if (email === '') {
        showErrorMessage($("#emailFeedback"), "이메일을 입력하세요");
        return false;
    } else if (!emailRegex.test(email)) {
        showErrorMessage($("#emailFeedback"), "유효한 이메일 형식이 아닙니다.");
        return false;
    } else {
        $("#emailFeedback").text('');
        return true;
    }

}

//이름 유효성검사
function validateName() {
    const name = $("input[name='name']").val().trim();

    if (name === '') {
        showErrorMessage($("#nameFeedback"), "이름을 입력하세요");
        return false;
    } else {
        $("#nameFeedback").text('');
        return true;
    }
}

//최종 검사
function validateForm() {
    if (!validateUsername()) {
        return false;
    }

    if (!validatePassword()) {
        return false;
    }

    if (!validateConfirmPassword()) {
        return false;
    }

    if (!validateEmail()) {
        return false;
    }

    if (!validateName()) {
        return false;
    }

    if (!isUsernameAvailability) {
        showErrorMessage($("#usernameFeedback"), "아이디 중복 검사를 하세요");
        return false;
    }

    if (!isEmailAvailability) {
        showErrorMessage($("#emailFeedback"), "이메일 중복 검사를 하세요");
        return false;
    }

    return true;
}

//에러 메시지 공통 함수
function showErrorMessage(elementId, message) {
    $(elementId).text(message).show();
}

$(function () {
    addButtonEvent();
});