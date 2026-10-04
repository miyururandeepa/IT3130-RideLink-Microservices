package com.ridelink.account.service;

import com.ridelink.account.dto.AccountResponse;
import com.ridelink.account.dto.LoginRequest;
import com.ridelink.account.dto.ProfileUpdateRequest;
import com.ridelink.account.dto.RegisterRequest;
import com.ridelink.account.dto.RoleUpdateRequest;
import com.ridelink.account.dto.StatusUpdateRequest;
import com.ridelink.account.entity.Account;
import com.ridelink.account.enums.AccountStatus;
import com.ridelink.account.enums.Role;
import com.ridelink.account.repository.AccountRepository;
import com.ridelink.account.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AccountService(
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // =========================
    // REGISTER
    // =========================

    public AccountResponse register(RegisterRequest request) {

        if (accountRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Account account = new Account();

        account.setName(request.getName());
        account.setEmail(request.getEmail());

        // Encrypt password before saving
        account.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // Default values for a newly registered account
        account.setRole(Role.PASSENGER);
        account.setStatus(AccountStatus.ACTIVE);

        Account savedAccount = accountRepository.save(account);

        return AccountResponse.from(savedAccount);
    }

    // =========================
    // LOGIN
    // =========================

    public String login(LoginRequest request) {

        Account account = accountRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        // Check password
        if (!passwordEncoder.matches(
                request.getPassword(),
                account.getPassword())) {

            throw new RuntimeException("Invalid email or password");
        }

        // Check account status
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new RuntimeException("Account is not active");
        }

        // Generate JWT
        return jwtService.generateToken(
                account.getEmail(),
                account.getRole()
        );
    }

    // =========================
    // GET PROFILE
    // =========================

    public AccountResponse getProfile(String email) {

        Account account = accountRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        return AccountResponse.from(account);
    }

    // =========================
    // UPDATE PROFILE
    // =========================

    public AccountResponse updateProfile(
            String currentEmail,
            ProfileUpdateRequest request) {

        Account account = accountRepository
                .findByEmail(currentEmail)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        // Update name if provided
        if (request.getName() != null &&
                !request.getName().isBlank()) {

            account.setName(request.getName());
        }

        // Update email if provided
        if (request.getEmail() != null &&
                !request.getEmail().isBlank() &&
                !request.getEmail().equals(account.getEmail())) {

            if (accountRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already exists");
            }

            account.setEmail(request.getEmail());
        }

        // Update password if provided
        if (request.getPassword() != null &&
                !request.getPassword().isBlank()) {

            account.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );
        }

        Account updatedAccount = accountRepository.save(account);

        return AccountResponse.from(updatedAccount);
    }

    // =========================
    // UPDATE STATUS
    // =========================

    public AccountResponse updateStatus(
            Long accountId,
            StatusUpdateRequest request) {

        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        account.setStatus(request.getStatus());

        Account updatedAccount = accountRepository.save(account);

        return AccountResponse.from(updatedAccount);
    }

    // =========================
    // UPDATE ROLE
    // =========================

    public AccountResponse updateRole(
            Long accountId,
            RoleUpdateRequest request) {

        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        account.setRole(request.getRole());

        Account updatedAccount = accountRepository.save(account);

        return AccountResponse.from(updatedAccount);
    }
}