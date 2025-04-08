//package com.codelab.rest.webservices.trainingtool.service;
//
//import com.codelab.rest.webservices.trainingtool.exception.CurrentCohortNotFoundException;
//import com.codelab.rest.webservices.trainingtool.exception.ResourceNotFoundExceptionString;
//import com.codelab.rest.webservices.trainingtool.model.Cohort;
//import com.codelab.rest.webservices.trainingtool.payload.CohortDto;
//import com.codelab.rest.webservices.trainingtool.repository.CohortRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@ExtendWith(MockitoExtension.class)
//public class CohortServiceTest {
//
//    @InjectMocks
//    private CohortService cohortService;
//
//    @Mock
//    private CohortRepository cohortRepository;
//
//    @Test
//    void getCohortByIdSuccess() {
//        Cohort cohort = new Cohort(1, "Test Cohort", new Date(), new Date(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//        given(cohortRepository.findById(1)).willReturn(Optional.of(cohort));
//        CohortDto result = cohortService.getCohortById(1);
//        assertThat(result).isNotNull();
//        assertThat(result.getCohortID()).isEqualTo(cohort.getCohortID());
//    }
//
//    @Test
//    void getCohortByIdFailure() {
//        given(cohortRepository.findById(1)).willReturn(Optional.empty());
//        assertThrows(ResourceNotFoundExceptionString.class, () -> cohortService.getCohortById(1));
//    }
//
//    @Test
//    void getCurrentCohortSuccess() {
//        Cohort cohort = new Cohort(1, "Test Cohort", new Date(), new Date(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//        given(cohortRepository.findCurrentCohort(any(Date.class))).willReturn(Optional.of(cohort));
//        CohortDto result = cohortService.getCurrentCohort();
//        assertThat(result).isNotNull();
//    }
//
//    @Test
//    void getCurrentCohortFailure() {
//        given(cohortRepository.findCurrentCohort(any(Date.class))).willReturn(Optional.empty());
//        assertThrows(CurrentCohortNotFoundException.class, cohortService::getCurrentCohort);
//    }
//
//    @Test
//    void createCohortTest() {
//        CohortDto cohortDTO = new CohortDto(1, "Test Cohort", new Date(), new Date(), new ArrayList<>());
//        Cohort cohort = new Cohort();
//        given(cohortRepository.save(any(Cohort.class))).willReturn(cohort);
//        CohortDto result = cohortService.createCohort(cohortDTO);
//        assertThat(result).isNotNull();
//    }
//
//    @Test
//    void getAllCohortsSuccess() {
//        List<Cohort> cohorts = new ArrayList<>();
//        cohorts.add(new Cohort(1, "Test Cohort", new Date(), new Date(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
//        given(cohortRepository.findAll()).willReturn(cohorts);
//        List<CohortDto> result = cohortService.getAllCohorts();
//        assertThat(result).isNotEmpty();
//    }
//
//    @Test
//    void updateCohortByIdSuccess() {
//        Cohort cohort = new Cohort(1, "Test Cohort", new Date(), new Date(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//        given(cohortRepository.findById(1)).willReturn(Optional.of(cohort));
//        CohortDto cohortDTO = new CohortDto(1, "Updated Cohort", new Date(), new Date(), new ArrayList<>());
//        given(cohortRepository.save(any(Cohort.class))).willReturn(cohort);
//        CohortDto result = cohortService.updateCohortById(1, cohortDTO);
//        assertThat(result).isNotNull();
//    }
//
//    @Test
//    void updateCohortByIdFailure() {
//        given(cohortRepository.findById(1)).willReturn(Optional.empty());
//        CohortDto cohortDTO = new CohortDto();
//        assertThrows(ResourceNotFoundExceptionString.class, () -> cohortService.updateCohortById(1, cohortDTO));
//    }
//
//    @Test
//    void deleteCohortByIdSuccess() {
//        Cohort cohort = new Cohort(1, "Test Cohort", new Date(), new Date(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//        given(cohortRepository.findById(1)).willReturn(Optional.of(cohort));
//        CohortDto result = cohortService.deleteCohortById(1);
//        assertThat(result).isNotNull();
//    }
//
//    @Test
//    void deleteCohortByIdFailure() {
//        given(cohortRepository.findById(1)).willReturn(Optional.empty());
//        assertThrows(ResourceNotFoundExceptionString.class, () -> cohortService.deleteCohortById(1));
//    }
//}
