package com.hotel.hotelapplication.service;

import com.hotel.hotelapplication.exception.ResourceNotFoundException;
import com.hotel.hotelapplication.model.Room;
import com.hotel.hotelapplication.repositoriy.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {
        private final RoomRepository roomRepository;
    @Override
    public Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice) throws SQLException, IOException {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        if (!file.isEmpty()){
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
        }
        return roomRepository.save(room);
    }
    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public byte[] gedtRoomPhotoByRoomId(Long roomId) throws SQLException {
        Optional<Room> theRoom=roomRepository.findById(roomId);
        if(theRoom.isEmpty()){
            throw  new ResourceNotFoundException("Sorry,Room not found");
        }
        Blob photoBlob=theRoom.get().getPhoto();
        if(photoBlob!=null){
            return photoBlob.getBytes(1,(int) photoBlob.length());
        }
        return null ;
    }

    @Override
    public Optional<Room> getRoomById(Long roomId) {
        return Optional.empty();
    }

    @Override
    public void deleteRoom(Long roomId) {
        Optional<Room> theRoom=roomRepository.findById(roomId);
        if(theRoom.isPresent()){
            roomRepository.deleteById(roomId);
        }
    }

    @Override
    public byte[] getRoomPhotoByRoomId(Long roomId) {
        return new byte[0];
    }

    @Override
    public Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes) {
        return null;
    }

    @Override
    public List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        return roomRepository.findAvailableRoomsByDatesAndType(checkInDate,checkOutDate,roomType);
    }

}
