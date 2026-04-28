<%-- 成績管理JSP --%>
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
          <form action="TestRegist.action" method="get">
              <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                  <div class="col-4 col-md-2">
                      <label class="form-label" for="student-f1-select">入学年度</label>
                      <select class="form-select" id="student-f1-select" name="f1" style="width: 100%;">
                          <option value="0">--------</option>
                          <c:forEach var="year" items="${ent_year_set}">
                              <%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
                              <option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
                          </c:forEach>
                      </select>
                  </div>
 
                  <div class="col-4 col-md-2">
                      <label class="form-label" for="student-f2-select">クラス</label>
                      <select class="form-select" id="student-f2-select" name="f2" style="width: 100%;">
                          <option value="0">--------</option>
                          <c:forEach var="num" items="${class_num_set}">
                              <%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
                              <option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
                          </c:forEach>
                      </select>
                  </div>
                  
                  <div class="col-5 col-md-4">
                      <label class="form-label" for="student-f3-select">科目</label>
                      <select class="form-select" id="student-f3-select" name="f3" style="width: 100%;">
                          <option value="0">--------</option>
                          <c:forEach var="subject" items="${subject_set}">
                              <option value="${subject.cd}" <c:if test="${subject.cd==f3.cd}">selected</c:if>>${subject.name }</option>
                          </c:forEach>
                      </select>
                  </div>
                  
                  <div class="col-4 col-md-2">
                      <label class="form-label" for="student-f4-select">回数</label>
                      <select class="form-select" id="student-f4-select" name="f4" style="width: 100%;">
                          <option value="0">--------</option>
                          <c:forEach var="no" items="${no_set }">
                              <option value="${no}" <c:if test="${no==f4}">selected</c:if>>${no}</option>
                          </c:forEach>
                      </select>
                  </div>
 
                  <div class="col-2 col-md-2 text-center">
                      <button class="btn btn-secondary" id="filter-button">検索</button>
                  </div>
              </div>
	          <div class="mt-2 text-warning">${errors.get("f1")}</div>
          </form>
 
          <c:choose>
              <c:when test="${tests.size() > 0}">
                  <div>科目：${f3.name} (${f4 })</div>
                  <form action="TestRegistExecute.action">
	                  <table class="table table-hover">
	                      <tr>
	                          <th>入学年度</th>
	                          <th>クラス</th>
	                          <th>学生番号</th>
	                          <th>氏名</th>
	                          <th>点数</th>
	                          <th></th>
	                          <th></th>
	                      </tr>
	                      
	                      <c:forEach var="test" items="${tests}">
	                          <tr>
	                              <td>${test.student.entYear}</td>
	                              <td>${test.student.classNum}</td>
	                              <td>${test.student.no}</td>
	                              <td>${test.student.name}</td>
	                              <td><input type="text" style="width: 100%;" name="point_${test.student.no}" value="${test.point}"><c:if test="${test.student.no == stuNo }"><div class="mt-2 text-warning">${error }</div></c:if></td>
	                              <input type="hidden" name="regist" value="${test.student.no}">
	                              <input type="hidden" name="count" value="${f4 }">
	                              <input type="hidden" name="subject" value="${f3.cd }">
	                          </tr>
	                      </c:forEach>
	                  </table>
	                <input type="submit" value="登録して終了" style="padding: 0.4em 0.8em; width: auto; height: auto; border: 0px; border-radius: 5px; color: white; background-color: #808080">
                  </form>
              </c:when>
 
          </c:choose>
 
      </section>
  </c:param>
</c:import>
