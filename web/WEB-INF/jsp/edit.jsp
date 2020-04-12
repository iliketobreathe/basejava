<%@ page import="ru.basejava.iliketobreathe.model.ContactType" %>
<%@ page import="ru.basejava.iliketobreathe.model.ListSection" %>
<%@ page import="ru.basejava.iliketobreathe.model.OrganizationSection" %>
<%@ page import="ru.basejava.iliketobreathe.model.SectionType" %>
<%@ page import="ru.basejava.iliketobreathe.util.DateUtil" %>
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
        <h1>Имя:</h1>
        <dl>
            <input type="text" name="fullName" size=50 value="${resume.fullName}">
        </dl>
        <h2>Контакты:</h2>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h2>Секции:</h2>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="ru.basejava.iliketobreathe.model.AbstractSection"/>
            <hr>
            <h3>${type.title}:</h3>
            <c:choose>
                <c:when test="${type=='PERSONAL' || type=='OBJECTIVE'}">
                    <br><input type="text" name="${type}" size=30 value="<%=section%>"><br><br>
                </c:when>
                <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                    <br><textarea name="${type}" rows="5" cols="100"><%=String.join("\n", ((ListSection)section).getElements())%></textarea><br><br>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="organization" items="<%=((OrganizationSection)section).getOrganizations()%>" varStatus="index">
                        <br><b>Организация:</b>
                        <br><input type="text" name="${type}" size="30" value="${organization.homePage.name}"/><br>
                        Домашняя страницы:<br>
                        <input type="text" name="${type}url" size="30" value="${organization.homePage.url}"/><br>
                        <br>Периоды работы:
                        <c:forEach var="period" items="${organization.periods}">
                            <jsp:useBean id="period" type="ru.basejava.iliketobreathe.model.Organization.Period"/>
                            <br><br>Дата начала:<br>
                            <input type="text" name="${type}startDate${index.index}" size="30" value="<%=DateUtil.write(period.getStartDate())%>"/><br>
                            Дата окончания:<br>
                            <input type="text" name="${type}endDate${index.index}" size="30" value="<%=DateUtil.write(period.getEndDate())%>"/><br>
                            Позиция:<br>
                            <input type="text" name="${type}title${index.index}" size="30" value="${period.title}"/><br>
                            Описание:<br>
                            <input type="text" name="${type}description${index.index}" size="30" value="${period.description}"/><br>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
