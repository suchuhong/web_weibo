<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>message</title>
</head>
<body>
<a href='/'>主页</a>
<form action="/message/add/get" method="get">
    <textarea name="content" rows="8" cols="40"></textarea>
    <br/>
    <button type="submit">GET 提交</button>
</form>
<form action="/message/add/post" method="post">
    <textarea name="content" rows="8" cols="40"></textarea>
    <br>
    <button type="submit">POST 提交</button>
</form>
<#list messages as message>
    <h4>
        作者: ${message.author}, 内容: ${message.content}
    </h4>
</#list>
</body>
</html>