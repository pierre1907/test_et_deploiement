package sn.ksi.test_et_deploiement.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import sn.ksi.test_et_deploiement.entity.User;

@Service
public class UserServiceImpl implements UserService {
    private List<User> users = new ArrayList<>(
            List.of(new User(1L, "Saint-Pierre", "KASSI", "pierre@ksi.com", "123456"),
                    new User(2L, "Amzah", "COULIBALY", "amz@clb.com", "123456"),
                    new User(3L, "Med", "DJB", "kate@djb.com", "123456")
            )
    );

    private long idCounter = 4;

    /**
     * @param user
     * @return User
     */
    @Override
    public User saveUser(User user) {
        if (user.getId() == null) {
            user.setId(idCounter++);
        }
        users.add(user);
        return user;
    }

    /**
     * @param id
     * @return User
     */
    @Override
    public User fetchUserById(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * @return
     */
    @Override
    public List<User> findAllUsers() {
        return users;
    }



    /**
     * @param id
     * Supprime l'utilisateur par ID
     **/
    @Override
    public void deleteUser(Long id) {
        Optional<User> userToDelete = users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();

        if (userToDelete.isPresent()) {
            users.remove(userToDelete.get());
            System.out.println("Utilisateur supprimé avec succès.");
        } else {
            throw new RuntimeException("Utilisateur avec l'ID " + id + " non trouvé.");
        }
    }
}
