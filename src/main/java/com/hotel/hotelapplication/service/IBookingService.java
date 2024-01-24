package com.hotel.hotelapplication.service;

import com.hotel.hotelapplication.model.BookedRoom;

import java.util.List;

public interface IBookingService {

    List<BookedRoom> getBookingsByUserEmail(String email);

    void cancelBooking(Long bookingId);

    List<BookedRoom> getAllBookingsByRoomId(Long roomId);

    String saveBooking(Long roomId, BookedRoom bookedRequest);

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    List<BookedRoom> getAllBookings();
}
