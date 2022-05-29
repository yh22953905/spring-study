package me.kimyounghan.validation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimyounghan.validation.dto.PaymentDto;
import me.kimyounghan.validation.service.PaymentService;
import me.kimyounghan.validation.vo.PaymentVO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<Object> selectList(@Valid @ModelAttribute PaymentDto.SelectListReq req) {
        List<PaymentVO> payments = paymentService.selectList(req);

        return ResponseEntity.ok(payments);
    }

    @GetMapping("/excel")
    public ResponseEntity<Object> selectListForExcel(@ModelAttribute PaymentDto.SelectListReq req) {
        List<PaymentVO> payments = paymentService.selectListForExcel(req);

        return ResponseEntity.ok(payments);
    }

    @PostMapping
    public ResponseEntity<Object> insert(@Valid @RequestBody PaymentDto.InsertReq req) {
        paymentService.insert(req);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/list")
    public ResponseEntity<Object> updateList(@Valid @RequestBody PaymentDto.UpdateListReq req) {
        paymentService.updateList(req);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/notValidated")
    public ResponseEntity<Object> selectListNotValidated(@ModelAttribute PaymentDto.SelectListReq req) throws Exception {
        if (req.getStartDate() == null) {
            throw new Exception("startDate is not null");
        }

        if (req.getEndDate() == null) {
            throw new Exception("endDate is not null");
        }

        List<PaymentVO> payments = paymentService.selectList(req);

        return ResponseEntity.ok(payments);
    }

    @PostMapping("/test")
    public ResponseEntity<Object> test(@Valid @RequestBody PaymentDto.TestReq req) {
        log.debug(req.toString());

        return ResponseEntity.ok().build();
    }
}
