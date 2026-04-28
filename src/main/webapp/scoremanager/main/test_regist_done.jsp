<%-- 成績登録/変更完了JSP --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
			<p class="py-2" style="text-align: center; background-color: #66cdaa;"><label>登録が完了しました</label></p>
			<a href="TestRegist.action">戻る</a>
			<a href="TestList.action">成績参照</a>
		</section>
	</c:param>
</c:import>
