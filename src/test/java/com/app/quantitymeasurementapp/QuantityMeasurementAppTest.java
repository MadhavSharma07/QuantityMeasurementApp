package com.app.quantitymeasurementapp;

import com.app.quantitymeasurementapp.controller.QuantityMeasurementController;
import com.app.quantitymeasurementapp.model.QuantityDTO;
import com.app.quantitymeasurementapp.model.QuantityInputDTO;
import com.app.quantitymeasurementapp.model.QuantityMeasurementDTO;
import com.app.quantitymeasurementapp.service.IQuantityMeasurementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuantityMeasurementController.class)
@WithMockUser
class QuantityMeasurementAppTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IQuantityMeasurementService service;

    // -------- helper --------

    private QuantityInputDTO input(double v1, String u1, String t1,
                                   double v2, String u2, String t2) {
        QuantityDTO thisQty = new QuantityDTO();
        thisQty.setValue(v1);
        thisQty.setUnit(u1);
        thisQty.setMeasurementType(t1);

        QuantityDTO thatQty = new QuantityDTO();
        thatQty.setValue(v2);
        thatQty.setUnit(u2);
        thatQty.setMeasurementType(t2);

        QuantityInputDTO inputDTO = new QuantityInputDTO();
        inputDTO.setThisQuantityDTO(thisQty);
        inputDTO.setThatQuantityDTO(thatQty);
        return inputDTO;
    }

    private String json(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    // ==================== compare ====================

    @Test
    void testCompare_FeetEqualsInches() throws Exception {
        QuantityMeasurementDTO result = new QuantityMeasurementDTO();
        result.setResultString("true");
        Mockito.when(service.compare(any(), any())).thenReturn(result);

        mockMvc.perform(post("/app/compare")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(input(1.0, "FEET", "LengthUnit", 12.0, "INCHES", "LengthUnit"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultString").value("true"));
    }

    @Test
    void testCompare_0Celsius_Equals_32Fahrenheit() throws Exception {
        QuantityMeasurementDTO result = new QuantityMeasurementDTO();
        result.setResultString("true");
        Mockito.when(service.compare(any(), any())).thenReturn(result);

        mockMvc.perform(post("/app/compare")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(input(0.0, "CELSIUS", "TemperatureUnit", 32.0, "FAHRENHEIT", "TemperatureUnit"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultString").value("true"));
    }

    // ==================== convert ====================

    @Test
    void testConvert_FeetToInches() throws Exception {
        QuantityMeasurementDTO result = new QuantityMeasurementDTO();
        result.setResultValue(12.0);
        Mockito.when(service.convert(any(), any())).thenReturn(result);

        mockMvc.perform(post("/app/convert")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(input(1.0, "FEET", "LengthUnit", 0.0, "INCHES", "LengthUnit"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(12.0));
    }

    // ==================== add ====================

    @Test
    void testAdd_FeetPlusInches() throws Exception {
        QuantityMeasurementDTO result = new QuantityMeasurementDTO();
        result.setResultValue(2.0);
        result.setResultUnit("FEET");
        Mockito.when(service.add(any(), any())).thenReturn(result);

        mockMvc.perform(post("/app/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(input(1.0, "FEET", "LengthUnit", 12.0, "INCHES", "LengthUnit"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(2.0))
                .andExpect(jsonPath("$.resultUnit").value("FEET"));
    }

    // ==================== subtract ====================

    @Test
    void testSubtract_FeetMinusInches() throws Exception {
        QuantityMeasurementDTO result = new QuantityMeasurementDTO();
        result.setResultValue(9.5);
        Mockito.when(service.subtract(any(), any())).thenReturn(result);

        mockMvc.perform(post("/app/subtract")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(input(10.0, "FEET", "LengthUnit", 6.0, "INCHES", "LengthUnit"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(9.5));
    }

    // ==================== divide ====================

    @Test
    void testDivide_InchesOverFeet() throws Exception {
        QuantityMeasurementDTO result = new QuantityMeasurementDTO();
        result.setResultValue(1.0);
        Mockito.when(service.divide(any(), any())).thenReturn(result);

        mockMvc.perform(post("/app/divide")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(input(24.0, "INCHES", "LengthUnit", 2.0, "FEET", "LengthUnit"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(1.0));
    }

    // ==================== GET endpoints ====================

    @Test
    void testGetHistoryByOperation() throws Exception {
        Mockito.when(service.getHistoryByOperation("COMPARE")).thenReturn(List.of());
        mockMvc.perform(get("/app/history/operation/COMPARE"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetHistoryByType() throws Exception {
        Mockito.when(service.getHistoryByType("LengthUnit")).thenReturn(List.of());
        mockMvc.perform(get("/app/history/type/LengthUnit"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetErrorHistory() throws Exception {
        Mockito.when(service.getErrorHistory()).thenReturn(List.of());
        mockMvc.perform(get("/app/history/errored"))
                .andExpect(status().isOk());
    }

    @Test
    void testCountByOperation() throws Exception {
        Mockito.when(service.countByOperation("compare")).thenReturn(2L);
        mockMvc.perform(get("/app/count/COMPARE"))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    // ==================== validation ====================

    @Test
    void testInvalidUnit_Returns400() throws Exception {
        mockMvc.perform(post("/app/compare")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(input(1.0, "FOOT", "LengthUnit", 12.0, "INCHE", "LengthUnit"))))
                .andExpect(status().isBadRequest());
    }
}