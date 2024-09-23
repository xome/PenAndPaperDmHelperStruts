<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>DM Helper</title>
    <s:head />
</head>
<body>

<s:if test="%{hueIp != null}">
    <label>The configured Hue Bridge's IP is: </label><s:property value="hueIp"/>
    <s:if test="%{hueToken != null}">
        <label>The assigned Hue Token is: </label><s:property value="hueToken"/>
    </s:if>
    <s:else>
        <label>Press Button on your Hue Bridge and then press this button to get a token: </label>
        <button><a href="<s:url action="GetHueToken" />">click</a></button>
    </s:else>
</s:if>

<h2>Manually configure IP</h2>
<s:form action="HueSetupSubmit">
    <s:textfield name="hueConfigurationBean.ip" label="IP" value="%{hueIp}"/>
    <s:submit/>
</s:form>


</body>
</html>
