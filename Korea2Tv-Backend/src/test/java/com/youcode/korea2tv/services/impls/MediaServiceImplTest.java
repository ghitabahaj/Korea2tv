package com.youcode.korea2tv.services.impls;

import com.youcode.korea2tv.models.entity.Media;
import com.youcode.korea2tv.repositories.MediaRepository;
import com.youcode.korea2tv.services.MediaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)

class MediaServiceImplTest {
    @Mock
    private MediaRepository mediaRepository;

    @InjectMocks
    private MediaServiceImpl mediaService;


    @Test
    public void testSaveMedia() {
        Media media = new Media();
        when(mediaRepository.save(media)).thenReturn(media);

        // When
        Media savedMedia = mediaService.saveMedia(media);

        // Then
        assertEquals(media, savedMedia);
        verify(mediaRepository, times(1)).save(media);
    }


}