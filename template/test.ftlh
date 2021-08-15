<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>template demo</title>
</head>
<body>

<div>
    <h1>todos</h1>
<#list todos as todo>

    ${todo.id}: ${todo.content} <br>
</#list>
</div>

<div>
    <#-- 虽然这里调用看起来是 todo1.id   -->
    <#-- 但真正调用的是 todo1.getId()  -->
    <#-- 这个框架就这么规定的  -->
    todo1: ${todo1.id} ${todo1.content}
</div>
</body>
</html>
