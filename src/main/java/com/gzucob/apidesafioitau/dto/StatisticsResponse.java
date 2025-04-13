package com.gzucob.apidesafioitau.dto;

import lombok.Getter;

import java.util.DoubleSummaryStatistics;

@Getter
public class StatisticsResponse {

    private final Long count;
    private final Double sum;
    private final Double avg;
    private final Double min;
    private final Double max;

    public StatisticsResponse() {
        this(new DoubleSummaryStatistics());
    }

    public StatisticsResponse(DoubleSummaryStatistics stats) {
        this.count = stats.getCount();
        this.sum = stats.getSum();
        this.avg = stats.getAverage();
        this.min = stats.getMin() == Double.POSITIVE_INFINITY ? 0 : stats.getMin();
        this.max = stats.getMax() == Double.NEGATIVE_INFINITY ? 0 : stats.getMax();
    }
}


