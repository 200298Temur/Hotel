package com.hotel.hotelapplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookedRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    @Column(name = "check_In")
    private LocalDateTime checkInDate;
    @Column(name = "check_Out")
    private LocalDateTime checkOutDate;
    @Column(name = "guest_FullName")
    private String guestFullName;
    @Column(name = "guest_Email")
    private String guestEmail;
    @Column(name = "children")
    private int NuOfChildren;
    @Column(name = "adults")
    private int NuOfAdults;
    @Column(name = "total_guest")
    private int totalNumberOfGuest;
    @Column(name = "confirmation_Code")
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
    public void calculateTotalNumberOfGuest(){
        this.totalNumberOfGuest=this.NuOfAdults+NuOfChildren;
    }

    public void setNuOfChildren(int nuOfChildren) {
        NuOfChildren = nuOfChildren;
        calculateTotalNumberOfGuest();

    }
    public void setNuOfAdults(int nuOfAdults) {
        NuOfAdults = nuOfAdults;
        calculateTotalNumberOfGuest();
    }

    public BookedRoom(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }

}
