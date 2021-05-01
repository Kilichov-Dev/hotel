package uz.pdp.task_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task_1.entity.Hotel;
import uz.pdp.task_1.entity.Room;
import uz.pdp.task_1.payload.RoomDto;
import uz.pdp.task_1.repository.HotelRepository;
import uz.pdp.task_1.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    RoomRepository roomRepository;

    @GetMapping("/{hotelId}")
    public Page<Room> getRoomByHotelId(@PathVariable Integer hotelId, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Room> roomPage = roomRepository.findAllByHotelId(hotelId, pageable);
        return roomPage;
    }


    @GetMapping
    public List<Room> getRoom() {
        List<Room> roomList = roomRepository.findAll();
        return roomList;
    }


    @GetMapping("/{id}")
    public Room getRoom(@PathVariable Integer id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            return room;
        }
        return new Room();
    }

    @PostMapping
    public String addRoom(@RequestBody RoomDto roomDto) {
        Room room = new Room();
        Optional<Hotel> hotelOptional = hotelRepository.findById(roomDto.getHotel_id());
        if (!hotelOptional.isPresent()) {
            return "Hotel not found!";
        }
        boolean exists = roomRepository.existsByNumberAndHotelIdAndSize(roomDto.getNumber(), roomDto.getHotel_id(), roomDto.getSize());
        if (exists) {
            return "This room number already exist!";
        }
        room.setFloor(roomDto.getFloor());
        room.setNumber(roomDto.getNumber());
        room.setSize(roomDto.getSize());

        room.setHotel(hotelOptional.get());
        roomRepository.save(room);
        return "Room added!";
    }

    @PutMapping("/{id}")
    public String editRoom(@PathVariable Integer id, @RequestBody RoomDto roomDto) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            Optional<Hotel> hotelOptional = hotelRepository.findById(roomDto.getHotel_id());

            if (!hotelOptional.isPresent()) {
                return "Hotel not found!";
            }
            boolean exists = roomRepository.existsByNumberAndHotelIdAndSize(roomDto.getNumber(), roomDto.getHotel_id(), roomDto.getSize());
            if (exists) {
                return "This room number already exist!";
            }
            Room room = optionalRoom.get();
            room.setFloor(roomDto.getFloor());
            room.setNumber(roomDto.getNumber());
            room.setSize(roomDto.getSize());

            room.setHotel(hotelOptional.get());
            roomRepository.save(room);
            return "Room editing!";
        }
        return "Room not found!";
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Integer id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            roomRepository.deleteById(id);
            return "Room deleted!";
        }
        return "Room not found!";
    }

}
