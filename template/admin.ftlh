<!DOCTYPE html>
<html>
<!-- head 中是放一些控制信息, 不会被显示 -->
<head>
    <!-- meta charset 指定了页面编码, 否则中文会乱码 -->
    <meta charset="utf-8">
    <!-- title 是浏览器显示的页面标题 -->
    <title>user</title>
</head>
<!-- body 中是浏览器要显示的内容 -->
<body>
<div>
    <#list users as user>
        <h3>
            username: ${user.username}, password: ${user.password}
        </h3>
    </#list>
    <form action="/admin/user/update" method="post">
        <input name="username" />
        <br>
        <input name="password" />
        <br>
        <button type="submit">修改</button>
    </form>

</div>
</body>
</html>
