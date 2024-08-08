<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.CompanySection" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
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
        <br>
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.Section>"/>
        <h2><%=sectionEntry.getKey().getTitle()%>
        </h2>
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
            <c:when test="${sectionEntry.key=='EDUCATION' || sectionEntry.key=='EXPERIENCE'}">
                <c:forEach var="listEntry" items="<%=((CompanySection) sectionEntry.getValue()).getList()%>">
                    <c:if test="${listEntry.website!=null}">
                        <h3><a href="${listEntry.website}">${listEntry.name}</a></h3>
                    </c:if>
                    <c:if test="${listEntry.website==null}">
                        <h4>${listEntry.name}</h4>
                    </c:if>
                    <c:forEach var="periodEntry" items="${listEntry.list}">
                        <b class="periodEntry_period">с&nbsp;${DateUtil.formatMMyyyy(periodEntry.start)}&nbsp;&nbsp;по&nbsp;&nbsp;${DateUtil.formatMMyyyy(periodEntry.end)}&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;${periodEntry.title}</b>
                        <br>
                        <p class="periodEntry_description">${periodEntry.description}</p>
                    </c:forEach>

                </c:forEach>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<button type="button" onclick="window.history.back()">Назад</button>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

