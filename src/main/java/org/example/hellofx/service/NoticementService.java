package org.example.hellofx.service;

import org.example.hellofx.model.Noticement;
import org.example.hellofx.repository.NoticementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticementService {
    @Autowired
    private NoticementRepository noticementRepository;

    public List<Noticement> findAllByNotificationId(Integer id) {
        return noticementRepository.findAllByNotificationId(id);
    }

    public void markAsWatched(Integer notificationId, Integer residentId) {
        noticementRepository.markAsWatched(notificationId, residentId);
    }

    public void saveAll(List<Noticement> noticements) {
        noticementRepository.saveAll(noticements);
    }

    public void deleteNoticementsByNoticementId(List<Integer> dsout) {
        noticementRepository.deleteNoticementsByNoticementId(dsout);
    }
}
