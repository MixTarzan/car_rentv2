package dev.akramk.car_rental.service;

import dev.akramk.car_rental.model.Car;
import dev.akramk.car_rental.model.Review;
import dev.akramk.car_rental.repo.ReviewRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final MongoTemplate mongoTemplate;
    public Review createReview(String body, int stars, ObjectId carId ){
        Review review = reviewRepository.insert(new Review (body, stars));

        mongoTemplate.update(Car.class)
                .matching(Criteria.where("id").is(carId))
                .apply(new Update().push("reviewId").value(review))
                .first();

        return review;
    }
}
