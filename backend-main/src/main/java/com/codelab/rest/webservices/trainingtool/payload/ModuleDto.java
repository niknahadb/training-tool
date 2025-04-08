package com.codelab.rest.webservices.trainingtool.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModuleDto extends IdentifiableDto {

    private String name;

    private String description;

    private List<LessonDto> lessons = new ArrayList<>();

    private Integer courseId;
}
