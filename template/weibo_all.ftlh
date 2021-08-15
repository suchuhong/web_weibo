<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>weibo</title>
</head>

<body>
<a href='/'>主页</a>
<form action="/weibo/add" method="get">
    <textarea name="content" rows="8" cols="40"></textarea>
    <br/>
    <button type="submit">weibo</button>
</form>

<div>
    <#list weibos as weibo>
        <h3>
            ${weibo.id}: ${weibo.content}
            <a href=/comment/new?id=${weibo.id}>添加评论</a>
            <a href=/weibo/delete?id=${weibo.id}>删除</a>
            <a href=/weibo/edit?id=${weibo.id}>编辑</a>
        </h3>
            <#list weibo.comments as comment>
                <div>
                ${comment.id}: ${comment.content}
                <a href=/comment/delete?id=${comment.id}>删除</a>
<#--                    新增以下语句-->
                <a href=/comment/edit?id=${comment.id}>编辑</a>
                </div>
            </#list>
        </div>
    </#list>

</div>

</body>
</html>
