package com.example.Intern.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Hex;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api")
public class PaymentController {

    private static final String vnp_TmnCode = "FKSNRXXM";
    private static final String vnp_HashSecret = "KLBP0A38P4E2ZY5U1JQ6FP5X0ECHTWD7";
    private static final String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private static final String vnp_Returnurl = "http://localhost:8080/api/return";

    @GetMapping("/payment")
    public ResponseEntity<?> createPayment(HttpServletRequest request) {
        String orderId = UUID.randomUUID().toString();
        String amount = "100000"; // 100,000 VND
        String bankCode = "NCB";

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(Integer.parseInt(amount) * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", orderId);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang " + orderId);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", request.getRemoteAddr());
        vnp_Params.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        // Sort and build query string
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String value = vnp_Params.get(fieldName);
            if (value != null && !value.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
                query.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
            }
        }

        // Xóa dấu & cuối cùng
        hashData.deleteCharAt(hashData.length() - 1);
        query.deleteCharAt(query.length() - 1);

        // Tạo chữ ký
        String vnp_SecureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
        String paymentUrl = vnp_Url + "?" + query + "&vnp_SecureHash=" + vnp_SecureHash;

        return ResponseEntity.ok(paymentUrl);
    }

    @GetMapping("/return")
    public ResponseEntity<String> paymentReturn(@RequestParam Map<String, String> allParams) {
        String secureHash = allParams.remove("vnp_SecureHash");
        List<String> fieldNames = new ArrayList<>(allParams.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        for (String field : fieldNames) {
            hashData.append(field).append('=').append(allParams.get(field)).append('&');
        }
        hashData.deleteCharAt(hashData.length() - 1);

        String myHash = hmacSHA512(vnp_HashSecret, hashData.toString());

        if (myHash.equalsIgnoreCase(secureHash)) {
            return ResponseEntity.ok("Thanh toán thành công!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sai chữ ký!");
        }
    }

    public static String hmacSHA512(String key, String data) {
        try {
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac512.init(secretKeySpec);
            byte[] bytes = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(bytes);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi ký HMAC SHA512", e);
        }
    }
}
