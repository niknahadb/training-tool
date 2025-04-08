package com.codelab.rest.webservices.trainingtool.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModuleProgressDto extends IdentifiableDto {

    private Integer courseProgressId;

    private Integer moduleId;

    private Integer userId;

    private List<LessonProgressDto> lessonProgress = new ArrayList<>();
}
