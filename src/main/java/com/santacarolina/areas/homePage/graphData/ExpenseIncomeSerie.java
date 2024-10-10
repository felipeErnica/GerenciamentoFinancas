package com.santacarolina.areas.homePage.graphData;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ExpenseIncomeSerie {

    private String name;
    private List<Date> dateList;
    private List<Double> valueList;

    public ExpenseIncomeSerie(String name, List<Date> dateList, List<Double> valueList) {
        this.name = name;
        this.dateList = dateList;
        this.valueList = valueList;
    }

    public String getName() { return name; }
    public List<Double> getValueList() { return valueList; }
    public List<Date> getDateList() { return dateList; }

}
