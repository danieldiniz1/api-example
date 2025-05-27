package br.com.sh.apiexample.facade.mapper.populator.impl.revert;

import br.com.sh.apiexample.facade.mapper.populator.Populator;
import br.com.sh.apiexample.model.dto.UserDto;
import br.com.sh.apiexample.model.projection.UserProjectionDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class DefaultUserDtoPopulator implements Populator<UserProjectionDto, UserDto> {


    @Override
    public UserDto populate(UserProjectionDto userProjectionDto, UserDto userDto) {
        return null;
    }

    @Override
    public UserDto populate(UserProjectionDto userProjectionDto) {
        validations(userProjectionDto);
        return new UserDto(userProjectionDto.email(),
                userProjectionDto.firstName(),
                userProjectionDto.lastName(), LocalDateTime.now());
    }

    private void validations(UserProjectionDto userProjectionDto) {
        Objects.requireNonNull(userProjectionDto);
    }
}
