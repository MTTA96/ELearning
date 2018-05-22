package com.eways.elearning.Utils;

/**
 * Created by ADMIN on 1/30/2018.
 */

public class ConvertUtf8ToNormal {
    public ConvertUtf8ToNormal(){}
    public String convertUTF8EncodedStringToNormalString(String utf8EncodedString)
    {
        /*utf8EncodedString = "\u0041\u0041\u0041\u0041\u0041";*/

        String normal = "";

        if(utf8EncodedString!=null)
            try
            {
                byte[] b = utf8EncodedString.getBytes("UTF-8");
                normal = new String(b);

            } catch (Exception e)
            {
                if(e!=null)
                    e.printStackTrace();
            }

        return normal;
    }
}
