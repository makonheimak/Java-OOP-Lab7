import java.awt.Desktop;
import java.net.URI;

/**
 * Главный класс запуска Лабораторной работы № 7
 * Тема: Создание веб-приложения (Spring Web MVC, Sockets HTTP)
 * Вариант: 5 (Выполнил студент 4 курса ИВТ-А Юраш Максим)
 */
public class Lab7_Main {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println(" Лабораторная работа № 7: Веб-приложение (Вариант 5)");
        System.out.println(" Выполнил студент 4 курса ИВТ-А Юраш Максим");
        System.out.println("==========================================================");

        WebController controller = new WebController();
        HttpServerApp server = new HttpServerApp(PORT, controller);

        boolean autoBrowse = true;
        if (args.length > 0 && "--no-browse".equalsIgnoreCase(args[0])) {
            autoBrowse = false;
        }

        if (autoBrowse) {
            // Открываем браузер при наличии графического окружения
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        System.out.println("-> Автоматическое открытие браузера: http://localhost:" + PORT + "/");
                        Desktop.getDesktop().browse(new URI("http://localhost:" + PORT + "/"));
                    }
                } catch (Exception ignored) {}
            }).start();
        }

        try {
            server.start();
        } catch (Exception e) {
            System.err.println("Ошибка запуска сервера: " + e.getMessage());
        }
    }
}
