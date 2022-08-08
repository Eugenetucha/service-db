package com.programm.service_db.Model.JsonView.Output;

import lombok.Data;
import java.util.List;

@Data
public class OutputCriteria {
     private Object criteria;
     private List<?> results;
}
