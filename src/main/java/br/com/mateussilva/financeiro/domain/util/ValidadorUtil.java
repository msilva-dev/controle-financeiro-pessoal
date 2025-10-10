package br.com.mateussilva.financeiro.domain.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidadorUtil {

    private static final String REGEX_EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private static final Pattern PADRAO_EMAIL = Pattern.compile(REGEX_EMAIL);

    public static boolean isEmailValido(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = PADRAO_EMAIL.matcher(email);
        return matcher.matches();
    }
}
