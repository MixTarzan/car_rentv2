package dev.akramk.car_rental.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "Cars")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    private String id;
    private String name;
    private int price;
    private String color;
    private int year;
    private String description;
    private String poster;
    private String video_link;

    @DocumentReference
    private List<Review> reviewId;
}
