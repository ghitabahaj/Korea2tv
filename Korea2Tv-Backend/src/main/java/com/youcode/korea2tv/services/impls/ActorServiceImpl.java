package  com.youcode.korea2tv.services.impls;

import com.youcode.korea2tv.exception.custom.NotFoundMediaException;
import  com.youcode.korea2tv.models.entity.Actor;
import  com.youcode.korea2tv.models.entity.AppUser;
import  com.youcode.korea2tv.repositories.ActorRepository;
import  com.youcode.korea2tv.services.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    private final ActorRepository actorRepository;

    @Override
    public Page<Actor> finAllActorPageable(String searchTerm, Integer numPage, Integer sizePage) {
        return actorRepository.findActorByContaining(searchTerm, PageRequest.of(numPage, sizePage))
                .orElseThrow(() -> new NotFoundMediaException("Not Found Any Media"));
    }

    @Override
    public List<Actor> findAllActor() {
        return actorRepository.findAll();
    }

    @Override
    public Actor findByName(String name) {
        return actorRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Not found any actor "+name));
    }

    @Override
    public Actor createActor(Actor actor) {
        return actorRepository.save(actor);
    }

    @Override
    public Actor updateActor(Long id, Actor actor) {
        actorRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Not found this actor with this id: "+id));
        Actor actorEdit = Actor.builder()
                .id(id)
                .name(actor.getName())
                .picture(actor.getPicture())
                .birthDate(actor.getBirthDate())
                .build();
        return actorRepository.save(actorEdit);
    }

    @Override
    public void deleteActor(Long id) {
        Actor actor = actorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found any user"));
        actorRepository.delete(actor);
    }
}
