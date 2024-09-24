<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>DM Helper</title>
    <s:head />
</head>
<body>

<s:if test="%{hueConfigurationBean.ip != null}">
    <label>The configured Hue Bridge's IP is: </label><s:property value="hueConfigurationBean.ip"/>
    <s:if test="%{hueConfigurationBean.token != null}">
        <label>The assigned Hue Token is: </label><s:property value="hueConfigurationBean.token"/>
    </s:if>
    <s:else>
        <label>Press Button on your Hue Bridge and then press this button to get a token: </label>
        <button><a href="<s:url action="GetHueToken" />">click</a></button>
    </s:else>
</s:if>

<h2>Manually configure IP</h2>
<s:form action="HueSetupSubmit">
    <s:textfield name="hueConfigurationBean.ip" label="IP" value="%{hueConfigurationBean.ip}"/>
    <s:submit/>
</s:form>


</body>
</html>
