package Omar.HotelWebServer.service;

import Omar.HotelWebServer.dataAccess.model.classes.Booking;
import Omar.HotelWebServer.dataAccess.model.classes.Payment;
import Omar.HotelWebServer.dataAccess.model.classes.Room;
import Omar.HotelWebServer.dataAccess.model.classes.User;
import Omar.HotelWebServer.dataAccess.model.enums.BookingStatus;
import Omar.HotelWebServer.dataAccess.model.enums.PaymentMethod;
import Omar.HotelWebServer.dataAccess.model.enums.PaymentStatus;
import Omar.HotelWebServer.dataAccess.repository.BookingRepository;
import Omar.HotelWebServer.dataAccess.repository.RoomRepository;
import Omar.HotelWebServer.dataAccess.repository.UserRepository;
import Omar.HotelWebServer.utils.DTOs.BookingDTO;
import Omar.HotelWebServer.utils.DTOs.BookingDateRangeDTO;
import Omar.HotelWebServer.utils.DTOs.BookingHistoryDTO;
import Omar.HotelWebServer.utils.DTOs.CheckInDTO;
import Omar.HotelWebServer.utils.Mapper.BookingMapper;
import Omar.HotelWebServer.utils.exceptions.EmptyResultException;
import Omar.HotelWebServer.utils.exceptions.WrongInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public BookingDTO createBooking(BookingDTO bookingDTO) {
        // Validate room availability
        Integer roomId = bookingDTO.getRoomId();
        LocalDate startDate = bookingDTO.getStartDate();
        LocalDate endDate = bookingDTO.getEndDate();

        if (!isRoomAvailable(roomId, startDate, endDate)) {
            throw new WrongInputException("Room with id " + roomId + " is not available in the specified date range.");
        }

        // Map BookingDTO to Booking entity
        Booking booking = new Booking();
        BookingMapper.INSTANCE.updateBookingFromDTO(bookingDTO, booking);

        // Set status, dates, and createdAt
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setCreatedAt(LocalDate.now());

        // Retrieve User for the booking
        Integer userId = bookingDTO.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EmptyResultException("User not found with id: " + userId));
        booking.setUser(user);

        // Retrieve Room for the booking
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EmptyResultException("Room not found with id: " + roomId));
        booking.setRoom(room);

        // Calculate price per night and create Payment for the Booking
        Optional<Double> pricePerNightOptional = roomRepository.findPriceById(roomId);
        if (pricePerNightOptional.isEmpty()) {
            throw new EmptyResultException("Room not found with id: " + roomId);
        }
        double pricePerNight = pricePerNightOptional.get();
        long numberOfNights = ChronoUnit.DAYS.between(startDate, endDate);
        double totalAmount = pricePerNight * numberOfNights;

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(totalAmount);
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentMethod(PaymentMethod.UNKNOWN);
        payment.setPaymentStatus(PaymentStatus.PENDING);

        booking.setPayment(payment); // Associate payment with the booking

        // Save the Booking entity
        bookingRepository.save(booking);

        // Map Booking entity back to BookingDTO for response
        BookingDTO createdBookingDTO = BookingMapper.INSTANCE.toBookingDTO(booking);
        createdBookingDTO.setCreatedAt(booking.getCreatedAt());
        createdBookingDTO.setPricePerNight(pricePerNight);
        createdBookingDTO.setStatus(booking.getStatus());

        return createdBookingDTO;
    }

    private boolean isRoomAvailable(Integer roomId, LocalDate startDate, LocalDate endDate) {
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(roomId, startDate, endDate);
        return overlappingBookings.isEmpty();
    }

    public List<BookingDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return BookingMapper.INSTANCE.toBookingDTOList(bookings);
    }

    public BookingDTO getBookingById(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with id: " + bookingId));
        return BookingMapper.INSTANCE.toBookingDTO(booking);
    }

    public BookingHistoryDTO getBookingHistoryByUserId(Integer userId) {
        // Assuming you have a method to fetch User by userId from your UserRepository
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        List<Booking> bookings = bookingRepository.findByUserOrderByStartDateDesc(user);
        return BookingMapper.INSTANCE.toBookingHistoryDTO(bookings, user);
    }

    public BookingDateRangeDTO getBookingsWithinDateRange(LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = bookingRepository.findAllByStartDateBetween(startDate, endDate);
        return BookingMapper.INSTANCE.toBookingDateRangeDTO(bookings);
    }

    public List<BookingDTO> getRoomBookings(Integer roomId) {
        List<Booking> bookings = bookingRepository.findByRoomId(roomId);
        return BookingMapper.INSTANCE.toBookingDTOList(bookings);
    }

    public void updateBooking(BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(bookingDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with id: " + bookingDTO.getId()));
        BookingMapper.INSTANCE.updateBookingFromDTO(bookingDTO, booking);
        bookingRepository.save(booking);
    }

    public BookingDTO checkIn(CheckInDTO checkInDTO) {
        Booking booking = bookingRepository.findById(checkInDTO.getBookingId())
                .orElseThrow(() -> new EmptyResultException("Booking not found with id: " + checkInDTO.getBookingId()));
        booking.setStatus(BookingStatus.CHECKED_IN);
        booking.getPayment().setPaymentStatus(PaymentStatus.COMPLETED);
        bookingRepository.save(booking);
        return BookingMapper.INSTANCE.toBookingDTO(booking);
    }

    public String getBookingUserUserName(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EmptyResultException("Booking not found with id: " + bookingId));
        return booking.getUser().getUsername();
    }

    public Integer getBookingUserId(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EmptyResultException("Booking not found with id: " + bookingId));
        return booking.getUser().getId();
    }

    public String getBookingUserNameByBookingId(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EmptyResultException("Booking not found with id: " + bookingId));
        return booking.getUser().getUsername();
    }

}
