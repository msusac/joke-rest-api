package hr.tvz.java.web.susac.joke.util.converter.impl;

import hr.tvz.java.web.susac.joke.dto.user.UserDTO;
import hr.tvz.java.web.susac.joke.model.User;
import hr.tvz.java.web.susac.joke.util.converter.ConverterUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Qualifier("JokeConverter")
public class UserConverterImpl implements ConverterUtil<User, UserDTO> {

    private final ModelMapper mapper;

    @Override
    public UserDTO convertToDTO(User entity) {
        UserDTO userDTO = mapper.map(entity, UserDTO.class);
        userDTO.setCommentCount(entity.getCommentList().size());
        userDTO.setJokeCount(entity.getJokeList().size());
        userDTO.setDateJoined(entity.getDateTimeCreated().toLocalDate());

        entity.getAuthoritySet().forEach(a ->{
            if(a.getName().equals("ROLE_ADMIN"))
                userDTO.setAuthority("Administrator");
            else
                userDTO.setAuthority("User");
        });

        return userDTO;
    }

    @Override
    public User convertToEntity(UserDTO dto) {
        return null;
    }
}
