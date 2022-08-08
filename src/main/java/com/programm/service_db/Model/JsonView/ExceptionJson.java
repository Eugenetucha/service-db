package com.programm.service_db.Model.JsonView;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionJson {
    private String type;
    private String message;
}
