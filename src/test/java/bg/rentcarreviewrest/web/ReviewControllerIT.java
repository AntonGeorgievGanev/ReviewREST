package bg.rentcarreviewrest.web;

import bg.rentcarreviewrest.model.dto.ReviewDTO;
import bg.rentcarreviewrest.model.entity.Review;
import bg.rentcarreviewrest.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerIT {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetById() throws Exception {

        var actualReview = createReview();

        ResultActions result = mockMvc
                .perform(get("/api/reviews/{id}", actualReview.getId())
                        .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(actualReview.getId().intValue())))
                .andExpect(jsonPath("$.title", is(actualReview.getTitle())))
                .andExpect(jsonPath("$.rating", is(actualReview.getRating())))
                .andExpect(jsonPath("$.description", is(actualReview.getDescription())))
                .andExpect(jsonPath("$.author", is(actualReview.getAuthor())));
    }

    @Test
    public void testReviewNotFound() throws Exception {
        mockMvc
                .perform(get("/api/reviews/{id}", "99999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateReview() throws Exception {
        ReviewDTO incomeReviewDTO = new ReviewDTO();

        incomeReviewDTO.setTitle("Test title");
        incomeReviewDTO.setDescription("Test description");
        incomeReviewDTO.setAuthor("Test author");
        incomeReviewDTO.setRating(5);
        incomeReviewDTO.setUserId(999L);
        incomeReviewDTO.setPublished(LocalDate.parse("2024-07-27"));

        MvcResult result = mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incomeReviewDTO))
                ).andExpect(status().isOk())
                .andReturn();

        Optional<Review> reviewOptional = reviewRepository.findById(1L);
        Assertions.assertTrue(reviewOptional.isPresent());
        Review review = reviewOptional.get();
        Assertions.assertEquals(review.getTitle(), incomeReviewDTO.getTitle());
        Assertions.assertEquals(review.getDescription(), incomeReviewDTO.getDescription());
        Assertions.assertEquals(review.getAuthor(), incomeReviewDTO.getAuthor());
        Assertions.assertEquals(review.getRating(), incomeReviewDTO.getRating());
        Assertions.assertEquals(review.getPublished(), incomeReviewDTO.getPublished());
    }

    @Test
    void testDeleteReview() throws Exception {
        var actualReview = createReview();
        mockMvc.perform(delete("/api/reviews/{id}", actualReview.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Assertions.assertTrue(reviewRepository.findById(actualReview.getId()).isEmpty());

    }

        private Review createReview() {
        Review review = new Review();
        review.setTitle("Title");
        review.setRating(5);
        review.setDescription("Description");
        review.setAuthor("Test");
        review.setUserId(1L);
        review.setPublished(LocalDate.now());
        return reviewRepository.save(review);
    }

}
