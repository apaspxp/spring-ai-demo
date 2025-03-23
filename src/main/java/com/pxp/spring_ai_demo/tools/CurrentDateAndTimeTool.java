package com.pxp.spring_ai_demo.tools;

import com.pxp.spring_ai_demo.model.DateTimeModel;
import com.pxp.spring_ai_demo.model.OutputDateTimeModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class CurrentDateAndTimeTool {

    private DateTimeModel dateTimeModel;

    public CurrentDateAndTimeTool(DateTimeModel dateTimeModel) {
        this.dateTimeModel = dateTimeModel;
    }

    //    @Tool(description = "Get the current date and time in the user's timezone")
    public OutputDateTimeModel getCurrentDateAndTime() {
        System.out.println("******************Message: " + dateTimeModel);
        var format = Objects.isNull(dateTimeModel.dateTimeFormat()) || dateTimeModel.dateTimeFormat().isEmpty() ? "yyyy-MM-dd HH:mm:ss" : dateTimeModel.dateTimeFormat();
//        var format = "yyyy-MM-dd HH:mm:ss";
        return new OutputDateTimeModel(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(format)));
    }
}
