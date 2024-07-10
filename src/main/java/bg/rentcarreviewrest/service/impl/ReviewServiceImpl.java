package bg.rentcarreviewrest.service.impl;

import bg.rentcarreviewrest.model.dto.IncomeReviewDTO;
import bg.rentcarreviewrest.model.dto.ReviewDTO;
import bg.rentcarreviewrest.model.entity.Review;
import bg.rentcarreviewrest.repository.ReviewRepository;
import bg.rentcarreviewrest.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final ModelMapper modelMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createReview(IncomeReviewDTO incomeReviewDTO) {
        Review review = modelMapper.map(incomeReviewDTO, Review.class);
        reviewRepository.save(review);
    }

    @Override
    public ReviewDTO getReviewById(Long id) {
        return reviewRepository.findById(id)
                .map(reviewFromDB -> modelMapper.map(reviewFromDB, ReviewDTO.class))
                .orElseThrow(() -> new IllegalArgumentException("Not found!"));
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(reviewFromDb -> modelMapper.map(reviewFromDb, ReviewDTO.class)).toList();
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
