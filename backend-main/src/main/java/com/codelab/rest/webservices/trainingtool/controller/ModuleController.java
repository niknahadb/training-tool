package com.codelab.rest.webservices.trainingtool.controller;

import com.codelab.rest.webservices.trainingtool.payload.ModuleDto;
import com.codelab.rest.webservices.trainingtool.service.ModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping("{moduleId}")
    public ResponseEntity<ModuleDto> getModuleById(@PathVariable int moduleId) {
        try {
            ModuleDto moduleDTO = moduleService.getModuleById(moduleId);
            return new ResponseEntity<>(moduleDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("from-course/{courseId}")
    public ResponseEntity<List<ModuleDto>> getModulesByCourseId(@PathVariable Integer courseId) {
        try {
            return new ResponseEntity<>(moduleService.getModulesByCourseId(courseId), HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ModuleDto>> getModules() {
        try {
            return new ResponseEntity<>(moduleService.getAllModules(), HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ModuleDto> createModule(@RequestBody ModuleDto moduleDto) {
        try {
            return new ResponseEntity<>(moduleService.createModule(moduleDto), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("{moduleId}/update")
    public ResponseEntity<ModuleDto> updateModule(@RequestBody ModuleDto moduleDTO, @PathVariable int moduleId) {
        try {
            ModuleDto updatedModule = moduleService.updateModuleById(moduleId, moduleDTO);
            return new ResponseEntity<>(updatedModule, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("{moduleId}/delete")
    public ResponseEntity<ModuleDto> deleteModule(@PathVariable int moduleId) {
        try {
            return new ResponseEntity<>(moduleService.deleteModuleById(moduleId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}