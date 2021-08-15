<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>todo 编辑</title>
    <!--    导航栏使用以下样式 -->
    <link rel="stylesheet" href="/static?file=12.2f97d03e.chunk.css">
    <link rel="stylesheet" href="/static?file=13.8c3b56fa.chunk.css">
    <link rel="stylesheet" href="/static?file=commons.d40cf249.chunk.css">
    <link rel="stylesheet" href="/static?file=styles.9b5b2500.chunk.css">

    <link rel="stylesheet" href="/static?file=todo.css">
</head>

<body>

<#--    导航栏-->
<header style="width:100%">
    <div class="_2oDcyf _1YyUun _2RZATq">
        <nav class="_3JYrtj">
            <a class="hM7XFL _1OhGeD" href="/">主页</a>
            <a class="hM7XFL _1OhGeD" href="/todo/all">Todo</a>
        </nav>
    </div>
</header>

<div class="_3kbg6I">
    <div class="_3VRLsv" role="main">
        <div class="_gp-ck">
            <div class="content">
                <div class="header">
                    <form action="/todo/update" method="get">
                        <input class="todo" name="id" value="${todo_id}"/>
                        <textarea name="content" rows="8" cols="40"></textarea>
                        <button type="submit">todo</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
