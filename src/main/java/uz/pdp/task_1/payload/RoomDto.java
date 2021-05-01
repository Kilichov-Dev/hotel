package uz.pdp.task_1.payload;

import lombok.Data;

@Data
public class RoomDto {
    private Integer number;

    private String floor;

    private String size;

    private Integer hotel_id;
}
