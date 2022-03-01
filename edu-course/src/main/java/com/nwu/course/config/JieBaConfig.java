package com.nwu.course.config;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.WordDictionary;
import com.nwu.course.entity.MyDictionary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class JieBaConfig {

    @Bean
    public JiebaSegmenter jiebaSegmenter(){
        Resource resource = new ClassPathResource("my.dict");
        Path path = null;
        try {
            path = Paths.get(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //加载自定义的词典进词库
        WordDictionary.getInstance().loadUserDict( path ) ;
        return new JiebaSegmenter();
    }

    @Bean
    public MyDictionary myDictionary(){
        return new MyDictionary();
    }
}
