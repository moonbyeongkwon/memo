<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="w-50">
	<h1>글 상세</h1>
	
	<input type="text" id="subject" class="form-control" placeholder="제목을 입력하세요" value="${post.subject}">
	<textarea id="content" class="form-control" placeholder="내용을 입력하세요" rows="10">${post.content}</textarea>
	
	<%-- 이미지가 있을 때만 영역 노출 --%>
	<c:if test="${not empty post.imagePath}">
	<div class="my-3">
		<img src="${post.imagePath}" alt="업로드 된 이미지" width="300">
	</div>
	</c:if>
	
	<div class="d-flex justify-content-end my-3">
		<input type="file" id="file" accept=".jpg, .png, .gif">
	</div>
	
	<div class="d-flex justify-content-between">
		<button type="button" id="delPostBtn" class="btn btn-secondary" data-post-id="${post.id}">삭제</button>
		
		<div>
			<a href="/post/post-list-view" class="btn btn-dark">목록</a>
			<button type="button" id="saveBtn" class="btn btn-warning" data-post-id="${post.id}">수정</button>
		</div>
	</div> 
</div>

<script>
	$(document).ready(function() {
		// 수정 
		$("#saveBtn").on('click', function() {
			//alert("수정");
			let postId = $(this).data("post-id");
			let subject = $("#subject").val().trim();
			let content = $("#content").val();
			let fileName = $("#file").val(); // C:\fakepath\ai-generated-8327632_640.jpg
			//alert(fileName);
			
			if (!subject) {
				alert("제목을 입력하세요.");
				return;
			}
			
			if (!content) {
				alert("내용을 입력하세요.");
				return;
			}
			
			// 파일이 업로드 된 경우에만 확장자 체크
			if (fileName) {
				//alert("파일이 있음");
				// C:\fakepath\ai-generated-8327632_640.jpg
				let extension = fileName.split(".").pop().toLowerCase();
				//alert(extension);
				
				if ($.inArray(extension, ['jpg', 'png', 'gif']) == -1) {
					alert("이미지 파일만 업로드 할 수 있습니다.");
					$("#file").val(""); // 파일을 비운다.
					return;
				}
			}
			
			// 이미지를 업로드 할 때는 반드시 form으로 구성한다.
			let formData = new FormData();
			formData.append("postId", postId);
			formData.append("subject", subject);
			formData.append("content", content);
			formData.append("file", $("#file")[0].files[0]);
			
			$.ajax({
				// request
				type:"PUT"
				, url:"/post/update"
				, data:formData
				, enctype:"multipart/form-data" // 파일 업로드 필수 설정
				, processData:false // 파일 업로드 필수 설정
				, contentType:false // 파일 업로드 필수 설정
				
				// response
				, success:function(data) {
					if (data.code == 200) {
						alert("메모가 수정되었습니다.");
						location.reload(true);
					} else {
						alert(data.error_message);
					}
				}
				, error:function(e) {
					alert("글을 수정하는데 실패했습니다.");
				}
			});
		});
		
		$("#delPostBtn").on('click', function() {
			let postId = $(this).data('post-id');
			//alert(postId);
			
			$.ajax({
				type:"DELETE"
				, url:"/post/delete"
				, data:{"postId":postId}
				, success:function(data) {
					if (data.result == "성공") {
						alert("삭제 되었습니다.");
						location.href = "/post/post-list-view";
					} else {
						alert(data.error_message);
					}
				} 
				, error:function(e) {
					alert("글을 삭제하는데 실패했습니다.");
				}
			});
		});
	});
</script>




