<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>weibo 编辑</title>

    </head>
    <!-- body 中是浏览器要显示的内容 -->
    <body>
        <form action="/weibo/update" method="get">
            <input name="id" value="${weibo_id}"/>
            <textarea name="content" rows="8" cols="40"></textarea>
            <br/>
            <button type="submit">weibo</button>
        </form>
    </body>
</html>
