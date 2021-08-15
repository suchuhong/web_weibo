package route;

import model.*;
import service.ServiceMessage;

import java.util.ArrayList;
import java.util.HashMap;

public class RouteMessage {

    public static String all(Request request) {

        // 显示当前用户的 message
        User currentUser = Route.currentUser(request);
        ArrayList<Message> messages = ServiceMessage.currentUserMessages(currentUser);
        // 显示所有用户的 message
        // ArrayList<Message> messages = ServiceMessage.allMessages();
        // 读出 模板 文件并把数据放进 html 文件
        HashMap<String, Object> data = new HashMap<>();
        data.put("messages", messages);
        String body = FreemarkerTemplate.render(data, "message_all.ftlh");
        return Route.responseHTML(body);
    }

    public static String addGet(Request request) {

        User currentUser = Route.currentUser(request);
        if (request.query.size() > 0) {
            ServiceMessage.add(currentUser, request.query.get("content"));
        }
        return Route.responseRedirect("/message/all");
    }

    public static String addPost(Request request) {

        User currentUser = Route.currentUser(request);
        if (request.form.size() > 0) {
            ServiceMessage.add(currentUser, request.form.get("content"));
        }
        return Route.responseRedirect("/message/all");
    }

}
