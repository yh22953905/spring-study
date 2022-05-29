package me.kimyounghan.validation.service;

import me.kimyounghan.validation.dto.PaymentDto;
import me.kimyounghan.validation.valid.group.Excel;
import me.kimyounghan.validation.vo.PaymentVO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface PaymentService {
    List<PaymentVO> selectList(PaymentDto.SelectListReq req);

    void updateList(PaymentDto.UpdateListReq req);

    @Validated(Excel.class)
    List<PaymentVO> selectListForExcel(@Valid PaymentDto.SelectListReq req);

    void insert(PaymentDto.InsertReq req);
}
