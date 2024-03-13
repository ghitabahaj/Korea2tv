package  com.youcode.korea2tv.mapper;


import com.youcode.korea2tv.dtos.requests.user.AuthenticateReqDto;
import  com.youcode.korea2tv.dtos.requests.user.RegisterReqDto;
import  com.youcode.korea2tv.models.entity.AppUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public AppUser mapToEntityRegister(RegisterReqDto registerReqDto){
        return modelMapper.map(registerReqDto, AppUser.class);
    }

    public AppUser mapToEntityAuthenticated(AuthenticateReqDto authenticateReqDto){
//        modelMapper.typeMap(AuthenticateReqDto.class, AppUser.class).addMappings(mapper -> {
//            mapper.map(src -> src.getEmail(),
//                    AppUser::setEmail);
//            mapper.map(src -> src.getPassword(),
//                    AppUser::setPassword);
//        });

        return modelMapper.map(authenticateReqDto, AppUser.class);
    }

//    public UserResDto mapToDto(AppUser user){
//        return modelMapper.map(user, UserResDto.class);
//    }
}
