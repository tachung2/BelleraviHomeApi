package kr.co.belleravi.homeapi.service;

import kr.co.belleravi.homeapi.dto.RegisterDTO;
import kr.co.belleravi.homeapi.entity.UserEntity;
import kr.co.belleravi.homeapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Value("${VALID_INVITATION_CODE}")
    private String validCode;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void registerProc(RegisterDTO registerDTO) {

        boolean isUser = userRepository.existsByUsername(registerDTO.getUsername());
        if (isUser) {
            return;
        }

        if (validCode.equals(registerDTO.getCode())) {
            UserEntity user = new UserEntity();

            user.setUsername(registerDTO.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(registerDTO.getPassword()));
            user.setRole("ROLE_ADMIN");

            userRepository.save(user);
        }

    }
}
