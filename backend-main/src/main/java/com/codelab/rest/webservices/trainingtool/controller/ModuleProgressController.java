package com.codelab.rest.webservices.trainingtool.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codelab.rest.webservices.trainingtool.service.*;
import com.codelab.rest.webservices.trainingtool.payload.ModuleProgressDto;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
@RestController
@RequestMapping("module-progress")
public class ModuleProgressController {

    private final ModuleProgressService moduleProgressService;

    public ModuleProgressController(ModuleProgressService moduleProgressService){
        this.moduleProgressService = moduleProgressService;

    }

    @GetMapping("{moduleProgressId}")
    public ResponseEntity<ModuleProgressDto> getByModuleProgressId(@PathVariable int moduleProgressId) {
        try {
            ModuleProgressDto moduleProgressDTO = moduleProgressService.getModuleProgressById(moduleProgressId);
            return new ResponseEntity<>(moduleProgressDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<ModuleProgressDto>> getModuleProgresses(@RequestParam(required = false) Integer courseProgressId,
                                                                       @RequestParam(required = false) Integer moduleId,
                                                                       @RequestParam(required = false) Integer userId) {
        try {
            List<ModuleProgressDto> moduleProgressesDTO = moduleProgressService.filterModuleProgress(courseProgressId, moduleId, userId);
            return new ResponseEntity<>(moduleProgressesDTO, HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ModuleProgressDto> createModuleProgress(@RequestBody ModuleProgressDto moduleProgressDTO) {
        try {
            return new ResponseEntity<>(moduleProgressService.createModuleProgress(moduleProgressDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("{moduleProgressId}/update")
    public ResponseEntity<ModuleProgressDto> updateModuleProgress(@RequestBody ModuleProgressDto moduleProgressDTO, @PathVariable("moduleProgressId") int iD) {
        try {
            return new ResponseEntity<>(moduleProgressService.updateModuleProgressById(iD, moduleProgressDTO), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("{moduleProgressId}/delete")
    public ResponseEntity<ModuleProgressDto> deleteModuleProgress(@PathVariable int moduleProgressId) {
        try {
            return new ResponseEntity<>(moduleProgressService.deleteModuleProgressById(moduleProgressId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
