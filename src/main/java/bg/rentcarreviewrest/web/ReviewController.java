package bg.rentcarreviewrest.web;

import bg.rentcarreviewrest.model.dto.IncomeReviewDTO;
import bg.rentcarreviewrest.model.dto.ReviewDTO;
import bg.rentcarreviewrest.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> addReview(@RequestBody IncomeReviewDTO incomeReviewDTO) {
        reviewService.createReview(incomeReviewDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> findReviewById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewDTO> deleteReviewById(@PathVariable("id") Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }
}
