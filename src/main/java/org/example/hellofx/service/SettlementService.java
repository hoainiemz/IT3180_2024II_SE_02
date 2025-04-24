package org.example.hellofx.service;

import org.example.hellofx.dto.ApartmentCountProjection;
import org.example.hellofx.model.Settlement;
import org.example.hellofx.repository.SettlementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettlementService {
    @Autowired
    private SettlementRepository settlementRepository;

    public List<ApartmentCountProjection> getApartmentsAndResidentCount(Integer residentId, String s) {
        return settlementRepository.findFilteredApartmentCountsByResidentId(residentId, s);
    }

    public List<ApartmentCountProjection> getApartmentsAndResidentCount(String s) {
        return settlementRepository.findApartmentCountsBySearch(s);
    }

    public void saveAll(List<Settlement>ds) {
        settlementRepository.saveAll(ds);
    }
}
