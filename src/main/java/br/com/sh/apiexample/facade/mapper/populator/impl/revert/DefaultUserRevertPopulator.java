package br.com.sh.apiexample.facade.mapper.populator.impl.revert;

import br.com.sh.apiexample.model.UserModel;
import br.com.sh.apiexample.model.dto.UserDto;
import br.com.sh.apiexample.facade.mapper.populator.Populator;
import br.com.sh.apiexample.util.CustomRandomUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DefaultUserRevertPopulator implements Populator<UserModel, UserDto> {
    @Deprecated(since = "1.0", forRemoval = true)
    @Override
    public UserDto populate(UserModel userModel, UserDto userDto) {
        return populate(userModel);
    }

    @Override
    public UserDto populate(UserModel userModel) {
        return new UserDto(userModel.getEmail(),
                userModel.getFirstName(),
                userModel.getLastName(),
                LocalDateTime.now(),
                CustomRandomUtil.generateRandomString() ? "11 11188888818" : null);
    }
}
