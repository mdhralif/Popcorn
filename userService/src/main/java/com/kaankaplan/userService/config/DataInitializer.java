package com.kaankaplan.userService.config;

import com.kaankaplan.userService.dao.ClaimDao;
import com.kaankaplan.userService.dao.UserDao;
import com.kaankaplan.userService.entity.Claim;
import com.kaankaplan.userService.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ClaimDao claimDao;
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeClaims();
        initializeAdminUser();
        fixExistingUsers();
    }

    private void initializeClaims() {
        // Initialize CUSTOMER claim if it doesn't exist
        if (claimDao.getClaimByClaimName("CUSTOMER") == null) {
            Claim customerClaim = Claim.builder()
                    .claimName("CUSTOMER")
                    .build();
            claimDao.save(customerClaim);
            System.out.println("✅ CUSTOMER claim initialized");
        }

        // Initialize ADMIN claim if it doesn't exist
        if (claimDao.getClaimByClaimName("ADMIN") == null) {
            Claim adminClaim = Claim.builder()
                    .claimName("ADMIN")
                    .build();
            claimDao.save(adminClaim);
            System.out.println("✅ ADMIN claim initialized");
        }
    }

    private void initializeAdminUser() {
        // Check if admin user already exists
        String adminEmail = "admin@cinevision.com";
        User existingAdmin = userDao.findUserByEmail(adminEmail);
        
        if (existingAdmin == null) {
            Claim adminClaim = claimDao.getClaimByClaimName("ADMIN");
            
            User adminUser = User.builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode("admin123"))
                    .fullName("CineVision Admin")
                    .claim(adminClaim)
                    .build();
            
            userDao.save(adminUser);
            System.out.println("✅ Default admin user created:");
            System.out.println("   Email: admin@cinevision.com");
            System.out.println("   Password: admin123");
        } else {
            System.out.println("ℹ️  Admin user already exists: " + adminEmail);
        }
    }

    private void fixExistingUsers() {
        // Fix any existing users without proper claims
        Claim customerClaim = claimDao.getClaimByClaimName("CUSTOMER");
        
        // Find users with null claims and assign CUSTOMER claim
        userDao.findAll().forEach(user -> {
            if (user.getClaim() == null) {
                user.setClaim(customerClaim);
                userDao.save(user);
                System.out.println("✅ Fixed claim for user: " + user.getEmail());
            }
        });
    }
}
