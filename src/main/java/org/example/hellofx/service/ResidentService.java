package org.example.hellofx.service;

import org.example.hellofx.model.Account;
import org.example.hellofx.model.Resident;
import org.example.hellofx.model.enums.AccountType;
import org.example.hellofx.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ResidentService {
    @Autowired
    ResidentRepository residentRepository;
    @Autowired
    RepositoryImpl repositoryImpl;

    public List<Resident> nativeResidentQuery(String query) {
        return repositoryImpl.executeRawSql(query, Resident.class);
    }

    public List<Resident> residentsQuery(Account profile, Resident resident) {
        if (profile.getRole() == AccountType.Admin || profile.getRole() == AccountType.Client) {
            return residentRepository.findAll();
        }
        return residentRepository.findByHouseId(resident.getHouseId());
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
        if (profile.getRole() == AccountType.Resident) {
            return Arrays.asList(resident.getHouseId());
        }
        for (int i = 0; i < 10; i++) {
            try {
                return residentRepository.findDistinctNonNullHouseId();
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
                resident.getHouseId(),
                resident.getIdentityCard(),
                resident.getMoveInDate()
        );
    }
}
