package route;

import model.User;
import model.Weibo;
import service.ServiceAuth;
import service.ServiceWeibo;
import util.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class RouteWeibo {

    public static String all(Request request) {
        Utils.log("routeWeibo query %s", request.query);
        // 显示所有 weibo
        User currentUser;
        // 当用户访问 /weibo/all?user_id=1 的时候，能访问用户 id 为 1 的所有微博。
        // 当用户访问 /weibo/all 的时候，能访问当前登陆用户的所有微博。
        if (request.query.get("user_id") != null) {
            int userId = Integer.parseInt(request.query.get("user_id"));
            currentUser = ServiceAuth.userFromId(userId);
        } else {
            currentUser = Route.currentUser(request);
        }

        ArrayList<Weibo> weibos = ServiceWeibo.currentUserWeibos(currentUser);
        // 读出 模板 文件并把数据放进 html 文件
        HashMap<String, Object> data = new HashMap<>();
        data.put("weibos", weibos);
        String body = FreemarkerTemplate.render(data, "weibo_all.ftlh");
        return Route.responseHTML(body);
    }

    public static String add(Request request) {
        // 添加 weibo 路由
        // 拿到请求带上的数据
        // 取数据
        Utils.log("routeWeibo query %s", request.query);
        if (request.query.size() > 0) {
            String content = request.query.get("content");
            User currentUser = Route.currentUser(request);
            // 处理数据
            ServiceWeibo.add(content, currentUser);
        }
        // 返回数据
        return Route.responseRedirect("/weibo/all");
    }

    public static String commentNew(Request request) {
        int id = Integer.parseInt(request.query.get("id"));
        // 读出 模板 文件并把数据放进 html 文件
        HashMap<String, Object> data = new HashMap<>();
        data.put("weibo_id", id);
        String body = FreemarkerTemplate.render(data, "comment_new.ftlh");
        return Route.responseHTML(body);
    }

    public static String commentAdd(Request request) {
        Utils.log("routeWeibo query %s", request.query);
        if (request.query.size() > 0) {
            String content = request.query.get("content");
            int weiboId = Integer.parseInt(request.query.get("id"));
            User currentUser = Route.currentUser(request);
            // 处理数据
            ServiceWeibo.commentAdd(content, currentUser, weiboId);
        }
        // 返回数据
        return Route.responseRedirect("/weibo/all");
    }

    public static String commentDelete(Request request) {
        // 拿数据
        int id = Integer.parseInt(request.query.get("id"));
        User user = Route.currentUser(request);
        // 处理数据
        // 权限验证
        if (ServiceWeibo.currentUserWeiboComment(id, user.getId())) {
            ServiceWeibo.commentDelete(id);
        }
        // 返回数据
        return Route.responseRedirect("/weibo/all");
    }

    public static String delete(Request request) {
        // 拿数据
        int id = Integer.parseInt(request.query.get("id"));
        User user = Route.currentUser(request);
        // 处理数据
        if (ServiceWeibo.currentUserWeibo(id, user.id)) {
            ServiceWeibo.delete(id);
        }
        // 返回数据
        return Route.responseRedirect("/weibo/all");
    }

    public static String edit(Request request) {
        // 拿到请求 id
        int id = Integer.parseInt(request.query.get("id"));
        User user = Route.currentUser(request);
        if (ServiceWeibo.currentUserWeibo(id, user.id)) {
            // 读出 模板 文件并把数据放进 html 文件
            HashMap<String, Object> data = new HashMap<>();
            data.put("weibo_id", id);
            String body = FreemarkerTemplate.render(data, "weibo_edit.ftlh");
            return Route.responseHTML(body);
        } else {
            return Route.responseRedirect("/weibo/all");
        }

    }

    public static String update(Request request) {
        // 拿到请求数据
        int id = Integer.parseInt(request.query.get("id"));
        String content = request.query.get("content");
        User user = Route.currentUser(request);
        if (ServiceWeibo.currentUserWeibo(id, user.id)) {
            // 处理数据
            ServiceWeibo.update(id, content);
        }
        // 返回数据
        return Route.responseRedirect("/weibo/all");
    }

    public static String commentEdit(Request request) {
        // 拿到请求 id
        int id = Integer.parseInt(request.query.get("id"));
        // 读出 模板 文件并把数据放进 html 文件
        HashMap<String, Object> data = new HashMap<>();
        data.put("comment_id", id);
        String body = FreemarkerTemplate.render(data, "comment_edit.ftlh");
        return Route.responseHTML(body);
    }

    public static String commentUpdate(Request request) {
        // 拿到请求数据
        int id = Integer.parseInt(request.query.get("id"));
        String content = request.query.get("content");
        User user = Route.currentUser(request);
        // 处理数据
        // 权限验证
        if (ServiceWeibo.currentUserWeiboComment(id, user.getId())) {
            ServiceWeibo.commentUpdate(id, content);
        }
        // 返回数据
        return Route.responseRedirect("/weibo/all");
    }
}
