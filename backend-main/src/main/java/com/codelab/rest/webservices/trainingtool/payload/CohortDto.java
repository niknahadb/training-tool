package com.codelab.rest.webservices.trainingtool.payload;

import com.codelab.rest.webservices.trainingtool.enumerated.CohortType;
import com.codelab.rest.webservices.trainingtool.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CohortDto extends IdentifiableDto {

    private CohortType type;

    private Integer year;

    private List<TeamDto> teams;

    private List<ProgramDto> programs;
}