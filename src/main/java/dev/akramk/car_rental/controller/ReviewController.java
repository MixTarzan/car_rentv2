package dev.akramk.car_rental.controller;

import dev.akramk.car_rental.model.Review;
import dev.akramk.car_rental.service.ReviewService;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/cars")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<Review> createReview(@RequestBody String message,
                                               @RequestParam("stars") int stars,
                                               @RequestParam("carId") ObjectId carId) {
        Review createdReview = reviewService.createReview(message, stars, carId);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }
}
