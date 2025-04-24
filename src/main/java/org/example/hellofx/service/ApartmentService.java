package org.example.hellofx.service;

import org.example.hellofx.model.Apartment;
import org.example.hellofx.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApartmentService {
    @Autowired
    private ApartmentRepository apartmentRepository;

    public boolean checkExistsByApartmentId(String s) {
        return apartmentRepository.existsByApartmentId(s);
    }

    public Apartment save(Apartment apartment) {
        for (int i = 0; i < 10; i++) {
            try {
                return apartmentRepository.save(apartment);
            }
            catch (Exception e) {
                continue;
            }
        }
        return null;
    }
}
