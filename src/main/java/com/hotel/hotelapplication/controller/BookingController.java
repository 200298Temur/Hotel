package com.hotel.hotelapplication.controller;

import com.hotel.hotelapplication.exception.InvalidBookingRequestException;
import com.hotel.hotelapplication.exception.ResourceNotFoundException;
import com.hotel.hotelapplication.model.BookedRoom;
import com.hotel.hotelapplication.model.Room;
import com.hotel.hotelapplication.response.BookingResponse;
import com.hotel.hotelapplication.response.RoomResponse;
import com.hotel.hotelapplication.service.IBookingService;
import com.hotel.hotelapplication.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final IBookingService bookingService;
    private final IRoomService roomService;

    @GetMapping("/all-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        List<BookedRoom> bookings=bookingService.getAllBookings();
        List<BookingResponse> bookingResponses=new ArrayList<>();
        for(BookedRoom booking:bookings){
            BookingResponse bookingResponse=getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingConfirmationCode(@PathVariable String confirmationCode){
        try {
            BookedRoom booking=bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingResponse bookingResponse=getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/room/{roomId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable Long roomId,
                                         @RequestBody BookedRoom bookedRequest){
        try {
            String confirmationCode=bookingService.saveBooking(roomId,bookedRequest);
            return ResponseEntity.ok("Room booked successfully, Your booking confirmation code is :"+confirmationCode);
        }catch (InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId){
        bookingService.cancelBooking(bookingId);
    }

    private BookingResponse getBookingResponse(BookedRoom booking) {
        Room theRoom = roomService.getRoomById(booking.getRoom().getId()).get();
        RoomResponse    room=new RoomResponse(
                theRoom.getId(),
                theRoom.getRoomType(),
                theRoom.getRoomPrice());
        return new BookingResponse(
                booking.getBookingId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getGuestFullName()
        );
    }

}
