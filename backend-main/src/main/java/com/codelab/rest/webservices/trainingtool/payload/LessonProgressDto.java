package com.codelab.rest.webservices.trainingtool.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonProgressDto extends IdentifiableDto {

    private Integer lessonId;

    private Integer moduleProgressId;

    private Integer courseProgressId;

    private Boolean completed;

    private Integer userId;
}
