package com.codelab.rest.webservices.trainingtool.payload;

import com.codelab.rest.webservices.trainingtool.model.Course;
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
public class CourseCollectionDto extends IdentifiableDto {
    private String name;
    private List<CourseDto> courses = new ArrayList<>();
}
