package com.codelab.rest.webservices.trainingtool.payload;

import com.codelab.rest.webservices.trainingtool.enumerated.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends IdentifiableDto {

    private String firstName;

    private String lastName;

    private String email;

    private MemberRole role;

    private Integer teamId;

    private Integer cohortId;

    private Integer programId;
}
