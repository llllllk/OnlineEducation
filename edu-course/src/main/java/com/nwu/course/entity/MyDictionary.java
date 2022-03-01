package com.nwu.course.entity;

import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Data
public class MyDictionary {

    private Map<String,String> map;

    public MyDictionary(){
        map=new HashMap<>();
        Resource resource = new ClassPathResource("my.dict");
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br=null;
        try {
            is = resource.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String data = null;
            while((data = br.readLine()) != null) {
                String[] s = data.split(" ");
                map.put(s[0],s[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                isr.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyDictionary dictionary=new MyDictionary();
    }
}
