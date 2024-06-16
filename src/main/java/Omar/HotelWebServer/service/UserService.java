package Omar.HotelWebServer.service;
import Omar.HotelWebServer.dataAccess.model.classes.User;
import Omar.HotelWebServer.dataAccess.repository.UserRepository;
import Omar.HotelWebServer.utils.DTOs.UpdatePasswordDTO;
import Omar.HotelWebServer.utils.exceptions.EmptyResultException;
import Omar.HotelWebServer.utils.exceptions.NotAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElse(null); // Handle not found case appropriately
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUserProfile(User updatedUser) {
        return userRepository.save(updatedUser);
    }

    public void updateUserPassword(UpdatePasswordDTO dto , int userId) {

        Optional<User> user = userRepository.getById(userId);
        if(user.isEmpty()) {
            throw new EmptyResultException("User with such id doesn't exist");
        }
        if(!passwordEncoder.matches(dto.getOldPassword(), user.get().getPassword())) {
            throw new NotAuthorizedException("Old password is incorrect");
        }
        String encodedPassword = passwordEncoder.encode(dto.getNewPassword());
        user.get().setPassword(encodedPassword);

        userRepository.save(user.get());
    }

    public String getUserNameById(Integer userId) {
        return userRepository.findById(userId)
                .map(User::getUsername)
                .orElseThrow(() -> new EmptyResultException("User not found with id: " + userId));
    }

}
