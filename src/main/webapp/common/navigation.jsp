<%-- サイドバー --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<div style="width:100px; height:300px; text-align:center;">

	<c:if test="${user.isAuthenticated()}">
		<nav>
			<ul>
				<li><a href="Menu.action">メニュー</a></li>
				<li><a href="StudentList.action">学生管理</a></li>
				<li>成績管理</li>
				<li><a href="">成績登録</a></li>
				<li><a href="">成績参照</a></li>
				<li><a href="SubjectList.action">科目管理</a></li>
			</ul>
		</nav>
	</c:if>
	
</div>