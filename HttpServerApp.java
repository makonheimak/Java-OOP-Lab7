import java.io.*;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Многопоточный HTTP-сервер на сокетах с маршрутизацией через рефлексию и аннотации @Route.
 * Лабораторная работа № 7 - Вариант 5
 * 
 * Выполнил студент 4 курса ИВТ-А Юраш Максим
 */
public class HttpServerApp {

    private final int port;
    private final Object controller;
    private final Map<String, Method> routeTable = new HashMap<>();
    private final ExecutorService threadPool = Executors.newFixedThreadPool(8);
    private ServerSocket serverSocket;
    private volatile boolean running = false;

    public HttpServerApp(int port, Object controller) {
        this.port = port;
        this.controller = controller;
        registerAnnotatedRoutes();
    }

    /**
     * Поиск и регистрация методов контроллера, помеченных аннотацией @Route
     */
    private void registerAnnotatedRoutes() {
        Method[] methods = controller.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Route.class)) {
                Route route = method.getAnnotation(Route.class);
                routeTable.put(route.value(), method);
                System.out.println("  [Маршрут] Зарегистрирован путь: " + route.value() + " -> " + method.getName() + "()");
            }
        }
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;
        System.out.println("Сервер успешно запущен на http://localhost:" + port + "/");
        System.out.println("Нажмите Ctrl+C в консоли для остановки сервера.");

        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                threadPool.submit(() -> handleClient(clientSocket));
            } catch (SocketException e) {
                if (!running) break;
            }
        }
    }

    public void stop() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            threadPool.shutdown();
        } catch (IOException ignored) {}
    }

    private void handleClient(Socket socket) {
        try (socket;
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             OutputStream out = socket.getOutputStream()) {

            String requestLine = in.readLine();
            if (requestLine == null || requestLine.isEmpty()) return;

            String[] parts = requestLine.split(" ");
            if (parts.length < 2) return;

            String method = parts[0];
            String fullUri = parts[1];

            // Чтение заголовков
            int contentLength = 0;
            String headerLine;
            while ((headerLine = in.readLine()) != null && !headerLine.isEmpty()) {
                if (headerLine.toLowerCase().startsWith("content-length:")) {
                    contentLength = Integer.parseInt(headerLine.substring(15).trim());
                }
            }

            // Чтение тела запроса для POST
            String body = "";
            if ("POST".equalsIgnoreCase(method) && contentLength > 0) {
                char[] bodyChars = new char[contentLength];
                int read = in.read(bodyChars, 0, contentLength);
                body = new String(bodyChars, 0, read);
            }

            // Разбор URI на путь и параметры
            String path = fullUri;
            Map<String, String> params = new HashMap<>();

            int qIdx = fullUri.indexOf('?');
            if (qIdx != -1) {
                path = fullUri.substring(0, qIdx);
                parseParams(fullUri.substring(qIdx + 1), params);
            }

            if (!body.isEmpty()) {
                parseParams(body, params);
            }

            // Поиск обработчика в таблице маршрутов
            Method targetMethod = routeTable.get(path);
            String responseBody;
            String contentType;
            int statusCode = 200;

            if (targetMethod != null) {
                try {
                    responseBody = (String) targetMethod.invoke(controller, params);
                    if (path.startsWith("/api/")) {
                        contentType = "application/json; charset=UTF-8";
                    } else if (responseBody.trim().startsWith("<!DOCTYPE") || responseBody.trim().startsWith("<html")) {
                        contentType = "text/html; charset=UTF-8";
                    } else {
                        contentType = "text/plain; charset=UTF-8";
                    }
                } catch (Exception ex) {
                    statusCode = 500;
                    responseBody = "<h1>500 Internal Server Error</h1><pre>" + ex.getMessage() + "</pre>";
                    contentType = "text/html; charset=UTF-8";
                }
            } else {
                statusCode = 404;
                responseBody = "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>404</title></head>" +
                        "<body style='font-family:sans-serif;text-align:center;padding:50px;'>" +
                        "<h2>404 - Страница не найдена</h2>" +
                        "<p>Запрошенный путь <code>" + path + "</code> отсутствует на сервере.</p>" +
                        "<a href='/'>Вернуться на главную</a>" +
                        "</body></html>";
                contentType = "text/html; charset=UTF-8";
            }

            byte[] responseBytes = responseBody.getBytes(StandardCharsets.UTF_8);

            // Отправка HTTP-ответа
            String header = "HTTP/1.1 " + statusCode + " OK\r\n" +
                    "Content-Type: " + contentType + "\r\n" +
                    "Content-Length: " + responseBytes.length + "\r\n" +
                    "Server: SimpleJavaSocketsServer/1.0\r\n" +
                    "Connection: close\r\n\r\n";

            out.write(header.getBytes(StandardCharsets.UTF_8));
            out.write(responseBytes);
            out.flush();

        } catch (Exception e) {
            // Ошибка сокета или обрыв соединения клиентом
        }
    }

    private void parseParams(String query, Map<String, String> outParams) {
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int eq = pair.indexOf('=');
            if (eq != -1) {
                String key = URLDecoder.decode(pair.substring(0, eq), StandardCharsets.UTF_8);
                String val = URLDecoder.decode(pair.substring(eq + 1), StandardCharsets.UTF_8);
                outParams.put(key, val);
            }
        }
    }
}
