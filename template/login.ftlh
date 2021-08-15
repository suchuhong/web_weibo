<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
</head>
<body>
    <a href='/login/view'>登录</a>
    <a href='/register/view'>注册</a>
    <a href='/'>主页</a>

    <form action="/login" method="post">
        <input name="username" />
        <br>
        <input name="password" />
        <br>
        <button type="submit">登录</button>
    </form>

    <h1>${message}</h1>
</body>
</html>