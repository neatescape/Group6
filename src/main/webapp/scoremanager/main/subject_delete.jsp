<%-- 科目削除画面 --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>
			<form action="SubjectDeleteExecute.action" method="get">
				<input type="hidden" name="cd" value="${subject_cd }">
				<input type="hidden" name="name" value="${subject_name }">
				<div class="px-2">
					<label class="form-label">「${subject_name }(${subject_cd })」を削除してもよろしいですか</label>
				</div>
				<div class="py-2 px-2">
					<input class="form-input" type="submit" value="削除" style="width: 50px; height: 35px; color:#FFFFFF; background-color:#FF0000; border-radius: 5px; border:none;">
				</div>
			</form>
			<div class="py-2 px-2">
				<a href="SubjectList.action">戻る</a>
			</div>
		</section>
	</c:param>
</c:import>