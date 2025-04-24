package org.example.hellofx.service;

import org.example.hellofx.dto.ApartmentCountProjection;
import org.example.hellofx.model.Apartment;
import org.example.hellofx.model.Settlement;
import org.example.hellofx.repository.SettlementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Settlement> getSettlementsByApartmentId(Integer id) {
        return settlementRepository.findSettlementsByApartmentId(id);
    }

    public void saveAll(List<Settlement>ds) {
        settlementRepository.saveAll(ds);
    }

    public void deleteByIds(List<Integer> ds) {
//        List<Long> longList = ds.stream()
//                .map(Integer::longValue)
//                .collect(Collectors.toList());
        settlementRepository.deleteSettlementsBySettlementId(ds);
    }
}
