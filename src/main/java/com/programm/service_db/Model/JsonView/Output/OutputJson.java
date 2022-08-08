package com.programm.service_db.Model.JsonView.Output;

import lombok.Data;

import java.util.List;

@Data
public class OutputJson {
    private String type;
    private List<OutputCriteria> results;
}
