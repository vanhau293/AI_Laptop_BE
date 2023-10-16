package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image")
public class ImageController {
    private static final String URL_IMG="src/main/java/com/example/demo/image/";
    @GetMapping(value = "{image}")
    public ResponseEntity<Object> loadImageProduct(@PathVariable("image")String image){
        try{
            Path   path  = Paths.get(URL_IMG + image);
            byte[] bytes = Files.readAllBytes(path);
            return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
