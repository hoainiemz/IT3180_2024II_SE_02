package org.example.hellofx.service;

import org.example.hellofx.model.Feedback;
import org.example.hellofx.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getTopFeedbackByWatchedStatusOrderByCreatedAtDesc(boolean watchedStatus, Pageable pageable) {
        return feedbackRepository.findAllByWatchedFilter(watchedStatus, pageable);
    }
}
