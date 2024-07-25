
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="WEB-INF/jsp/fragments/header.jsp"/>
<section>
    <table>
        <tr>
            <th>Идентификатор</th>
            <th>Имя</th>
        </tr>
        <jsp:useBean id="resumes" scope="request" type="java.util.List"/>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}">${resume.uuid}
                </a></td>
                <td>${resume.fullName}
                </td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>
