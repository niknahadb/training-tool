package com.codelab.rest.webservices.trainingtool.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto extends IdentifiableDto {

    private String name;

    private Integer cohortId;

    private Integer programId;

    private Integer courseCollectionId;
}