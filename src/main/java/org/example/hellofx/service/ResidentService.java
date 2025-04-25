package org.example.hellofx.service;

import org.example.hellofx.model.Account;
import org.example.hellofx.model.Resident;
import org.example.hellofx.model.Settlement;
import org.example.hellofx.model.enums.AccountType;
import org.example.hellofx.repository.RepositoryImpl;
import org.example.hellofx.repository.ResidentRepository;
import org.example.hellofx.repository.SettlementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResidentService {
    @Autowired
    private ResidentRepository residentRepository;
    @Autowired
    private RepositoryImpl repositoryImpl;
    @Autowired
    private SettlementRepository settlementRepository;

    public List<Resident> nativeResidentQuery(String query) {
        return repositoryImpl.executeRawSql(query, Resident.class);
    }

    public Resident findResidentByUserId(int id) {
        return residentRepository.findByUserId(id).get();
    }

    public boolean checkResidentExistByIdentityCard(String identityCard) {
        return residentRepository.existsByIdentityCard(identityCard);
    }

    public Resident findResidentByAccount(Account profile) {
        return residentRepository.findByUserId(profile.getUserId()).get();
    }




    @Transactional
    public List<String> findDistinctNonNullHouseId(Account profile, Resident resident) {
        for (int i = 0; i < 10; i++) {
            try {
                if (profile.getRole() == AccountType.Resident) {
                    return settlementRepository.findApartmentNamesByResidentId(resident.getResidentId());
                }
                return settlementRepository.findAllApartmentNames();
            }
            catch (Exception e) {
                continue;
            }
        }
        return null;
    }

    public void updateResident(Resident resident) {
        residentRepository.updateRowByUserId(
                resident.getUserId(),
                resident.getFirstName(),
                resident.getLastName(),
                resident.getDateOfBirth(),
                resident.getGender(),
                resident.getIdentityCard(),
                resident.getMoveInDate()
        );
    }

    public void save(Resident resident) {
        residentRepository.save(resident);
    }

    public List<Resident> findResidentsByFilters(String houseNameFilter, String roleFilter, String searchFilter) {
        return residentRepository.findResidentsByFilters(houseNameFilter, roleFilter, searchFilter);
    }
}
