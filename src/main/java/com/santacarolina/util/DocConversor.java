package com.santacarolina.util;

public class DocConversor {

    public static final int CPF_FORMAT = 0;
    public static final int CNPJ_FORMAT = 1;
    public static final int IE_FORMAT = 2;
    public static final int PHONE_FORMAT = 3;


    public static String docFormat(String doc, int docType) {
        if (doc == null) return "";
        if (!isValidFormat(doc,docType)) return doc;
        return switch (docType) {
            case CPF_FORMAT -> doc.substring(0, 3) +
                    "." + doc.substring(3, 6) +
                    "." + doc.substring(6, 9) +
                    "-" + doc.substring(9);
            case CNPJ_FORMAT -> doc.substring(0,2) +
                    "." + doc.substring(2,5) +
                    "." + doc.substring(5,8) +
                    "/" + doc.substring(8,12) +
                    "-" + doc.substring(12,14);
            case IE_FORMAT -> doc.substring(0,3) +
                    "." + doc.substring(3, 6) +
                    "." + doc.substring(6, 9) +
                    "." + doc.substring(9,12);
            case PHONE_FORMAT -> "(" + doc.substring(0, 2) + ") " + doc.substring(2, 7) + "-" + doc.substring(7);
            default -> "";
        };

    }

    public static boolean isValidFormat(String doc, int docType) {
        return switch (docType) {
            case CPF_FORMAT, PHONE_FORMAT -> doc.length() == 11;
            case CNPJ_FORMAT -> doc.length() == 14;
            case IE_FORMAT -> doc.length() == 12;
            default -> false;
        };
    }

}
