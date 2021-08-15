package route;

import model.*;
import service.ServiceAuth;

import java.util.ArrayList;
import java.util.HashMap;

import static route.Route.currentUser;
import static route.Route.responseRedirect;

public class RouteAuth {

    public static String allByAdmin(Request request) {
        // 权限验证
        User currentUser = currentUser(request);

        if (currentUser.role != UserRole.admin) {
            return responseRedirect("/login/view");
        } else {
            // 读出 User 数据
            ArrayList<User> users = ServiceAuth.allUsers();
            // 读出 模板 文件并把数据放进 html 文件
            HashMap<String, Object> data = new HashMap<>();
            data.put("users", users);
            String body = FreemarkerTemplate.render(data, "admin.ftlh");
            return Route.responseHTML(body);
        }

    }

    public static String update(Request request) {
        // 拿到请求数据
        String username = request.form.get("username");
        String newPassword = request.form.get("password");
        // 处理数据
        ServiceAuth.update(username, newPassword);
        return Route.responseRedirect("/admin/users");
    }

    public static String loginView(Request request) {
        User user = Route.currentUser(request);
        String username = user.username;
        String message = String.format("当前登录用户 %s", username);
        HashMap<String, String> data = new HashMap<>();
        data.put("message", message);
        String body = FreemarkerTemplate.render(data, "login.ftlh");
        return Route.responseHTML(body);
    }


    public static String login(Request request) {
        String message;
        String cookie;
        String username = request.form.get("username");
        String password = request.form.get("password");
        String sessionId = ServiceAuth.login(username, password);
        if (sessionId.length() > 0) {
            cookie = String.format("Set-Cookie: session_id=%s\r\n", sessionId);
            message = String.format("登录成功 %s", username);
        } else {
            cookie = "";
            message = "登录失败";
        }

        HashMap<String, String> data = new HashMap<>();
        data.put("message", message);
        String body = FreemarkerTemplate.render(data, "login.ftlh");
        return Route.responseHTML(body, cookie);
    }

    public static String registerView(Request request) {
        HashMap<String, String> data = new HashMap<>();
        data.put("message", "请注册");
        String body = FreemarkerTemplate.render(data, "register.ftlh");
        return Route.responseHTML(body);
    }

    public static String register(Request request) {
        String message;
        String username = request.form.get("username");
        String password = request.form.get("password");
        boolean success = ServiceAuth.register(username, password);
        if (success) {
            message = String.format("注册成功 %s", username);
        } else {
            message = String.format("注册失败");
        }
        HashMap<String, String> data = new HashMap<>();
        data.put("message", message);
        String body = FreemarkerTemplate.render(data, "register.ftlh");
        return Route.responseHTML(body);
    }

}
