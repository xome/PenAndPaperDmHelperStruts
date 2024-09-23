<%@ page import="de.mayer.penandpaperdmhelper.adventures.model.Text" %>
<%@ page import="de.mayer.penandpaperdmhelper.adventures.model.Picture" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>DM Helper</title>
</head>
<body>

<script language="JavaScript"
        type="application/javascript"
        src="${pageContext.request.contextPath}/js/presentPictureToGroup.js"></script>

<h1><s:property value="adventureName"/></h1>
<s:iterator value="chapters" var="chapter">
    <s:property value="#chapter.name"/> - <s:property value="#chapter.approximateDurationInMinutes"/> Minutes
    <ol>
        <s:iterator value="records" var="record">
            <li>
                <s:if test="%{isText(#record)}">
                    <s:property value="%{getText(#record)}"/>
                </s:if>

                <s:elseif test="%{isPicture(#record)}">
                    <img id="picture<s:property value="#record.index"/>"
                         src="<s:property value='%{getPictureDataForImgSrc(#record)}'/>"/>
                    <s:if test="%{renderPresentationButton(#record)}">
                        <button onclick="presentPicture('picture<s:property value="#record.index"/>')">Present
                            to group
                        </button>
                    </s:if>
                </s:elseif>

                <s:elseif test="%{isEnvironmentLightning(#record)}">
                    <s:if test="%{hueIp == null}">
                        <label>Hue Bridge is not set up. Please set it up here: <a href="<s:url action="HueSetup" namespace="/setup"/>">click</a></label>
                    </s:if>
                </s:elseif>

            </li>
        </s:iterator>
    </ol>
</s:iterator>
</body>
</html>
