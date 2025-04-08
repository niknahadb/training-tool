package com.codelab.rest.webservices.trainingtool.payload;

import com.codelab.rest.webservices.trainingtool.enumerated.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto extends IdentifiableDto{
    private String name;

    private String description;

    private String coverImage;

    private MemberRole type;

    private List<ModuleDto> modules = new ArrayList<>();
}
