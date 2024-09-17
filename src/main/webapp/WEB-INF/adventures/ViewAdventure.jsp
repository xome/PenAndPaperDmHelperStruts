<%@ page import="de.mayer.penandpaperdmhelper.adventures.model.Text" %>
<%@ page import="de.mayer.penandpaperdmhelper.adventures.model.Picture" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>DM Helper</title>
    <script type="javascript">
        function presentPicture(imageSource) {
            let presentationWindow = window.open("", "presentationWindow") //todo: hier gehts weiter! windowFeatures nach passend bauen
        }
    </script>
</head>
<body>
<h1><s:property value="adventureName"/></h1>
<s:iterator value="chapters" var="chapter">
    <s:property value="#chapter.name"/> - <s:property value="#chapter.approximateDurationInMinutes"/> Minutes
    <ol>
        <s:iterator value="records" var="record">
            <s:if test="%{isText(#record)}">
                <li><s:property value="%{getText(#record)}"/></li>
            </s:if>
            <s:elseif test="%{isPicture(#record)}">
                <li><img src="<s:property value='%{getPictureDataForImgSrc(#record)}' />"/></li>
                <s:if test="%{isShareableWithGroup(#record)}">
                    <button onclick="presentPicture(<s:property value='%{getPictureDataForImgSrc(#record)}'/>)"></button>
                </s:if>
            </s:elseif>
            <s:elseif test="%{isEnvironmentLightning(#record)}">
                <li></li>
            </s:elseif>
        </s:iterator>
    </ol>
</s:iterator>
</body>
</html>
