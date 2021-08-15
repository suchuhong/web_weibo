<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>todo</title>
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
        </nav>
    </div>
</header>

<div class="_3kbg6I">
    <div class="_3VRLsv" role="main">
        <div class="_gp-ck">
            <center><h1>Todo List</h1></center>

            <div class="content">
                <div class="header">
                    <form action="/todo/add" method="get">

                        <textarea name="content" rows="8" cols="40" placeholder="添加 todo"></textarea>
                        <button type="submit">
                            <span class="entypo-list-add"> todo </span>
                        </button>
                    </form>
                </div>

                <div class="todoItem">
                    <#list todos as todo>
                        <h3>
                            ${todo.id}: ${todo.content}
                            <button>
                                <a href=/todo/delete?id=${todo.id}>删除</a>
                            </button>
                            <button>
                                <a href=/todo/edit?id=${todo.id}>编辑</a>
                            </button>

                        </h3>
                    </#list>
                </div>
            </div>

        </div>
    </div>
</div>

</body>
</html>
