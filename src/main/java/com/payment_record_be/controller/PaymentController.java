package com.payment_record_be.controller;

import com.payment_record_be.dto.request.PaymentFilterRequest;
import com.payment_record_be.dto.request.PaymentRequest;
import com.payment_record_be.dto.response.PaymentResponse;
import com.payment_record_be.response.ApiResponse;
import com.payment_record_be.response.PageResponse;
import com.payment_record_be.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payment History", description = "APIs to store and query UPI payment transactions")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "Save a payment record",
            description = "Stores a UPI payment transaction received from the mobile application. "
                    + "All fields are optional — complete, partial, or minimal payloads are accepted. "
                    + "Any field omitted from the request is stored as NULL. Only data-type correctness "
                    + "is validated (e.g. amount must be numeric); no field is required.")
    public ResponseEntity<ApiResponse<PaymentResponse>> savePayment(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.savePayment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Payment history stored successfully", response));
    }

    @GetMapping
    @Operation(summary = "Get payment history",
            description = "Returns a paginated, filterable, sortable list of payment records, latest first")
    public ResponseEntity<ApiResponse<PageResponse<PaymentResponse>>> getPayments(
            @Parameter(description = "Search by payment id") @RequestParam(required = false) String paymentId,
            @Parameter(description = "Search by mobile number") @RequestParam(required = false) String mobileNumber,
            @Parameter(description = "Filter by event id") @RequestParam(required = false) String eventId,
            @Parameter(description = "Filter by payment status") @RequestParam(required = false) String paymentStatus,
            @Parameter(description = "Filter by payment method") @RequestParam(required = false) String paymentMethod,
            @Parameter(description = "From date (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @Parameter(description = "To date (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PaymentFilterRequest filter = PaymentFilterRequest.builder()
                .paymentId(paymentId)
                .mobileNumber(mobileNumber)
                .eventId(eventId)
                .paymentStatus(paymentStatus)
                .paymentMethod(paymentMethod)
                .fromDate(fromDate)
                .toDate(toDate)
                .build();

        Page<PaymentResponse> page = paymentService.getPayments(filter, pageable);
        return ResponseEntity.ok(ApiResponse.success("Payment history fetched successfully", PageResponse.from(page)));
    }
}
