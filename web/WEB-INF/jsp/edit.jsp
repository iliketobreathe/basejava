<%@ page import="ru.basejava.iliketobreathe.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.basejava.iliketobreathe.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt><b>Имя:</b></dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="ru.basejava.iliketobreathe.model.AbstractSection"/>
            <b>${type.title}:</b><br/>
            <c:choose>
                <c:when test="${type=='PERSONAL' || type=='OBJECTIVE'}">
                    <input type="text" name="${type.name()}" size=30 value="<%=section%>"><br/><br/>
                </c:when>
                <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                    <textarea name="${type.name()}" rows="5" cols="100"><%=String.join("\n", ((ListSection)section).getElements())%></textarea><br/><br/>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="organization" items="<%=((OrganizationSection)section).getOrganizations()%>">
                        Организация:<br/>
                        <input type="text" name="${type.name()}" size="30" value="${organization.homePage.name}"/><br/>
                        Домашняя страницы:<br/>
                        <input type="text" name="${type.name()}url" size="30" value="${organization.homePage.url}"/><br/>
                        Периоды работы<br/>
                        <c:forEach var="period" items="${organization.periods}" varStatus="index">
                            Дата начала:<br/>
                            <input type="text" name="${type.name()}startDate${index.index}" size="30" value="${period.startDate}"/><br/>
                            Дата окончания:<br/>
                            <input type="text" name="${type.name()}endDate${index.index}" size="30" value="${period.endDate}"/><br/>
                            Позиция:<br/>
                            <input type="text" name="${type.name()}title${index.index}" size="30" value="${period.title}"/><br/>
                            Описание:<br/>
                            <input type="text" name="${type.name()}description${index.index}" size="30" value="${period.description}"/><br/>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
