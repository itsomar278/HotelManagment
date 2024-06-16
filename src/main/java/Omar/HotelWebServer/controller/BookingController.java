package Omar.HotelWebServer.controller;

import Omar.HotelWebServer.dataAccess.repository.UserRepository;
import Omar.HotelWebServer.security.TokenProvider;
import Omar.HotelWebServer.service.BookingService;
import Omar.HotelWebServer.service.UserService;
import Omar.HotelWebServer.utils.DTOs.BookingDTO;
import Omar.HotelWebServer.utils.DTOs.BookingDateRangeDTO;
import Omar.HotelWebServer.utils.DTOs.BookingHistoryDTO;
import Omar.HotelWebServer.utils.DTOs.CheckInDTO;
import Omar.HotelWebServer.utils.exceptions.NotAuthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final TokenProvider tokenProvider;
    private final UserService userService;

    @Autowired
    public BookingController(BookingService bookingService, TokenProvider tokenProvider , UserService userService) {
        this.bookingService = bookingService;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }


    @PostMapping("/create")
    public BookingDTO createBooking(HttpServletRequest request, @RequestBody BookingDTO bookingDTO) {
        validateUserPermissionById(request, bookingDTO.getUserId());
        return bookingService.createBooking(bookingDTO);
    }

    @PostMapping("/check-in")
    public ResponseEntity<String> checkIn(HttpServletRequest request, @RequestBody CheckInDTO checkInDTO) {
        validateUserPermissionByBookingId(request, checkInDTO.getBookingId());
        bookingService.checkIn(checkInDTO);
        return ResponseEntity.ok("Checked in successfully");
    }

    @GetMapping("/all")
    public List<BookingDTO> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{bookingId}")
    public BookingDTO getBookingById(@PathVariable Integer bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    @GetMapping("/history/{userId}")
    public BookingHistoryDTO getBookingHistoryByUserId(HttpServletRequest request, @PathVariable Integer userId) {
        validateUserPermissionById(request, userId);
        return bookingService.getBookingHistoryByUserId(userId);
    }

    @GetMapping("/date-range")
    public BookingDateRangeDTO getBookingsWithinDateRange(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return bookingService.getBookingsWithinDateRange(start, end);
    }

    @GetMapping("/room/{roomId}")
    public List<BookingDTO> getRoomBookings(@PathVariable Integer roomId) {
        return bookingService.getRoomBookings(roomId);
    }

    private void validateUserPermissionByBookingId(HttpServletRequest request, Integer bookingId) {
        String username = tokenProvider.getUsernameFromToken(extractToken(request));
        if (!username.equals(bookingService.getBookingUserNameByBookingId(bookingId))) {
            throw new NotAuthorizedException("User does not have permission to perform this action");
        }
    }

    private void validateUserPermissionById(HttpServletRequest request, Integer userId) {
        String username = tokenProvider.getUsernameFromToken(extractToken(request));
        if (!username.equals(userService.getUserNameById(userId))) {
            throw new NotAuthorizedException("User does not have permission to perform this action");
        }
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(14);
        }
        throw new IllegalArgumentException("Invalid authorization header format");
    }
}
