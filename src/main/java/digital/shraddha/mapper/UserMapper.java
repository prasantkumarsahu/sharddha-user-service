package digital.shraddha.mapper;

import digital.shraddha.model.dto.UpdateUserRequest;
import digital.shraddha.model.dto.UserDto;
import digital.shraddha.model.entity.UserEntity;
import org.mapstruct.*;
import org.springframework.util.StringUtils;

import java.util.List;

@Mapper (componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

	UserDto toDto(UserEntity userEntity);

	@Mapping (target = "id", ignore = true)
	UserEntity toEntity(UserDto userDto);

	List<UserDto> toDtoList(List<UserEntity> userEntities);

	@BeanMapping (nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateUserFromRequest(UpdateUserRequest request, @MappingTarget UserEntity userEntity);

	@Condition
	default boolean isNotBlank(String value) {
		return StringUtils.hasText(value);
	}
}
