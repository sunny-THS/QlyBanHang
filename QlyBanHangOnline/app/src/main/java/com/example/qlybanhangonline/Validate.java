package com.example.qlybanhangonline;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Validate {

    public static boolean check_Email(String email)
    {
        String reg = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(reg);
    }

    public static boolean check_SoDT(String sdt)
    {
        String reg = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
        return sdt.matches(reg);
    }

    public static boolean check_Ngay(String pNgay) {
        SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
        sdfrmt.setLenient(false);
        try
        {
            Date javaDate = sdfrmt.parse(pNgay);
        }
        /* Date format is invalid */
        catch (ParseException e)
        {
            return false;
        }
        return true;
    }

}
