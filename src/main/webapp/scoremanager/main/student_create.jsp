<%-- 学生登録JSP --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>
			<form action="StudentCreateExecute.action" method="get">
				<div class="col-4">
					<c:if test="${judg == 0}">
						<div>${error }</div>
					</c:if>
					<label class="form-label" for="student-f1-select">入学年度</label><br>
					<select class="form-select" id="student-f1-select" name="entyear">
						<option value="0">--------</option>
						<c:forEach var="year" items="${ent_year_set}">
							<option value="${year}">${year}</option>
						</c:forEach>
					</select>
					<c:if test="${judg == 1}">
						<div class="mt-2 text-warning">${error }</div>
					</c:if>
					
					<label class="form-label" for="student-f1-select">学生番号</label><br>
					<input type="text" size="90" name="no" placeholder="学生番号を入力してください" required>
					<c:if test="${judg == 2}">
						<div class="mt-2 text-warning">${error }</div>
					</c:if>
					
					<label class="form-label" for="student-f1-select">氏名</label><br>
					<input type="text" size="90" name="name" placeholder="氏名を入力してください" required>
					<label class="form-label" for="student-f1-select">クラス</label><br>
					<select class="form-select" id="student-f1-select" name="classnum">
						<c:forEach var="classnum" items="${class_num_set}">
							<option value="${classnum}">${classnum}</option>
						</c:forEach>
					</select>
				</div>
				
				<div class="mt-2 text-warning">${errors.get("f1")}</div>
				<p></p>
				<input type="submit" value="登録して終了" style="padding: 0.4em 0.8em; width: auto; height: auto; border: 0px; border-radius: 5px; color: white; background-color: #1e90ff">
			</form>
			<p></p>
			<a href="StudentList.action">戻る</a>
		</section>
	</c:param>
</c:import>