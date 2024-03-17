package  com.youcode.korea2tv.services.impls;

import  com.youcode.korea2tv.exception.custom.NotFoundMediaException;
import  com.youcode.korea2tv.models.entity.Media;
import   com.youcode.korea2tv.repositories.MediaRepository;
import   com.youcode.korea2tv.services.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {
    private final MediaRepository mediaRepository;
    public Page<Media> findAllMediaPageable(String typeMedia, String searchTerm, Pageable pageable) {
        return mediaRepository.findMediaByTypeMediaContaining(searchTerm, typeMedia, pageable)
                .orElseThrow(() -> new NotFoundMediaException("Not found any media"));
    }

    @Override
    public Media findMediaByShortLink(String shortLink) {
        return mediaRepository.findMediaByShortLink(shortLink)
                .orElseThrow(() -> new NotFoundMediaException("Not this media: "+shortLink));
    }

    @Override
    public Media findMediaByIdTmdb(Long idTmdb) {
        return mediaRepository.findMediaByIdTmdb(idTmdb)
                .orElseThrow(() -> new NotFoundMediaException("Not this media: "+idTmdb));
    }

    @Override
    public List<Media> getTrendingKoreanMovies() {
        LocalDateTime cutoffDateTime = LocalDateTime.now().minusDays(60); // Example cutoff date and time
        LocalDate cutoffDate = cutoffDateTime.toLocalDate(); // Convert LocalDateTime to LocalDate
        List<Media> trendingMovies = mediaRepository.findTrendingMovies(cutoffDate);
        return trendingMovies;
    }


    @Override
    public Boolean checkMediaIsFounded(Media media) {
        Optional<Media> checkMedia = mediaRepository.findMediaByOriginalTitleAndReleaseDate(
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
