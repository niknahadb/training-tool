package com.codelab.rest.webservices.trainingtool.payload;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lesson_progress")
public class CourseProgressDto extends IdentifiableDto{

    private Integer courseId;

    private boolean assigned;

    private Integer userId;

    private ArrayList<ModuleProgressDto> moduleProgress = new ArrayList<>();
}
