package me.kimyounghan.validation.controller;

import me.kimyounghan.validation.CommonMockMvcTest;
import me.kimyounghan.validation.dto.PaymentDto;
import me.kimyounghan.validation.valid.util.ValidBean;
import me.kimyounghan.validation.valid.util.ValidUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MultiValueMap;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class PaymentControllerTest extends CommonMockMvcTest {
    @Autowired
    ValidBean validBean;

    @Test
    void 유효성_검사_테스트() {
        // Given
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        PaymentDto.SelectListReq req = new PaymentDto.SelectListReq("20220529", null, "0");

        // When
        final Collection<ConstraintViolation<PaymentDto.SelectListReq>> constraintViolations = validator.validate(req);

        // Then
        assertThat(constraintViolations.size()).isEqualTo(1); // ConstraintViolation 에 실패에 대한 정보가 담긴다.
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("비어 있을 수 없습니다");
    }

    @Test
    void 리스트_조회_유효하지_않은_파라미터_null() throws Exception {
        // given
        PaymentDto.SelectListReq req = new PaymentDto.SelectListReq("20220529", null, "0");
        MultiValueMap<String, String> reqMap = convertToMultiValueMap(req);

        // when
        ResultActions resultActions = mockMvc.perform(get("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .params(reqMap))
            .andDo(print());

        // then
        assertThat(resultActions.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 업데이트_리스트_유효하지_않은_파라미터_list_elements() throws Exception {
        // given
        PaymentDto.UpdateListReq req = new PaymentDto.UpdateListReq(List.of("tid-1", "tid-123456789"), "0");

        // when
        ResultActions resultActions = mockMvc.perform(put("/payments/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andDo(print());

        // then
        assertThat(resultActions.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 업데이트_리스트_유효하지_않은_파라미터_이모지() throws Exception {
        // given
        PaymentDto.UpdateListReq req = new PaymentDto.UpdateListReq(List.of("tid-1", "tid-2"), "😀");

        // when
        ResultActions resultActions = mockMvc.perform(put("/payments/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andDo(print());

        // then
        assertThat(resultActions.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 엑셀_리스트_조회_유효하지_않은_파라미터_그룹() throws Exception {
        // given
        PaymentDto.SelectListReq req = new PaymentDto.SelectListReq("20220529", "20220530", null);
        MultiValueMap<String, String> reqMap = convertToMultiValueMap(req);

        // when
        ResultActions resultActions = mockMvc.perform(get("/payments/excel")
                .contentType(MediaType.APPLICATION_JSON)
                .params(reqMap))
            .andDo(print());

        // then
        assertThat(resultActions.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 리스트_조회_유효하지_않은_파라미터여도_그룹이_아니면_정상_응답() throws Exception {
        // given
        PaymentDto.SelectListReq req = new PaymentDto.SelectListReq("20220529", "20220530", null);
        MultiValueMap<String, String> reqMap = convertToMultiValueMap(req);

        // when
        ResultActions resultActions = mockMvc.perform(get("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .params(reqMap))
            .andDo(print());

        // then
        assertThat(resultActions.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 등록_유효하지_않은_파라미터_이미_존재하는_결제_내역() throws Exception {
        // given
        PaymentDto.InsertReq req = new PaymentDto.InsertReq("tid-1", "0", "20220529");

        // when
        ResultActions resultActions = mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andDo(print());

        // then
        assertThat(resultActions.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 국제화_테스트_영어() {
        // Given
        Locale.setDefault(Locale.US);

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        PaymentDto.UpdateListReq req = new PaymentDto.UpdateListReq(List.of("tid-1", "tid-2"), "😀");

        // When
        final Collection<ConstraintViolation<PaymentDto.UpdateListReq>> constraintViolations = validator.validate(req);

        // Then
        assertThat(constraintViolations.size()).isEqualTo(1); // ConstraintViolation 에 실패에 대한 정보가 담긴다.
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Emoji is not allowed. : 😀");
    }

    @Test
    void 국제화_테스트_한국어() {
        // Given
        Locale.setDefault(Locale.KOREA);

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        PaymentDto.UpdateListReq req = new PaymentDto.UpdateListReq(List.of("tid-1", "tid-2"), "😀");

        // When
        final Collection<ConstraintViolation<PaymentDto.UpdateListReq>> constraintViolations = validator.validate(req);

        // Then
        assertThat(constraintViolations.size()).isEqualTo(1); // ConstraintViolation 에 실패에 대한 정보가 담긴다.
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("이모지는 허용되지 않습니다. : 😀");
    }

    @Test
    void 메소드로_유효성_검사() {
        // Given
        PaymentDto.SelectListReq req = new PaymentDto.SelectListReq(null, "20220529", "0");

        // When, Then
        assertThrows(ConstraintViolationException.class, () -> {
           ValidUtils.validateSelectListReq(req);
        });
    }

    @Test
    void javax_hibernate_비교_javax() throws Exception {
        // given
        PaymentDto.TestReq req1 = new PaymentDto.TestReq(null, "tid-1");

        // when
        ResultActions resultActions = mockMvc.perform(post("/payments/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req1)))
            .andDo(print());

        // then
        assertThat(resultActions.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void javax_hibernate_비교_hibernate() throws Exception {
        // given
        PaymentDto.TestReq req2 = new PaymentDto.TestReq("tid-1", null);

        // when
        ResultActions resultActions = mockMvc.perform(post("/payments/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req2)))
            .andDo(print());

        // then
        assertThat(resultActions.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 컨트롤러_서비스_아닌_빈_유효성_검사() {
        assertThrows(ConstraintViolationException.class, () -> {
            validBean.isValidPayment(null);
        });
    }
}