package com.parcial.dos.parcialdos.account.service;

import com.parcial.dos.parcialdos.account.dto.AccountOwnerBalanceDTO;
import com.parcial.dos.parcialdos.account.dto.AccountRequestDTO;
import com.parcial.dos.parcialdos.account.dto.AccountResponseDTO;
import com.parcial.dos.parcialdos.account.entity.Account;
import com.parcial.dos.parcialdos.account.repository.AccountRepository;
import com.parcial.dos.parcialdos.account.exception.ResourceNotFoundException; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public AccountResponseDTO create(AccountRequestDTO request) {
        Account account = mapToEntity(request);
        account.setActive(true);
        Account savedAccount = repository.save(account);
        return mapToResponseDTO(savedAccount);
    }

    @Transactional(readOnly = true)
    public List<AccountResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AccountResponseDTO getById(Long id) {
        Account account = findAccountById(id);
        return mapToResponseDTO(account);
    }

    @Transactional
    public String update(Long id, AccountRequestDTO request) {
        Account account = findAccountById(id);
        BigDecimal balanceAnterior = account.getBalance();
        BigDecimal balanceActual = request.getBalanceActual();

        account.setBalance(balanceActual);
        repository.save(account);

        return String.format(
                "La cuenta %s fue actualizada: balanceAnterior=%.2f, balanceActual=%.2f",
                account.getAccountNumber(),
                balanceAnterior,
                balanceActual
        );
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cuenta no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public AccountOwnerBalanceDTO findByNumeroCuenta(String numeroCuenta) {
        Account account = repository.findByAccountNumber(numeroCuenta)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con número: " + numeroCuenta));
        return mapToOwnerBalanceDTO(account);
    }


    private Account findAccountById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id: " + id));
    }

    private AccountResponseDTO mapToResponseDTO(Account account) {
        return new AccountResponseDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getOwnerName(),
                account.getBalance(),
                account.getActive()
        );
    }

    private Account mapToEntity(AccountRequestDTO dto) {
        return new Account(
                dto.getNumeroCuenta(),
                dto.getDueno(),
                dto.getBalanceActual(),
                true 
        );
    }

    private AccountOwnerBalanceDTO mapToOwnerBalanceDTO(Account account) {
        return new AccountOwnerBalanceDTO(
                account.getOwnerName(),
                account.getBalance()
        );
    }
}