package route;

import model.*;
import service.ServiceTodo;
import util.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class RouteTodo {
    public static String all(Request request) {
        // 显示所有 todo
        User currentUser = Route.currentUser(request);
        ArrayList<Todo> todos = ServiceTodo.currentUserTodos(currentUser);
        // 读出 模板 文件并把数据放进 html 文件
        HashMap<String, Object> data = new HashMap<>();
        data.put("todos", todos);
        String body = FreemarkerTemplate.render(data, "todo_all.ftlh");
        return Route.responseHTML(body);
    }

    public static String add(Request request) {
        // 添加 todo 路由
        // 拿到请求带上的数据
        // 取数据
        Utils.log("routeTodo query %s", request.query);
        if (request.query.size() > 0) {
            String content = request.query.get("content");
            User currentUser = Route.currentUser(request);
            // 处理数据
            ServiceTodo.add(content, currentUser);
        }
        // 返回数据
        return Route.responseRedirect("/todo/all");
    }

    public static String delete(Request request) {
        // 拿数据
        int id = Integer.parseInt(request.query.get("id"));
        User user = Route.currentUser(request);
        // 处理数据
        if (ServiceTodo.currentUserTodo(id, user.id)) {
            ServiceTodo.delete(id);
        }
        // 返回数据
        return Route.responseRedirect("/todo/all");
    }

    public static String edit(Request request) {
        // 拿到请求 id
        int id = Integer.parseInt(request.query.get("id"));
        User user = Route.currentUser(request);
        // 处理数据
        if (ServiceTodo.currentUserTodo(id, user.id)) {
            // 读出 模板 文件并把数据放进 html 文件
            HashMap<String, Object> data = new HashMap<>();
            data.put("todo_id", id);
            String body = FreemarkerTemplate.render(data, "todo_edit.ftlh");
            return Route.responseHTML(body);
        } else {
            return Route.responseRedirect("/todo/all");
        }

    }

    public static String update(Request request) {
        // 拿到请求数据
        int id = Integer.parseInt(request.query.get("id"));
        String content = request.query.get("content");
        // 处理数据
        ServiceTodo.update(id, content);
        // 返回数据
        return Route.responseRedirect("/todo/all");
    }
}
