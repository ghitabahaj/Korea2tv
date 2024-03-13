package  com.youcode.korea2tv.services.impls;

import  com.youcode.korea2tv.exception.custom.NotFoundMediaException;
import  com.youcode.korea2tv.models.entity.Media;
import   com.youcode.korea2tv.repositories.MediaRepository;
import   com.youcode.korea2tv.services.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {
    private final MediaRepository mediaRepository;
    public Page<Media> findAllMediaPageable(String searchTerm, Integer numPage, Integer sizePage) {
        return mediaRepository.findMediaByContaining(searchTerm, PageRequest.of(numPage, sizePage))
                .orElseThrow(() -> new NotFoundMediaException("Not Found Any Media"));
    }

    @Override
    public Boolean checkMediaIsFounded(Media media) {
        Optional<Media> checkMedia = mediaRepository.findMediaByOrOriginalTitleAndReleaseDate(
                media.getOriginalTitle(),
                media.getReleaseDate());
        if(checkMedia.isPresent()){
            throw new IllegalArgumentException("This media already exist!");
        }
        return true;
    }

    @Override
    public Media saveMedia(Media media) {
        Media saveMedia = mediaRepository.save(media);
        if(Boolean.TRUE.equals(checkMediaIsFounded(media))){
            return mediaRepository.save(media);
        }
        return saveMedia;
    }

    @Override
    public Media updateMedia(Long id, Media media) {
        return null;
    }

    @Override
    public Media deleteMedia(Long id) {
        return null;
    }

}
