package route;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;

public class FreemarkerTemplate {
    static Configuration config;

    static {
        // static 里面的东西只会被初始化一次
        // 指定使用版本
        config = new Configuration(Configuration.VERSION_2_3_31);
        // 指定模板存储文件夹
        try {
            File f = new File("./template");

            config.setDirectoryForTemplateLoading(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 用 utf8 编码
        config.setDefaultEncoding("utf-8");
        // 在 html 页面显示异常信息
        config.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        // 下面三个不用管 属于框架遗留问题
        config.setLogTemplateExceptions(false);
        config.setWrapUncheckedExceptions(true);
        config.setFallbackOnNullLoopVariable(false);
    }

    public static String render(Object data, String templateFileName) {
        Template template;
        try {
            template = config.getTemplate(templateFileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(result);
        try {
            template.process(data, writer);
        } catch (TemplateException | IOException e) {
            String message = String.format("模板 process 失败 <%s> error<%s>", data, e);
            throw new RuntimeException(message, e);
        }
        return result.toString();
    }
}
