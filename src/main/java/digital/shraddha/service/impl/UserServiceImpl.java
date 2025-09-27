package digital.shraddha.service.impl;

import digital.shraddha.exception.ApiException;
import digital.shraddha.mapper.UserMapper;
import digital.shraddha.model.dto.request.UpdateUserRequest;
import digital.shraddha.model.dto.UserDto;
import digital.shraddha.model.entity.UserEntity;
import digital.shraddha.model.enums.AccountStatus;
import digital.shraddha.model.enums.ErrorType;
import digital.shraddha.repo.UserEntityRepo;
import digital.shraddha.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

	private final UserEntityRepo userEntityRepo;
	private final UserMapper userMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		// Check for existing username/email
		if (userEntityRepo.existsByUsername(userDto.username())) {
			throw new ApiException(ErrorType.USER_ALREADY_EXISTS,
					"Username '" + userDto.username() + "' is already taken");
		}
		if (userEntityRepo.existsByEmail(userDto.email())) {
			throw new ApiException(ErrorType.USER_ALREADY_EXISTS,
					"Email '" + userDto.email() + "' is already registered");
		}

		UserEntity userEntity = userMapper.toEntity(userDto);
		UserEntity saved = userEntityRepo.save(userEntity);
		return userMapper.toDto(saved);
	}

	@Override
	public UserDto getUserById(UUID id) {
		UserEntity userEntity = userEntityRepo.findById(id)
				.orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND,
						"UserEntity with ID " + id + " not found"));
		return userMapper.toDto(userEntity);
	}

	@Override
	public UserDto getUserByUsername(String username) {
		UserEntity userEntity = userEntityRepo.findByUsername(username)
				.orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND,
						"UserEntity with username '" + username + "' not found"));
		return userMapper.toDto(userEntity);
	}

	@Override
	public UserDto getUserByEmail(String email) {
		UserEntity userEntity = userEntityRepo.findByEmail(email)
				.orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND,
						"UserEntity with email '" + email + "' not found"));
		return userMapper.toDto(userEntity);
	}

	@Override
	public List<UserDto> getAllUsers() {
		return userMapper.toDtoList(userEntityRepo.findAll());
	}

	@Override
	public UserDto updateUser(UUID id, UpdateUserRequest request) {
		UserEntity userEntity = userEntityRepo.findById(id)
				.orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND,
						"UserEntity with ID " + id + " not found"));
		userMapper.updateUserFromRequest(request, userEntity);
		return userMapper.toDto(userEntityRepo.save(userEntity));
	}

	@Override
	public void deleteUser(UUID id) {
		UserEntity userEntity = userEntityRepo.findById(id)
				.orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND,
						"UserEntity with ID " + id + " not found"));
		userEntity.setStatus(AccountStatus.DELETED);
		userEntityRepo.save(userEntity);
	}

	@Override
	public boolean existsByUsername(String username) {
		return userEntityRepo.existsByUsername(username);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userEntityRepo.existsByEmail(email);
	}

	@Override
	public boolean existsByPhone(String phone) {
		return userEntityRepo.existsByPhone(phone);
	}
}
