package uz.pdp.task_1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.task_1.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

//    boolean existsByIdAndNumberAndHotelId(Integer id, Integer number, Integer hotel_id);
    boolean existsByNumberAndHotelId(Integer number, Integer hotel_id);
    Page<Room> findAllByHotelId(Integer hotel_id, Pageable pageable);
}

