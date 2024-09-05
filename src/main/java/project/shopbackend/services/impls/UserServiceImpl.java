package project.shopbackend.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.shopbackend.dtos.RegisterDTO;
import project.shopbackend.models.User;
import project.shopbackend.repositories.UserRepository;
import project.shopbackend.services.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public void register(RegisterDTO registerDTO) {
        if(!registerDTO.getPassword().equals(registerDTO.getRetypePassword()))
            throw new RuntimeException("Password not same");
        userRepository.save(User.builder()
                        .fullName(registerDTO.getFullName())
                        .userName(registerDTO.getUserName())
                        .phoneNumber(registerDTO.getPhoneNumber())
                        .email(registerDTO.getEmail())
                        .password(registerDTO.getPassword())
                .build());
    }
}
