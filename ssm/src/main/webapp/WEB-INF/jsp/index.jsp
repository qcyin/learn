<%@page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="mt" uri="http://localhost/mytag" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Test</title>
</head>
<body>
<h1>hello world!</h1>
<h2>${abc}</h2>
<mt:myTag message="456d">123</mt:myTag>
</body>
</html>