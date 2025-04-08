package com.codelab.rest.webservices.trainingtool.payload;

import com.codelab.rest.webservices.trainingtool.model.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class IdentifiableDto {

    private Integer id;

    private Date createDate;

    private Date modifyDate;
}
