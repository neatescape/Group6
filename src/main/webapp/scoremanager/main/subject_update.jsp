<%-- 学生一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>
	
	<c:param name="scripts"></c:param>
	
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
			<form action="SubjectUpdateExecute.action" method="get">
				<div class="col-4">
					<label class="form-label" for="student-f1-select">科目コード</label><br>
					<input type="text" size="1" name="cd" value="${cd}" readonly>
					<div class="mt-2 text-warning">${error}</div>
					<label class="form-label" for="student-f1-select">科目名</label><br>
					<input type="text" size="90" name="name" placeholder="${name}" required>
				</div>
				<p></p>
				<input type="submit" value="変更" style="width: 50px; height: 35px; border: 0px; border-radius: 5px; color: white; background-color: #1e90ff">
			</form>
			<p></p>
			<a href="SubjectList.action">戻る</a>
		</section>
	</c:param>
</c:import>