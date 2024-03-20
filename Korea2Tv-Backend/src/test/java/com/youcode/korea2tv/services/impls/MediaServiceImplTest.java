package com.youcode.korea2tv.services.impls;

import com.youcode.korea2tv.exception.custom.NotFoundException;
import com.youcode.korea2tv.models.entity.Media;
import com.youcode.korea2tv.repositories.MediaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)

class MediaServiceImplTest {
    @Mock
    private MediaRepository mediaRepository;

    @InjectMocks
    private MediaServiceImpl mediaService;


    @Test
    public void testSaveMedia() {
        Media media = new Media(); // create a Media object
        when(mediaRepository.findMediaByOriginalTitleAndReleaseDate(anyString(), any(LocalDate.class)))
                .thenReturn(Optional.empty()); // Media not found
        when(mediaRepository.save(media)).thenReturn(media);

        Media savedMedia = mediaService.saveMedia(media);

        assertEquals(media, savedMedia);
        verify(mediaRepository, times(1)).save(media);
    }

    @Test
    public void testSaveMediaWhenMediaNotFound() {
        Media media = new Media(); // create a Media object
        when(mediaRepository.findMediaByOriginalTitleAndReleaseDate(anyString(), any(LocalDate.class)))
                .thenReturn(Optional.empty()); // Media not found

        // Test and assert that NotFoundException is thrown
        assertThrows(NotFoundException.class, () -> mediaService.saveMedia(media));
    }

    @Test
    public void testSaveMediaWhenMediaAlreadyExists() {
        Media media = new Media(); // create a Media object
        when(mediaRepository.findMediaByOriginalTitleAndReleaseDate(anyString(), any(LocalDate.class)))
                .thenReturn(Optional.of(media)); // Media already exists

        // Test and assert that IllegalArgumentException is thrown
        assertThrows(IllegalArgumentException.class, () -> mediaService.saveMedia(media));
    }


}