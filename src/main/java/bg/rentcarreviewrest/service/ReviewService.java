package bg.rentcarreviewrest.service;

import bg.rentcarreviewrest.model.dto.IncomeReviewDTO;
import bg.rentcarreviewrest.model.dto.ReviewDTO;

import java.util.List;


public interface ReviewService {
    void createReview(IncomeReviewDTO incomeReviewDTO);
    ReviewDTO getReviewById(Long id);
    List<ReviewDTO> getAllReviews();
    void deleteReview(Long id);
}
