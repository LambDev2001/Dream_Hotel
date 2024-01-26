package com.hotelproject.anireamlhotel.service;


import com.hotelproject.anireamlhotel.exception.InvalidBookingRequestException;
import com.hotelproject.anireamlhotel.model.BookedRoom;
import com.hotelproject.anireamlhotel.model.Room;
import com.hotelproject.anireamlhotel.repository.BookingRepository;
import com.hotelproject.anireamlhotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository;
    private final IRoomService roomService;


    public List<BookedRoom> getAllBookingByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    @Override
    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public String saveBooking(long roomId, BookedRoom bookingRequest) {
        if (bookingRequest.getCheckInDate().isBefore(bookingRequest.getCheckOutDate())) {
            throw new InvalidBookingRequestException("Check-in date must come before check-out date");
        }
        Room room = roomService.getRoomById(roomId).get();
        List<BookedRoom> exitingBookings = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest, exitingBookings);
        if (roomIsAvailable) {
            room.addBooking(bookingRequest);
            bookingRepository.save(bookingRequest);
        } else {
            throw new InvalidBookingRequestException("This has been booked for the selected dates");
        }

        return bookingRequest.getBookingConfirmationCode();
    }

    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> exitingBookings) {
        return exitingBookings.stream()
                .noneMatch(exitingBooking -> bookingRequest.getCheckInDate().equals(exitingBooking.getCheckInDate())
                        || bookingRequest.getCheckOutDate().isBefore(exitingBooking.getCheckOutDate())
                        || (bookingRequest.getCheckInDate().isAfter(exitingBooking.getCheckInDate())
                        && bookingRequest.getCheckInDate().isBefore(exitingBooking.getCheckOutDate()))
                        || (bookingRequest.getCheckInDate().isBefore(exitingBooking.getCheckInDate())

                        && bookingRequest.getCheckOutDate().equals(exitingBooking.getCheckOutDate()))
                        || (bookingRequest.getCheckInDate().isBefore(exitingBooking.getCheckInDate())


                        && bookingRequest.getCheckOutDate().isAfter(exitingBooking.getCheckOutDate()))

                        || (bookingRequest.getCheckInDate().equals(exitingBooking.getCheckOutDate())
                        && bookingRequest.getCheckOutDate().equals(exitingBooking.getCheckInDate()))

                        || (bookingRequest.getCheckInDate().equals(exitingBooking.getCheckOutDate())
                        && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))


                );
    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
        return bookingRepository.findByBookingConfirmationCode(confirmationCode);
    }

    @Override
    public List<BookedRoom> getAllBooking() {
        return bookingRepository.findAll();
    }
}
