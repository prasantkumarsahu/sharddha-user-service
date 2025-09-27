package digital.shraddha.controller;

import digital.shraddha.model.dto.request.UpdateUserRequest;
import digital.shraddha.model.dto.UserDto;
import digital.shraddha.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping ("/users")
@RequiredArgsConstructor
@Tag (name = "UserEntity Management", description = "CRUD operations for users")
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(userService.createUser(userDto));
	}

	@GetMapping ("/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}

	@GetMapping ("/username/{username}")
	public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
		return ResponseEntity.ok(userService.getUserByUsername(username));
	}

	@GetMapping ("/email/{email}")
	public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
		return ResponseEntity.ok(userService.getUserByEmail(email));
	}

	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@PutMapping ("/{id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable UUID id,
											  @RequestBody UpdateUserRequest request) {
		return ResponseEntity.ok(userService.updateUser(id, request));
	}

	@DeleteMapping ("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping ("/exists/username/{username}")
	public ResponseEntity<Boolean> existsByUsername(@PathVariable String username) {
		return ResponseEntity.ok(userService.existsByUsername(username));
	}

	@GetMapping ("/exists/email/{email}")
	public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
		return ResponseEntity.ok(userService.existsByEmail(email));
	}

	@GetMapping ("/exists/phone/{phone}")
	public ResponseEntity<Boolean> existsByPhone(@PathVariable String phone) {
		return ResponseEntity.ok(userService.existsByPhone(phone));
	}
}
