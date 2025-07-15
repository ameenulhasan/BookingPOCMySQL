package com.ameen.bookingTicket.controller;

import com.ameen.bookingTicket.dto.BookingDto;
import com.ameen.bookingTicket.response.SuccessResponse;
import com.ameen.bookingTicket.service.AllService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/booking")
public class AllController {

    private  final AllService allService;

    public AllController(AllService allService) {
        this.allService = allService;
    }

    @PostMapping("/create")
    public SuccessResponse<Object> create(@RequestBody BookingDto bookingDto){
        return allService.createBooking(bookingDto);
    }

    @GetMapping("/downloadUsers")
    public ResponseEntity<byte[]> downloadUsersExcel() {
        return allService.downloadUsersExcel();
    }

    @GetMapping("/downloadFlights")
    public ResponseEntity<byte[]> downloadFlightsExcel() {
        return allService.downloadFlightsExcel();
    }

    @GetMapping("/downloadBooking")
    public ResponseEntity<byte[]> downloadBookingExcel() {
        return allService.downloadBookingsExcel();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadBookingsToHistory(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a valid Excel file!");
        }
        try {
            allService.saveUploadBookings(file);
            return ResponseEntity.ok("File uploaded and stored in booking history!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
        }
    }

}
