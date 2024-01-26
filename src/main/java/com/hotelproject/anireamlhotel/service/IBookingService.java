package com.hotelproject.anireamlhotel.service;

import com.hotelproject.anireamlhotel.model.BookedRoom;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IBookingService {

    void cancelBooking(Long bookingId);

    String saveBooking(long roomId, BookedRoom bookingRequest);

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    List<BookedRoom> getAllBooking();
}
