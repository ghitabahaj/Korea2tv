package  com.youcode.korea2tv.mapper;

import  com.youcode.korea2tv.dtos.requests.actor.ActorReqDto;
import com.youcode.korea2tv.dtos.response.actor.ActorResDto;
import  com.youcode.korea2tv.models.entity.Actor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActorMapper {
    private final ModelMapper modelMapper;

    public Actor mapToEntity(ActorReqDto reqDto){
        return modelMapper.map(reqDto, Actor.class);

    }
    public ActorResDto mapToDto(Actor actor){
        return modelMapper.map(actor, ActorResDto.class);
    }
}
