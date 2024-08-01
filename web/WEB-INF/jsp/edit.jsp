<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <jsp:useBean id="action" type="java.lang.String" scope="request"/>
    <form method="post" action="resume?action=${action}" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt><h3>Имя</h3></dt>
            <dd><label>
                <input type="text" required name="fullName" size=50 value="${resume.fullName}">
            </label></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><label>
                    <input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}">
                </label></dd>
            </dl>
        </c:forEach>
        <hr>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <dl>
                <dt><h3>${sectionType.title}</h3></dt>
                <c:choose>
                    <c:when test="${sectionType.name()==SectionType.PERSONAL || sectionType.name()==SectionType.OBJECTIVE}">
                        <label>
                            <input type="text" name="${sectionType.name()}" size=100
                                   value="${resume.getSection(sectionType)}">
                        </label>
                    </c:when>
                    <c:when test="${sectionType.name()==SectionType.ACHIEVEMENTS || sectionType.name()==SectionType.QUALIFICATIONS}">
                        <c:set var="section" value="${(resume.getSection(sectionType))}"/>
                        <c:set var="text" value=""/>
                        <c:if test="${section!=null}">
                            <jsp:useBean id="section" type="com.urise.webapp.model.ListSection"/>
                            <label>
                                <textarea name="${sectionType.name()}" cols="120"
                                          rows="10"><%= section == null ? "" : String.join("\n", section.getList())%></textarea>
                            </label>
                        </c:if>
                        <c:if test="${section==null}">
                            <label>
                                <textarea name="${sectionType.name()}" cols="120" rows="10"></textarea>
                            </label>
                        </c:if>
                    </c:when>
                </c:choose>
            </dl>
        </c:forEach>
        <button type="submit" onclick="oncliskSave(this)">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

