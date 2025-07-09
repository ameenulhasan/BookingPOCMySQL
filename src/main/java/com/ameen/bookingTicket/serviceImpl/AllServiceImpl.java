package com.ameen.bookingTicket.serviceImpl;

import com.ameen.bookingTicket.dto.BookingDto;
import com.ameen.bookingTicket.model.Booking;
import com.ameen.bookingTicket.model.Flight;
import com.ameen.bookingTicket.model.User;
import com.ameen.bookingTicket.repository.BookingRepository;
import com.ameen.bookingTicket.repository.FlightRepository;
import com.ameen.bookingTicket.repository.UserRepository;
import com.ameen.bookingTicket.response.SuccessResponse;
import com.ameen.bookingTicket.service.AllService;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AllServiceImpl implements AllService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    public AllServiceImpl(BookingRepository bookingRepository, UserRepository userRepository, FlightRepository flightRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    public SuccessResponse<Object> createBooking(BookingDto bookingDto) {
        SuccessResponse<Object> successResponse = new SuccessResponse<>();
        Optional<User> userOptional = userRepository.findById(bookingDto.getUserId());
        Optional<Flight> flightOptional = flightRepository.findById(bookingDto.getFlightId());
        if (userOptional.isPresent() && flightOptional.isPresent()) {
            Booking booking = new Booking();
            booking.setBookingNo(bookingDto.getBookingNo());
            booking.setSendPlace(bookingDto.getSendPlace());
            booking.setReceivePlace(bookingDto.getReceivePlace());
            booking.setUser(userOptional.get());
            booking.setFlight(flightOptional.get());
            Booking savedBooking = bookingRepository.save(booking);
            successResponse.setMessage("Booking created successfully");
            successResponse.setSuccess(true);
            successResponse.setData(savedBooking);
        } else {
            successResponse.setMessage("User or Flight not found");
            successResponse.setSuccess(false);
        }
        return successResponse;
    }

//    @Override
//    public ResponseEntity<byte[]> downloadUsersExcel() {
//        try (Workbook workbook = new XSSFWorkbook()) {
//            List<User> users = userRepository.findAll();
//            Sheet sheet = workbook.createSheet("Users");
//            Row headerRow = sheet.createRow(0);
//            String[] columns = {"ID", "User Name", "Phone No", "Email", "Address"};
//            for (int i = 0; i < columns.length; i++) {
//                headerRow.createCell(i).setCellValue(columns[i]);
//            }
//            int rowNum = 1;
//            for (User user : users) {
//                Row row = sheet.createRow(rowNum++);
//                row.createCell(0).setCellValue(user.getId());
//                row.createCell(1).setCellValue(user.getUserName());
//                row.createCell(2).setCellValue(user.getPhoneNo());
//                row.createCell(3).setCellValue(user.getEmail());
//                row.createCell(4).setCellValue(user.getAddress());
//            }
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            workbook.write(outputStream);
//            byte[] excelBytes = outputStream.toByteArray();
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.xlsx")
//                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                    .body(excelBytes);
//        } catch (IOException e) {
//            throw new RuntimeException("Error generating Excel file", e);
//        }
//    }

    @Override
    public ResponseEntity<byte[]> downloadFlightsExcel() {
        try {
            List<Flight> flights = flightRepository.findAll();
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Flights");
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Flight No", "Send Place", "Receive Place"};
            for (int i = 0; i < columns.length; i++) {
                headerRow.createCell(i).setCellValue(columns[i]);
            }
            int rowNum = 1;
            for (Flight flight : flights) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(flight.getId());
                row.createCell(1).setCellValue(flight.getFlightNo());
                row.createCell(2).setCellValue(flight.getSendPlace());
                row.createCell(3).setCellValue(flight.getReceivePlace());
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();
            byte[] excelBytes = outputStream.toByteArray();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=flights.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(excelBytes);
        } catch (IOException e) {
            throw new RuntimeException("Error generating Excel file", e);
        }
    }
    @Override
    public ResponseEntity<byte[]> downloadUsersExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            List<User> users = userRepository.findAll();
            Sheet sheet = workbook.createSheet("Users");
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "User Name", "Phone No", "Email", "Address"};
            for (int i = 0; i < columns.length; i++) {
                headerRow.createCell(i).setCellValue(columns[i]);
            }
            int rowNum = 1;
            for (User user : users) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(user.getId());
                row.createCell(1).setCellValue(user.getUserName());
                row.createCell(2).setCellValue(user.getPhoneNo());
                row.createCell(3).setCellValue(user.getEmail());
                row.createCell(4).setCellValue(user.getAddress());
            }
            Name namedRange = workbook.createName();
            namedRange.setNameName("UserNames");
            String reference = "Users!$B$2:$B$" + rowNum;
            namedRange.setRefersToFormula(reference);
            DataValidationHelper validationHelper = sheet.getDataValidationHelper();
            DataValidationConstraint constraint = validationHelper.createFormulaListConstraint("UserNames");
            CellRangeAddressList addressList = new CellRangeAddressList(1, rowNum - 1, 1, 1);
            DataValidation validation = validationHelper.createValidation(constraint, addressList);
            sheet.addValidationData(validation);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] excelBytes = outputStream.toByteArray();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(excelBytes);
        } catch (IOException e) {
            throw new RuntimeException("Error generating Excel file", e);
        }
    }
    @Override
    public ResponseEntity<byte[]> downloadBookingsExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            List<Booking> bookings = bookingRepository.findAll();
            List<User> users = userRepository.findAll();
            List<Flight> flights = flightRepository.findAll();
            Sheet bookingSheet = workbook.createSheet("Bookings");
            Row headerRow = bookingSheet.createRow(0);
            String[] columns = {"ID", "Booking No", "Send Place", "Receive Place", "User ID", "User Name",
                    "Email", "Phone No", "Address", "Flight ID", "Flight No", "Send Place", "Receive Place"};
            for (int i = 0; i < columns.length; i++) {
                headerRow.createCell(i).setCellValue(columns[i]);
            }
            int rowNum = 1;
            for (Booking booking : bookings) {
                Row row = bookingSheet.createRow(rowNum++);
                row.createCell(0).setCellValue(booking.getId());
                row.createCell(1).setCellValue(booking.getBookingNo());
                row.createCell(2).setCellValue(booking.getSendPlace());
                row.createCell(3).setCellValue(booking.getReceivePlace());
                if (booking.getUser() != null) {
                    row.createCell(4).setCellValue(booking.getUser().getId());
                    row.createCell(5).setCellValue(booking.getUser().getUserName());
                    row.createCell(6).setCellValue(booking.getUser().getEmail());
                    row.createCell(7).setCellValue(booking.getUser().getPhoneNo());
                    row.createCell(8).setCellValue(booking.getUser().getAddress());
                }
                if (booking.getFlight() != null) {
                    row.createCell(9).setCellValue(booking.getFlight().getId());
                    row.createCell(10).setCellValue(booking.getFlight().getFlightNo());
                    row.createCell(11).setCellValue(booking.getFlight().getSendPlace());
                    row.createCell(12).setCellValue(booking.getFlight().getReceivePlace());
                }
            }
            Sheet userSheet = workbook.createSheet("Users");
            for (int i = 0; i < users.size(); i++) {
                Row row = userSheet.createRow(i);
                row.createCell(0).setCellValue(users.get(i).getUserName());
            }
            Sheet flightSheet = workbook.createSheet("Flights");
            for (int i = 0; i < flights.size(); i++) {
                Row row = flightSheet.createRow(i);
                row.createCell(0).setCellValue(flights.get(i).getFlightNo());
            }
            DataValidationHelper validationHelper = bookingSheet.getDataValidationHelper();
            DataValidationConstraint userConstraint = validationHelper.createFormulaListConstraint("Users!A1:A" + users.size());
            CellRangeAddressList userList = new CellRangeAddressList(1, rowNum - 1, 5, 5);
            DataValidation userValidation = validationHelper.createValidation(userConstraint, userList);
            bookingSheet.addValidationData(userValidation);
            DataValidationConstraint flightConstraint = validationHelper.createFormulaListConstraint("Flights!A1:A" + flights.size());
            CellRangeAddressList flightList = new CellRangeAddressList(1, rowNum - 1, 10, 10);
            DataValidation flightValidation = validationHelper.createValidation(flightConstraint, flightList);
            bookingSheet.addValidationData(flightValidation);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] excelBytes = outputStream.toByteArray();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bookings.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(excelBytes);
        } catch (IOException e) {
            throw new RuntimeException("Error generating Excel file", e);
        }
    }

    @Override
    public void saveUploadBookings(MultipartFile file) {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            List<Booking> bookingHistories = new ArrayList<>();
            while (rows.hasNext()) {
                Row row = rows.next();
                Booking booking = new Booking();
                booking.setBookingNo(row.getCell(1).getStringCellValue());
                booking.setSendPlace(row.getCell(2).getStringCellValue());
                booking.setReceivePlace(row.getCell(3).getStringCellValue());
                if (row.getCell(4) != null && row.getCell(4).getCellType() == CellType.NUMERIC) {
                    int userId = (int) row.getCell(4).getNumericCellValue();
                    User user = userRepository.findById(Long.valueOf(userId)).orElse(null);
                    booking.setUser(user);
                }
                if (row.getCell(5) != null && row.getCell(5).getCellType() == CellType.NUMERIC) {
                    int flightId = (int) row.getCell(5).getNumericCellValue();
                    Flight flight = flightRepository.findById(Long.valueOf(flightId)).orElse(null);
                    booking.setFlight(flight);
                }
                bookingHistories.add(booking);
            }
            bookingRepository.saveAll(bookingHistories);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
