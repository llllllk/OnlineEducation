package com.nwu.statistic.client;


import com.nwu.statistic.client.impl.ExtraClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-extra",fallback = ExtraClientImpl.class)
@Component
public interface ExtraClient {
    @GetMapping(value = "/extra/rest/answers/getNumByDate/{date}")
    public Integer getAnswerNumByDate(@PathVariable("date") String date);

    @GetMapping(value = "/extra/rest/questions/getNumByDate/{date}")
    public Integer getQuestionNumByDate(@PathVariable("date") String date);

    @GetMapping(value = "/extra/rest/articles/getNumByDate/{date}")
    public Integer getArticleNumByDate(@PathVariable("date") String date);
}
