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
public class LessonDto extends IdentifiableDto {

    private Integer moduleId;

    private Integer courseId;

    private String name;

    private String content;

    private List<String> text = new ArrayList<>();

    private List<String> videos = new ArrayList<>();

    private List<String> images = new ArrayList<>();
}
