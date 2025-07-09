package com.ameen.bookingTicket.service;

import com.ameen.bookingTicket.dto.BookingDto;
import com.ameen.bookingTicket.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AllService {

    SuccessResponse<Object> createBooking(BookingDto bookingDto);

    ResponseEntity<byte[]> downloadUsersExcel();

    ResponseEntity<byte[]> downloadFlightsExcel() ;

    ResponseEntity<byte[]> downloadBookingsExcel();

    void saveUploadBookings(MultipartFile file);

}
