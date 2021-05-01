package uz.pdp.task_1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task_1.entity.Hotel;
import uz.pdp.task_1.repository.HotelRepository;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public List<Hotel> getHotel() {
        List<Hotel> hotelList = hotelRepository.findAll();
        return hotelList;
    }

    @GetMapping("/{id}")
    public Hotel getHotel(@PathVariable Integer id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            return hotel;
        }
        return new Hotel();
    }

    @PostMapping
    public String addHotel(@RequestBody Hotel hotel) {
        boolean exists = hotelRepository.existsByName(hotel.getName());
        if (exists) {
            return "This hotel already exists!";
        }
        hotelRepository.save(hotel);
        return "Hotel added!";

    }

    @PutMapping("/{id}")
    public String editHotel(@PathVariable Integer id, @RequestBody Hotel hotel) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);

        if (optionalHotel.isPresent()) {
            boolean exists = hotelRepository.existsByName(hotel.getName());
            if (exists) {
                return "This hotel already exists!";
            }
            Hotel hotel1 = optionalHotel.get();
            hotel1.setName(hotel.getName());

            hotelRepository.save(hotel1);
            return "Hotel editing!";
        }
        return "Hotel not found!";
    }

    @DeleteMapping("/{id}")
    public String deleteHotel(@PathVariable Integer id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()) {
            hotelRepository.deleteById(id);
            return "Hotel deleted!";
        }
        return "Hotel not found!";
    }
}
