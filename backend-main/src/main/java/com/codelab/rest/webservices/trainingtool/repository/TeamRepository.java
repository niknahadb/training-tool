package com.codelab.rest.webservices.trainingtool.repository;
import com.codelab.rest.webservices.trainingtool.model.Team;
import com.codelab.rest.webservices.trainingtool.model.User;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends IdentifiableRepository<Team> {
    List<Team> findByCohortIdAndProgramId(Integer cohortId, Integer programId);
    List<Team> findByCohortId(Integer cohortId);

    List<Team> findByProgramId(Integer programId);

}