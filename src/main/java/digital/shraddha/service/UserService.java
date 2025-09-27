package digital.shraddha.service;

import digital.shraddha.model.dto.request.UpdateUserRequest;
import digital.shraddha.model.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

	UserDto createUser(UserDto userDto);

	UserDto getUserById(UUID id);

	UserDto getUserByUsername(String username);

	UserDto getUserByEmail(String email);

	List<UserDto> getAllUsers();

	UserDto updateUser(UUID id, UpdateUserRequest request);

	void deleteUser(UUID id);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	boolean existsByPhone(String phone);
}
