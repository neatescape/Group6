<%-- 成績一覧（学生）JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>
	
	<c:param name="scripts"></c:param>
	
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
			<div class="border mx-3 mb-3 py-2 rounded" id="filter">
				<form action="TestListSubjectExecute.action" method="get">
					<div class="row px-2 py-2 align-items-center">
						<div class="col-12 col-md-2 text-center">
							科目情報
						</div>
						<div class="col-6 col-md-2">
							<label class="form-label" for="student-f1-select">入学年度</label>
							<select class="form-select" id="student-f1-select" name="f1" style="width: 100%;">
								<option value="0">--------</option>
								<c:forEach var="year" items="${ent_year_set}">
									<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
									<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="col-6 col-md-2">
							<label class="form-label" for="student-f2-select">クラス</label>
							<select class="form-select" id="student-f2-select" name="f2" style="width: 100%;">
								<option value="0">--------</option>
								<c:forEach var="num" items="${class_num_set}">
									<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
									<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="col-8 col-md-4">
							<label class="form-label" for="student-f3-select">科目</label>
							<select class="form-select" id="student-f3-select" name="f3" style="width: 100%;">
								<option value="0">--------</option>
								<c:forEach var="subject" items="${subject_set}">
									<option value="${subject.cd}" <c:if test="${subject.cd==f3.cd}">selected</c:if>>${subject.name}</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="col-4 col-md-2 text-center">
							<button class="btn btn-secondary" id="filter-button">検索</button>
						</div>
						
						<div class="mt-2 text-warning">${errors.get("f1")}</div>
						<input type="hidden" name="f" value="sj">
					</div>
				</form>
				<hr style="width: 95%; margin: auto;">
				<form action="TestListStudentExecute.action" method="get">
					<div class="row px-2 py-2 align-items-center">
						<div class="col-12 col-md-2 text-center">
							学生情報
						</div>
						<div class="col-6 col-md-4">
							<label class="form-label" for="student-f1-select">学生番号</label>
							<input type="text" style="width: 100%;" name="f4" value="${fn:trim(f4.no)}" placeholder="学生番号を入力してください">
						</div>
						
						<div class="col-4 text-center">
							<button class="btn btn-secondary" id="filter-button">検索</button>
						</div>
						
						<div class="mt-2 text-warning">${errors.get("f1")}</div>
						<input type="hidden" name="f" value="st">
					</div>
				</form>
			</div>
			<label style="color: #00e6e6;">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</label>
			
			<c:choose>
				<c:when test="${test_list_student.size() > 0}">
					<div>氏名：${f4.name} (${fn:trim(f4.no)})</div>
					<table class="table table-hover">
						<tr>
							<th>科目名</th>
							<th>科目コード</th>
							<th>回数</th>
							<th>点数</th>
						</tr>
						
						<c:forEach var="tes_stu" items="${test_list_student}">
							<c:if test="${!empty tes_stu.point}">
								<tr>
									<td>${tes_stu.subjectName}</td>
									<td>${tes_stu.subjectCd}</td>
									<td>${tes_stu.num}</td>
									<td>${tes_stu.point}</td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<div>成績情報が見つかりませんでした</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>