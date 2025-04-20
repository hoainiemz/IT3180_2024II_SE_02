package org.example.hellofx.service;

import org.example.hellofx.dto.ResidentBillPaymentDTO;
import org.example.hellofx.model.Account;
import org.example.hellofx.model.Bill;
import org.example.hellofx.model.Payment;
import org.example.hellofx.model.Resident;
import org.example.hellofx.model.enums.AccountType;
import org.example.hellofx.model.enums.GenderType;
import org.example.hellofx.repository.AccountRepository;
import org.example.hellofx.repository.BillRepository;
import org.example.hellofx.repository.PaymentRepository;
import org.example.hellofx.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@Service
public class DataBaseService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ResidentRepository residentRepository;
    @Autowired
    private RepositoryImpl repositoryImpl;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BillRepository billRepository;

    public List<ResidentBillPaymentDTO> getResidentPayments(Integer residentId) {
        List<Object[]> results = paymentRepository.findResidentBillsWithPayments(residentId);

        return results.stream().map(row -> new ResidentBillPaymentDTO(
                (Integer) row[0],  // residentId
                (Integer) row[1],  // userId
                (String) row[2],   // firstName
                (String) row[3],   // lastName
                (GenderType) row[4],   // gender (should be GenderType if applicable)
                (LocalDate) row[5], // dateOfBirth
                (String) row[6],   // identityCard
                (String) row[7],   // houseId
                (LocalDate) row[8], // moveInDate
                (Integer) row[9],  // billId
                (Double) row[10],  // amount
                (Double) row[11],  // lateFee
                (LocalDateTime) row[12], // dueDate
                (String) row[13],  // content
                (String) row[14],  // description
                (Boolean) row[15], // required
                (LocalDateTime) row[16] // payTime
        )).collect(Collectors.toList());
    }

    

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public boolean checkAccountExistByUsername(String value) {
        return accountRepository.existsByUsername(value);
    }

    public boolean checkAccountExistByEmail(String value) {
        return accountRepository.existsByEmail(value);
    }

    public boolean checkAccountExistByPhone(String value) {
        return accountRepository.existsByPhone(value);
    }

    public void createAccount(String username, String password, String email, String phone) {
        Account acc = new Account(null, username, email, phone, password, null);
        for (int i = 0; i < 10; i++) {
            try {
                accountRepository.save(acc);
                break;
            }
            catch (Exception e) {
                continue;
            }
        }
    }

    public Account findAccountByUsernameAndPassword(String username, String passwordHash) {
        Optional<Account> result = accountRepository.findByUsernameAndPasswordHash(username, passwordHash);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    public int passwordChangeQuery(Account profile, String newPassword) {
        return accountRepository.updatePasswordByUsername(profile.getUsername(), newPassword);
    }

    public List<Resident> residentsQuery(Account profile, Resident resident) {
        if (profile.getRole() == AccountType.Admin || profile.getRole() == AccountType.Client) {
            return residentRepository.findAll();
        }
        return residentRepository.findByHouseId(resident.getHouseId());
    }

    public List<Resident> nativeResidentQuery(String query) {
        return repositoryImpl.executeRawSql(query, Resident.class);
    }

    public List<Account> nativeAccountQuery(String query) {
        return repositoryImpl.executeRawSql(query, Account.class);
    }

    public Account findAccountByUserId(int id) {
        return accountRepository.findByUserId(id).get();
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

    public void updateAccount(Account account) {
        accountRepository.updateRowByUserId(account.getUserId(), account.getRole(), account.getEmail(), account.getPhone());
    }

    @Transactional
    public void saveAllPayments(List<Payment> payments) {
        paymentRepository.saveAll(payments);
    }

    @Transactional
    public void updateBill(Bill bill) {
        int d = 10;
        while (d --> 0) {
            try {
                billRepository.save(bill);
                return;
            } catch (Exception e) {
                continue;
            }
        }
    }

    @Transactional
    public void deletePayments(List<Integer> dsOut) {
        if (dsOut == null || dsOut.isEmpty()) {
            throw new IllegalArgumentException("List of bill IDs cannot be null or empty");
        }
        paymentRepository.deletePaymentsByPaymentId(dsOut);
    }

}
