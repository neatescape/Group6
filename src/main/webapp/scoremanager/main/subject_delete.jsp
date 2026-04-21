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
			<div class="col-4">
          		<label>「${subject_name }(${subject_cd })」を削除してもよろしいですか</label>
          		<button onclick="location.href='SubjectDeleteExecute.action?cd=${subject_cd}'" style="color:#FFFFFF; background-color:#FF0000; border-radius: 5px; border:none;">削除</button>
          		<br>
          		<a href="SubjectList.action">戻る</a>
          	</div>
      </section>
  </c:param>
</c:import>