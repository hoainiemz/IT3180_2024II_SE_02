package org.example.hellofx.repository;

import org.example.hellofx.model.Feedback;
import org.example.hellofx.model.NotificationItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("""
        SELECT f
        FROM Feedback f
        WHERE (:showUnWatchedOnly = false OR f.watched = false)
        ORDER BY f.createdAt DESC
    """)
    List<Feedback> findAllByWatchedFilter(
            @Param("showUnWatchedOnly") boolean showUnWatchedOnly,
            Pageable pageable
    );
}
