package  com.youcode.korea2tv.services.impls;

import com.youcode.korea2tv.exception.custom.NotFoundException;
import  com.youcode.korea2tv.exception.custom.NotFoundMediaException;
import com.youcode.korea2tv.models.entity.*;
import com.youcode.korea2tv.repositories.MediaCreditRepository;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {
    private final MediaRepository mediaRepository;
    private final MediaCreditRepository mediaCreditRepository;
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
        LocalDateTime cutoffDateTime = LocalDateTime.now().minusDays(120); // Example cutoff date and time
        LocalDate cutoffDate = cutoffDateTime.toLocalDate(); // Convert LocalDateTime to LocalDate
        List<Media> trendingMovies = mediaRepository.findTrendingMovies(cutoffDate);
        return trendingMovies;
    }

    @Override
    public List<Media> getRelatedMoviesByGenre(Long genreId) {
        return mediaRepository.findByGenresId(genreId); // Assuming you have a method findByGenreId in the repository

    }


    @Override
    public Boolean checkMediaIsFounded(Media media) {
        Optional<Media> checkMedia = mediaRepository.findMediaByOriginalTitleAndReleaseDate(
                media.getOriginalTitle(),
                media.getReleaseDate());
        if(checkMedia.isPresent()) {
            throw new IllegalArgumentException("This media already exists!");
        } else {
            throw new NotFoundException("Media not found!"); // Throw NotFoundException if media not found
        }
    }

    @Override
    public Set<Media> recommended(Set<Country> countries, Set<Genre> genres, Set<Production> productions) {
        return mediaRepository.findMediaByCountriesInAndGenresInOrProductionsIn(countries, genres, productions)
                .orElseThrow(() -> new NotFoundMediaException("no media found"));
    }

    @Override
    public Media saveMedia(Media media) {
        if(Boolean.TRUE.equals(checkMediaIsFounded(media))){
            // No need to save again if media is already found
            return media;
        } else {
            return mediaRepository.save(media);
        }
    }

    @Override
    public Media updateMedia(Long id, Media media) {
        return null;
    }

    @Override
    public Media deleteMedia(Long id) {
        return null;
    }

    @Override
    public List<Media> searchMediaByName(String name) {
        List<Media> mediaList = mediaRepository.findByOriginalTitle(name);
        if (mediaList.isEmpty()) {
            throw new NotFoundException("No media found with the name: " + name);
        }
        return mediaList;
    }

    @Override
    public List<Media> getMediaByActor(String actorId) {
        // Retrieve all media credits associated with the actor
        List<MediaCredit> actorMediaCredits = mediaCreditRepository.findBy_creditIdTmdb(actorId);

        // Extract media from the retrieved media credits

        return actorMediaCredits.stream()
                .map(MediaCredit::getMedia)
                .collect(Collectors.toList());
    }

}
