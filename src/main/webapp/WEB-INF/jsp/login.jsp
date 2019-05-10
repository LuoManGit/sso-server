<%@page contentType="text/html; charset=utf-8" language="java"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录页面</title>
</head>
<body>
<form action="/sso/doLogin?service="+${service} method="post">
    <%=service%><br/>
    用户名：<input type="text" name="name"/><br/>
    密码：<input type="password" name="password"/><br/>
    <input type="submit" value="提交">
</form>
</body>
</html>