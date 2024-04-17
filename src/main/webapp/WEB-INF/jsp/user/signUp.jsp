<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="w-50">
	<h1>회원가입</h1>

	<form id="signUpForm" method="post" action="/user/sign-up">
		<table class="sign-up-table table table-bordered">
			<tr>
				<th>* 아이디(4자 이상)<br></th>
				<td>
					<%-- 인풋박스 옆에 중복확인을 붙이기 위해 div를 하나 더 만들고 d-flex --%>
					<div class="d-flex">
						<input type="text" id="loginId" name="loginId"
							class="form-control col-10" placeholder="아이디를 입력하세요.">
						<button type="button" id="loginIdCheckBtn" class="btn btn-success">중복확인</button>
						<br>
					</div> <%-- 아이디 체크 결과 --%> <%-- d-none 클래스: display none (보이지 않게) --%>
					<div id="idCheckLength" class="small text-danger d-none">ID를
						4자 이상 입력해주세요.</div>
					<div id="idCheckDuplicated" class="small text-danger d-none">이미
						사용중인 ID입니다.</div>
					<div id="idCheckOk" class="small text-success d-none">사용 가능한
						ID 입니다.</div>
				</td>
			</tr>
			<tr>
				<th>* 비밀번호</th>
				<td><input type="password" id="password" name="password"
					class="form-control" placeholder="비밀번호를 입력하세요."></td>
			</tr>
			<tr>
				<th>* 비밀번호 확인</th>
				<td><input type="password" id="confirmPassword"
					class="form-control" placeholder="비밀번호를 입력하세요."></td>
			</tr>
			<tr>
				<th>* 이름</th>
				<td><input type="text" id="name" name="name"
					class="form-control" placeholder="이름을 입력하세요."></td>
			</tr>
			<tr>
				<th>* 이메일</th>
				<td><input type="text" id="email" name="email"
					class="form-control" placeholder="이메일 주소를 입력하세요."></td>
			</tr>
		</table>
		<br>

		<button type="submit" id="signUpBtn"
			class="btn btn-primary float-right">회원가입</button>
	</form>
</div>

<script>
	$(document).ready(function() {
		
		// 아이디 중복확인
		$("#loginIdCheckBtn").on('click', function() {
			//alert("중복");
			
			// 경고 문구 초기화
			$("#idCheckLength").addClass("d-none");
			$("#idCheckDuplicated").addClass("d-none");
			$("#idCheckOk").addClass("d-none");
			
			let loginId = $("input[name=loginId]").val().trim();
			if (loginId.length < 4) {
				$("#idCheckLength").removeClass("d-none");
				return;
			}
			
			// AJAX - 중복확인
			$.get("/user/is-duplicated-id", {"loginId":loginId})	// request
			.done(function(data) {	// response
				// {"code":200, "is_duplicated_id":true} true:중복
				if (data.code == 200) {
					if (data.is_duplicated_id) {	// 중복
						$("#idCheckDuplicated").removeClass("d-none");
					} else {// 사용 가능
						$("#idCheckOk").removeClass("d-none");
					}
				} else {	
					alert(data.error_message);
				}
			});
		});
	});
</script>