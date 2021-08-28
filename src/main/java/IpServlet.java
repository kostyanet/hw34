import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Реализовать веб-приложение, которое принимает GET запросы, и хранит в памяти
 * список уникальных ip адресов, сделавших к нему запрос, и соответствующих
 * значений http-заголовка User-Agent.
 * При запросе выдает html документ с текущим списком ( при этом IP и User-Agent пользователя,
 * сделавшего запрос - выделить жирным текстом - html тег <b></b>)
 * Пример элемента списка:
 * 127.0.0.1 :: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:71.0) Firefox/71.0
 */
public class IpServlet extends HttpServlet {
    private final HashMap<String, String> addresses = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter responseBody = resp.getWriter();
        String ipAddr = req.getRemoteAddr();
        String userAgent = req.getHeader("User-Agent");
        addresses.put(ipAddr, userAgent);

        resp.setContentType("text/html");

        responseBody.println("<ul>");
        addresses.forEach((ip, ua) -> {
            String tag = ip.equals(ipAddr) ? "b" : "span";
            responseBody.println("<li><" + tag + ">" + ip + "&nbsp;::&nbsp;" + ua + "</" + tag + "></li>");
        });
        responseBody.println("</ul>");
    }
}
