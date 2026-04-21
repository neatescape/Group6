<%-- ログインJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>
	
	<c:param name="scripts"></c:param>
	
	<c:param name="content">
		<div class="row border mx-3 mb-3 py-2 align-items-center rounded">
		
			<section class ="me=4">
				<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">ログイン</h2>
			</section>
			
			<form action="LoginExecute.action" method="post">
				<div style="text-align: center;">
				
					<c:if test="${error != null }">
						<ul><li class="error">${error }</li></ul>
					</c:if>
					
					<p><input type="text" inputmode="url" name="id" value="${id }" placeholder="半角でご入力ください" 
						maxlength="10" style="border-radius: 10px; width: 90%; padding: 15px;" required></p>
					<p><input type="password" id="password" name="password" placeholder="30文字以内の半角英数字でご入力ください" 
						maxlength="30" style="border-radius: 10px; width: 90%; padding: 15px;" required></p>
						
					<p><input type="checkbox" id="chk_d_ps" name="chk_d_ps">
					<label for="chk_d_ps">パスワードを表示</label></p>
					
					<script>
						const passwordField = document.getElementById("password");
						const toggleCheckbox = document.getElementById("chk_d_ps");
						
						toggleCheckbox.addEventListener("change", function () {
							if (toggleCheckbox.checked) {
								passwordField.type = "text";
				    		} else {
							passwordField.type = "password";
							}
						});
					</script>
					<p><button type="submit" style="background-color: #0000FF; color: white; padding: 10px 40px; border: none; border-radius: 5px;">ログイン</button></p> 
				</div>
			</form>
		</div>
	</c:param>
</c:import>