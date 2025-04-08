package com.codelab.rest.webservices.trainingtool.repository;

import com.codelab.rest.webservices.trainingtool.model.Course;
import com.codelab.rest.webservices.trainingtool.model.Module;
import java.util.*;

public interface ModuleRepository extends IdentifiableRepository<Module> {
    List<Module> findByCourse(Course course);
}