package com.hotel.hotelapplication.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private Long id;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private String guestName;

    private String guestEmail;

    private int numOfAdults;

    private int numOfChildren;

    private int totalNumOfGuests;

    private String bookingConfirmationCode;

    private RoomResponse room;

    public BookingResponse(Long id, LocalDateTime checkInDate, LocalDateTime checkOutDate,
                           String bookingConfirmationCode) {
        this.id = id;
        this.checkInDate = LocalDate.from(checkInDate);
        this.checkOutDate = LocalDate.from(checkOutDate);
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}