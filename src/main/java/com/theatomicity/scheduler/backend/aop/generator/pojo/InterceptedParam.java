package com.theatomicity.scheduler.backend.aop.generator.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InterceptedParam {
    private String name;
    private Class<?> type;
    private Object value;
}
