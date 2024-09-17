<%@ page import="de.mayer.penandpaperdmhelper.adventures.model.Adventure" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>DM Helper</title>
</head>
<body>

<s:iterator value="adventures" var="adventure">
    <s:property value="#adventure.name"/>
</s:iterator>

</body>
</html>
