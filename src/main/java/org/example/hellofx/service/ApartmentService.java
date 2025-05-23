package org.example.hellofx.service;

import org.example.hellofx.model.Apartment;
import org.example.hellofx.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApartmentService {
    @Autowired
    private ApartmentRepository apartmentRepository;

    public boolean checkExistsByApartmentName(String s) {
        return apartmentRepository.existsByApartmentName(s);
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
        return apartmentRepository.save(apartment);
    }

    public Apartment getByApartmentId(Integer apartmentId) {
        return apartmentRepository.findByApartmentId(apartmentId);
    }

    public List<Integer> getApartmentIdsByBillId(Integer billId) {
        return apartmentRepository.findApartmentIdsByBillId(billId);
    }

    public Apartment findApartmentByApartmentName(String name) {
        return apartmentRepository.findByApartmentName(name);
    }

    public void deleteApartmentById(Integer id) {
        apartmentRepository.deleteByApartmentId(id);
    }
}
