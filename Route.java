import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Пользовательская аннотация маршрутизации URL-путей
 * Лабораторная работа № 7 - Вариант 5
 * Выполнил студент 4 курса ИВТ-А Юраш Максим
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Route {
    String value();
}
