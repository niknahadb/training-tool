package com.codelab.rest.webservices.trainingtool.repository;

import com.codelab.rest.webservices.trainingtool.model.CourseProgress;
import com.codelab.rest.webservices.trainingtool.model.ModuleProgress;

import java.util.List;


public interface ModuleProgressRepository extends IdentifiableRepository<ModuleProgress>  {
    List<ModuleProgress> findByCourseProgress(CourseProgress courseProgress);


}
