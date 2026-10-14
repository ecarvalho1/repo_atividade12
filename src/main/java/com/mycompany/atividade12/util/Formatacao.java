package com.mycompany.atividade12.util;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class Formatacao {

    public static final DateTimeFormatter DATA_BR =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    public static double lerDecimal(String texto) {
        return Double.parseDouble(texto.trim().replace(",", "."));
    }

    public static String mostrarDecimal(double valor) {
        return String.valueOf(valor).replace(".", ",");
    }
}