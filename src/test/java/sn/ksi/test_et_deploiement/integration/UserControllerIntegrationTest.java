package sn.ksi.test_et_deploiement.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sn.ksi.test_et_deploiement.controller.UserController;
import sn.ksi.test_et_deploiement.entity.User;
import sn.ksi.test_et_deploiement.service.UserService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserController userController;

    @BeforeEach
    public void setup() {
        // Initialisation avec des données de test
        userService.saveUser(new User(null, "Test1", "User1", "test1@example.com", "password123"));
        userService.saveUser(new User(null, "Test2", "User2", "test2@example.com", "password123"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].firstName").value("Saint-Pierre"))
                .andExpect(jsonPath("$[1].email").value("amz@clb.com"))
                .andExpect(jsonPath("$[2].lastName").value("DJB"));
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = userService.saveUser(new User(null, "John", "Doe", "john@example.com", "password123"));

        mockMvc.perform(get("/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    public void testCreateUser() throws Exception {
        User newUser = new User(null, "Jane", "Doe", "jane@example.com", "password123");
        String userJson = objectMapper.writeValueAsString(newUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.email").value("jane@example.com"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Créez l'utilisateur avec l'ID 11 si nécessaire
        User user = new User(11L, "Test", "User", "test@example.com", "password");
        userController.createUser(user);  // Si vous avez un repository pour gérer les utilisateurs

        mockMvc.perform(delete("/users/11"))
                .andExpect(status().isOk())
                .andExpect(content().string("Utilisateur supprimé avec succès."));
    }
}
