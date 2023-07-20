package com.akistd.moneybucket.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class UtilConverter {
    private static UtilConverter instance = null;
    public String vndCurrencyConverter(Double value){
        Locale locale = new Locale("vi", "VN");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

        return numberFormat.format(value);
    }

    public String vndCurrencyConverterWithoutSymbol(Double value){
        Locale locale = new Locale("vi", "VN");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) numberFormat).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) numberFormat).setDecimalFormatSymbols(decimalFormatSymbols);

        return numberFormat.format(value).trim();
    }

    public static synchronized UtilConverter getInstance(){
        if (instance == null){
            instance = new UtilConverter();
        }
        return instance;
    }

}
