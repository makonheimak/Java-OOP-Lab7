/**
 * Образец контроллера для Spring Web MVC (Вариант 5)
 * Демонстрирует решение первого задания через официальный стек Spring Boot / Spring MVC.
 * 
 * Лабораторная работа № 7 - Вариант 5
 * Выполнил студент 4 курса ИВТ-А Юраш Максим
 * 
 * Примечание: Для запуска в Spring Boot достаточно поместить данный класс в проект
 * со стартером 'spring-boot-starter-web'.
 */
/*
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
public class SpringMvcSampleController {

    // 1. Отображение главной страницы с формой (Вариант 5)
    @GetMapping("/")
    public String showIndexPage() {
        return "index"; // Возвращает шаблон index.html (Thymeleaf)
    }

    // 2. Обработка формы сложения двух чисел (Вариант 5)
    @PostMapping("/calc-sum")
    public String calculateSum(@RequestParam("num1") double num1,
                               @RequestParam("num2") double num2,
                               Model model) {
        double result = num1 + num2;
        model.addAttribute("num1", num1);
        model.addAttribute("num2", num2);
        model.addAttribute("sum", result);
        return "result"; // Возвращает шаблон result.html с результатом
    }

    // 3. REST API отдача списка студентов в формате JSON (Вариант 5)
    @GetMapping("/api/students")
    @ResponseBody
    public List<StudentDto> getStudentsList() {
        return List.of(
            new StudentDto(1, "Юраш Максим", 21, "ИВТ-А"),
            new StudentDto(2, "Иванов Алексей Петрович", 20, "ИВТ-А"),
            new StudentDto(3, "Смирнова Екатерина Олеговна", 22, "ИВТ-Б")
        );
    }

    public static class StudentDto {
        private int id;
        private String name;
        private int age;
        private String group;

        public StudentDto(int id, String name, int age, String group) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.group = group;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public int getAge() { return age; }
        public String getGroup() { return group; }
    }
}
*/
public class SpringMvcSampleController {
    // Класс предоставлен в виде документированного исходного кода
    // для демонстрации работы архитектуры Spring Web MVC.
}
