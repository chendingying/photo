package com.photo.warehouse.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by CDZ on 2018/12/21.
 */
public class ExceptionUtil {

    public static String getStackTrace(Throwable throwable)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        try
        {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally
        {
            pw.close();
        }
    }
}
