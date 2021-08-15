<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>comment</title>
</head>
<body>
<form action="/comment/add" method="get">
    <input name="id" value="${weibo_id}"/>
    <textarea name="content" rows="8" cols="40"></textarea>
    <br/>
    <button type="submit">comment</button>
</form>
</body>
</html>
