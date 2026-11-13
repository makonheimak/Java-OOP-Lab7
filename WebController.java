import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Веб-контроллер с маршрутизацией через аннотации @Route
 * Лабораторная работа № 7 - Вариант 5
 * 
 * Реализует:
 * 1. Форму для ввода двух чисел с возвратом их суммы (Часть 1, Вариант 5).
 * 2. Отдачу JSON-ответа со списком студентов (Часть 2, Вариант 5).
 * 
 * Выполнил студент 4 курса ИВТ-А Юраш Максим
 */
public class WebController {

    @Route("/")
    public String index(Map<String, String> params) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"ru\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <title>Лабораторная 7 - Веб-приложение (Вариант 5)</title>\n" +
                "  <style>\n" +
                "    body { font-family: 'Segoe UI', Tahoma, sans-serif; background: #f0f2f5; margin: 0; padding: 25px; color: #333; }\n" +
                "    .container { max-width: 820px; margin: 0 auto; background: #fff; padding: 30px; border-radius: 12px; box-shadow: 0 4px 16px rgba(0,0,0,0.08); }\n" +
                "    h1 { color: #0d6efd; margin-top: 0; border-bottom: 2px solid #e9ecef; padding-bottom: 12px; font-size: 24px; }\n" +
                "    .badge { background: #0d6efd; color: white; padding: 4px 10px; border-radius: 6px; font-size: 13px; font-weight: bold; }\n" +
                "    .card { background: #f8f9fa; border: 1px solid #dee2e6; border-radius: 8px; padding: 20px; margin-bottom: 25px; }\n" +
                "    .card h2 { margin-top: 0; font-size: 18px; color: #212529; }\n" +
                "    label { font-weight: bold; display: block; margin-top: 10px; margin-bottom: 4px; }\n" +
                "    input[type='number'], input[type='text'] { width: 100%; box-sizing: border-box; padding: 10px; border: 1px solid #ced4da; border-radius: 6px; font-size: 15px; }\n" +
                "    .row { display: flex; gap: 15px; }\n" +
                "    .col { flex: 1; }\n" +
                "    button, .btn { display: inline-block; background: #0d6efd; color: #fff; border: none; padding: 10px 20px; font-size: 15px; font-weight: bold; border-radius: 6px; cursor: pointer; text-decoration: none; margin-top: 12px; }\n" +
                "    button:hover, .btn:hover { background: #0b5ed7; }\n" +
                "    .btn-green { background: #198754; }\n" +
                "    .btn-green:hover { background: #157347; }\n" +
                "    pre { background: #212529; color: #00ff66; padding: 15px; border-radius: 6px; overflow-x: auto; font-family: Consolas, monospace; font-size: 14px; }\n" +
                "    footer { text-align: center; margin-top: 25px; color: #6c757d; font-size: 13px; }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <div class=\"container\">\n" +
                "    <h1>Лабораторная работа № 7 <span class=\"badge\">Вариант 5</span></h1>\n" +
                "    <p><b>Выполнил студент 4 курса ИВТ-А:</b> Юраш Максим | ДонГУ - 2026</p>\n" +
                "    \n" +
                "    <!-- Часть 1. Форма сложения двух чисел -->\n" +
                "    <div class=\"card\">\n" +
                "      <h2>Задание 1: Калькулятор суммы двух чисел (Вариант 5)</h2>\n" +
                "      <form action=\"/sum\" method=\"GET\">\n" +
                "        <div class=\"row\">\n" +
                "          <div class=\"col\">\n" +
                "            <label>Первое число (A):</label>\n" +
                "            <input type=\"number\" step=\"any\" name=\"a\" placeholder=\"Введите число A\" required value=\"15\">\n" +
                "          </div>\n" +
                "          <div class=\"col\">\n" +
                "            <label>Второе число (B):</label>\n" +
                "            <input type=\"number\" step=\"any\" name=\"b\" placeholder=\"Введите число B\" required value=\"37\">\n" +
                "          </div>\n" +
                "        </div>\n" +
                "        <button type=\"submit\" class=\"btn-green\">Вычислить сумму (A + B)</button>\n" +
                "      </form>\n" +
                "    </div>\n" +
                "    \n" +
                "    <!-- Часть 2. JSON API списка студентов -->\n" +
                "    <div class=\"card\">\n" +
                "      <h2>Задание 2: REST API - Список студентов в JSON (Вариант 5)</h2>\n" +
                "      <p>Эндпоинт сокетового HTTP-сервера возвращает валидный массив JSON с данными учащихся:</p>\n" +
                "      <a href=\"/api/students\" target=\"_blank\" class=\"btn\">Открыть REST API /api/students</a>\n" +
                "      <button type=\"button\" onclick=\"loadJson()\" style=\"margin-left: 10px;\">Загрузить прямо сюда (AJAX)</button>\n" +
                "      <div id=\"jsonContainer\" style=\"margin-top: 15px; display: none;\">\n" +
                "        <pre id=\"jsonOutput\"></pre>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "    \n" +
                "    <!-- Дополнительные маршруты -->\n" +
                "    <div class=\"card\">\n" +
                "      <h2>Сервисные маршруты сервера:</h2>\n" +
                "      <ul>\n" +
                "        <li><a href=\"/time\">/time</a> - текущее системное время сервера</li>\n" +
                "        <li><a href=\"/api/students\">/api/students</a> - массив студентов в формате JSON</li>\n" +
                "      </ul>\n" +
                "    </div>\n" +
                "    \n" +
                "    <footer>ДонГУ • Кафедра компьютерных технологий • Java Sockets Web Server</footer>\n" +
                "  </div>\n" +
                "  \n" +
                "  <script>\n" +
                "    function loadJson() {\n" +
                "      fetch('/api/students')\n" +
                "        .then(r => r.json())\n" +
                "        .then(data => {\n" +
                "          document.getElementById('jsonOutput').innerText = JSON.stringify(data, null, 2);\n" +
                "          document.getElementById('jsonContainer').style.display = 'block';\n" +
                "        });\n" +
                "    }\n" +
                "  </script>\n" +
                "</body>\n" +
                "</html>";
    }

    /**
     * Вариант 5 (Часть 1): Обработка сложения двух чисел
     */
    @Route("/sum")
    public String calculateSum(Map<String, String> params) {
        String aStr = params.getOrDefault("a", "0");
        String bStr = params.getOrDefault("b", "0");

        double a = 0;
        double b = 0;
        String error = null;

        try {
            a = Double.parseDouble(aStr);
            b = Double.parseDouble(bStr);
        } catch (NumberFormatException e) {
            error = "Некорректный формат входных чисел!";
        }

        double sum = a + b;
        String formattedSum = (sum == (long) sum) ? String.format("%d", (long) sum) : String.format("%.2f", sum);

        return "<!DOCTYPE html>\n" +
                "<html lang=\"ru\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <title>Результат вычисления суммы - Вариант 5</title>\n" +
                "  <style>\n" +
                "    body { font-family: 'Segoe UI', sans-serif; background: #f0f2f5; padding: 40px; text-align: center; }\n" +
                "    .box { background: white; max-width: 500px; margin: 0 auto; padding: 30px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }\n" +
                "    .result { font-size: 32px; font-weight: bold; color: #198754; margin: 20px 0; }\n" +
                "    .details { font-size: 18px; color: #495057; }\n" +
                "    a { display: inline-block; margin-top: 25px; padding: 10px 20px; background: #0d6efd; color: white; text-decoration: none; border-radius: 6px; font-weight: bold; }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <div class=\"box\">\n" +
                "    <h2>Результат вычисления суммы (Вариант 5)</h2>\n" +
                (error != null ?
                        "    <p style=\"color: red; font-size: 18px;\">" + error + "</p>\n" :
                        "    <div class=\"details\">Выражение: " + aStr + " + " + bStr + "</div>\n" +
                        "    <div class=\"result\">Сумма = " + formattedSum + "</div>\n"
                ) +
                "    <br><a href=\"/\">← Вернуться к форме</a>\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>";
    }

    /**
     * Вариант 5 (Часть 2): Отдача JSON-ответа со списком студентов
     */
    @Route("/api/students")
    public String getStudentsJson(Map<String, String> params) {
        return "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Юраш Максим\",\n" +
                "    \"age\": 21,\n" +
                "    \"group\": \"ИВТ-А\",\n" +
                "    \"variant\": 5,\n" +
                "    \"courses\": [\"Объектно-ориентированное программирование\", \"Базы данных\", \"Web MVC\"]\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Иванов Алексей Петрович\",\n" +
                "    \"age\": 20,\n" +
                "    \"group\": \"ИВТ-А\",\n" +
                "    \"courses\": [\"Объектно-ориентированное программирование\"]\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 3,\n" +
                "    \"name\": \"Смирнова Екатерина Олеговна\",\n" +
                "    \"age\": 22,\n" +
                "    \"group\": \"ИВТ-Б\",\n" +
                "    \"courses\": [\"Базы данных\"]\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 4,\n" +
                "    \"name\": \"Кузнецов Дмитрий Сергеевич\",\n" +
                "    \"age\": 19,\n" +
                "    \"group\": \"ИВТ-В\",\n" +
                "    \"courses\": [\"Компьютерные сети\"]\n" +
                "  }\n" +
                "]";
    }

    @Route("/time")
    public String getTime(Map<String, String> params) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        return "<!DOCTYPE html>\n" +
                "<html lang=\"ru\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <title>Системное время сервера - Вариант 5</title>\n" +
                "  <style>\n" +
                "    body { font-family: 'Segoe UI', sans-serif; background: #f0f2f5; padding: 40px; text-align: center; }\n" +
                "    .box { background: white; max-width: 500px; margin: 0 auto; padding: 30px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }\n" +
                "    .time { font-size: 26px; font-weight: bold; color: #0d6efd; margin: 20px 0; }\n" +
                "    a { display: inline-block; margin-top: 15px; padding: 8px 18px; background: #0d6efd; color: white; text-decoration: none; border-radius: 6px; font-weight: bold; }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <div class=\"box\">\n" +
                "    <h2>Системное время сервера (Вариант 5)</h2>\n" +
                "    <div class=\"time\">" + now + "</div>\n" +
                "    <a href=\"/\">← На главную</a>\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>";
    }
}
