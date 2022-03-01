package com.nwu.statistic.client.impl;

import com.nwu.statistic.client.ExtraClient;
import org.springframework.stereotype.Component;

@Component
public class ExtraClientImpl implements ExtraClient {
    @Override
    public Integer getAnswerNumByDate(String date) {
        return 0;
    }

    @Override
    public Integer getQuestionNumByDate(String date) {
        return 0;
    }

    @Override
    public Integer getArticleNumByDate(String date) {
        return 0;
    }
}
