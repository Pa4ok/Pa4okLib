package ru.pa4ok.library.javafx;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.pa4ok.library.javafx.exception.FieldValidationException;
import ru.pa4ok.library.util.OtherUtils;

import java.util.function.Predicate;

public class ValidationUtil
{
    public static double validateRusDouble(String s, Predicate<Double> filter, String errorText) throws FieldValidationException
    {
        if(s == null || s.isEmpty()) {
            DialogUtil.showError(errorText);
            throw new FieldValidationException("Null or empty value");
        }

        double d;
        try {
            d = OtherUtils.parseRusDouble(s);
        } catch (Exception e) {
            DialogUtil.showError(errorText);
            throw new FieldValidationException(e);
        }

        if(filter != null && !filter.test(d)) {
            DialogUtil.showError(errorText);
            throw new FieldValidationException("Filter value error");
        }

        return d;
    }

    public static double validateRusDouble(TextField field, Predicate<Double> filter, String errorText) throws FieldValidationException
    {
        return validateRusDouble(field.getText(), filter, errorText);
    }

    public static double validateRusDouble(TextArea field, Predicate<Double> filter, String errorText) throws FieldValidationException
    {
        return validateRusDouble(field.getText(), filter, errorText);
    }

    public static double validateRusDouble(TextField field, String errorText) throws FieldValidationException
    {
        return validateRusDouble(field.getText(), null, errorText);
    }

    public static double validateRusDouble(TextArea field, String errorText) throws FieldValidationException
    {
        return validateRusDouble(field.getText(), null, errorText);
    }

    public static String validateString(String s, Predicate<String> filter, String errorText)
    {
        if(filter != null && !filter.test(s != null ? s : "")) {
            DialogUtil.showError(errorText);
            throw new FieldValidationException("Filter value error");
        }

        return s;
    }

    public static String validateString(TextField field, Predicate<String> filter, String errorText)
    {
        return validateString(field.getText(), filter, errorText);
    }

    public static String validateString(TextArea field, Predicate<String> filter, String errorText)
    {
        return validateString(field.getText(), filter, errorText);
    }

    public static String validateString(TextField field, String errorText)
    {
        return validateString(field.getText(), null, errorText);
    }

    public static String validateString(TextArea field, String errorText)
    {
        return validateString(field.getText(), null, errorText);
    }

    public static <T> T validateComboBox(ComboBox<T> box, String errorText) throws FieldValidationException
    {
        T t = box.getValue();
        if(t == null) {
            DialogUtil.showError(errorText);
            throw new FieldValidationException("Null box value");
        }
        return t;
    }
}
