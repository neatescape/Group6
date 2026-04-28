<%-- 杉本 --%>
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
          <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>
          <form action="StudentCreateExecute.action" method="get">
	          
	          <c:if test="${judg == 0}">
		          <div>${error }</div>
	          </c:if>
	          
	          <%-- py-2で行間に空白を入れる --%>
	          <div class="py-2 px-2">
			      <label class="form-label" for="student-f1-select">入学年度</label>
			      <select class="form-select" id="student-f1-select" name="entyear">
				      <option value="0">--------</option>
				      <c:forEach var="year" items="${ent_year_set}">
					      <option value="${year}">${year}</option>
				      </c:forEach>
			      </select>
			      <c:if test="${judg == 1}">
					  <div style="color: #FF8C00;">${error }</div>
				  </c:if>
			  </div>
	                      
	          <div class="py-2 px-2">
			      <label class="form-label" for="student-f1-select">学生番号</label>
			      <input class="form-input" type="text" style="width: 100%;" name="no" value="${no }" placeholder="学生番号を入力してください" maxlength="10" required>
			      <c:if test="${judg == 2}">
					  <div style="color: #FF8C00;">${error }</div>
				  </c:if>
			  </div>
			  
			  <div class="py-2 px-2">
			      <label class="form-label" for="student-f1-select">氏名</label>
			      <input class="form-input" type="text" style="width: 100%;" name="name" value="${name }" placeholder="氏名を入力してください" maxlength="10" required>
	          </div>
	                      
	          <div class="py-2 px-2">
			      <label class="form-label" for="student-f1-select">クラス</label>
			      <select class="form-select" id="student-f1-select" name="classnum">
			      <%-- 入学年度未入力エラーが起きた時にクラスを入力されていた状態にする --%>
				      <c:choose>
					      <c:when test="${judg == 1 }">
						      <c:forEach var="classnum" items="${class_num_set}">
							      <c:choose>
								      <c:when test="${classnum == classNum }">
									      <option value="${classnum}" selected>${classnum}</option>
								      </c:when>
								      <c:otherwise>
									      <option value="${classnum}">${classnum }</option>
									  </c:otherwise>
								  </c:choose>
						      </c:forEach>
						  </c:when>
						  <c:otherwise>
							  <c:forEach var="classnum" items="${class_num_set}">
								  <option value="${classnum}">${classnum }</option>
							  </c:forEach>
						  </c:otherwise>
					  </c:choose>
			      </select>
	          </div>
	 
	          <div class="mt-2 text-warning">${errors.get("f1")}</div>
	          
	          <div class="py-2 px-2">
	          	<input class="btn btn-secondary" type="submit" value="登録して終了" style="padding: 0.4em 0.8em; width: auto; height: auto; border: 0px; border-radius: 5px; color: white; background-color: #808080">
	          </div>
	          
          </form>
          <p><a href="StudentList.action" class="py-2 px-2">戻る</a></p>
      </section>
  </c:param>
</c:import>
