<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/editBig.png" alt="Редактировать"></a>
<a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/deleteBig.png" alt="Удалить"></a>
<section>
    <h2>${resume.fullName}</h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHTML(contactEntry.getValue())%><br/>
        </c:forEach>
    <hr>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.Section>"/>
        <h3><%=sectionEntry.getKey().getTitle()%>
        </h3>
        <p>
            <c:choose>
                <c:when test="${sectionEntry.key=='PERSONAL' || sectionEntry.key=='OBJECTIVE'}">
                    <%=sectionEntry.getValue()%>
                </c:when>
                <c:when test="${sectionEntry.key=='ACHIEVEMENTS' || sectionEntry.key=='QUALIFICATIONS'}">
                    <c:forEach var="listEntry" items="<%=((ListSection) sectionEntry.getValue()).getList()%>">
                        <li>${listEntry}</li>
                    </c:forEach>
                </c:when>
            </c:choose>
        </p>
    </c:forEach>
</section>
<button type="button" onclick="window.history.back()">Назад</button>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

