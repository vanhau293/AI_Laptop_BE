package com.example.demo.bean;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import javax.xml.bind.DatatypeConverter;

public class ImageUtils {
    public static String downloadImage(String imageName,String binaryImage,String URL_IMG) {
        if (!Objects.isNull(imageName)) {
            try {
                String[] strings = binaryImage.split(",");
                byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
                File file = new File(URL_IMG + imageName);
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
                outputStream.write(data);
                return "SUCESS";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "FAIL";
    }
}
