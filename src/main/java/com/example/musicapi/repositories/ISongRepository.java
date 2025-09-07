package com.example.musicapi.repositories;

import com.example.musicapi.dtos.song_dtos.SongInfoDto;
import com.example.musicapi.dtos.song_dtos.SongInfoLikeDto;
import com.example.musicapi.entities.Song;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ISongRepository extends JpaRepository<Song, Long> {
    Optional<Song> findByTitleAndAuthor(String title, String author);

    List<Song> findByTitle(String title);

    @Query("""
        SELECT new com.example.musicapi.dtos.song_dtos.SongInfoLikeDto(
            s.id,
            s.title,
            s.author,
            s.addedAt,
            SIZE(s.likes),
            s.duration,
            s.genre.id,
            false, p.user.username)
        FROM Song s JOIN s.playlists p
        WHERE LOWER(CONCAT(s.title, ' - ', s.author)) LIKE LOWER(CONCAT('%', :query, '%'))
        ORDER BY SIZE(s.likes) DESC
    """)
    List<SongInfoLikeDto> findSongByQuery(@Param("query") String query);

    @Query(value = """
           SELECT CONCAT(s.title, ' - ', s.author) FROM songs s
           WHERE LOWER(CONCAT(s.title, ' - ', s.author)) LIKE LOWER(CONCAT('%', :query, '%'))
           LIMIT 6
    """,  nativeQuery = true)
    List<String> searchByTitleAuthor(@Param("query") String query);


    @Query("SELECT new com.example.musicapi.dtos.song_dtos.SongInfoDto(" +
            "s.id, " +
            "s.title, " +
            "s.author, " +
            "s.addedAt, " +
            "SIZE(s.likes), " +
            "s.duration, " +
            "s.genre.id) " +
            "FROM Song s JOIN s.playlists p " +
            "WHERE p.id = :playlistId")
    List<SongInfoDto> getAllWithoutData(@Param("playlistId") Long playlistId);

    @Query("SELECT s.data FROM Song s WHERE s.id = :id")
    byte[] getDataById(Long id);

    @Query("""
        SELECT new com.example.musicapi.dtos.song_dtos.SongInfoLikeDto(
        s.id,
        s.title,
        s.author,
        s.addedAt,
        SIZE(s.likes),
        s.duration,
        s.genre.id,
        CASE WHEN EXISTS (
            SELECT l FROM s.likes l
            WHERE l.user.id = :userId
        )
        THEN TRUE ELSE FALSE END,
        p.user.username)
        FROM Song s JOIN s.playlists p
        ORDER BY SIZE(s.likes) DESC""")
    List<SongInfoLikeDto> getAllPopular(@Param("userId") Long userId);

    @Query("""
    SELECT new com.example.musicapi.dtos.song_dtos.SongInfoLikeDto(
    s.id,
    s.title,
    s.author,
    s.addedAt,
    SIZE(s.likes),
    s.duration,
    s.genre.id,
    CASE WHEN EXISTS (
        SELECT l FROM s.likes l
        WHERE l.user.id = :userId
    )
    THEN TRUE ELSE FALSE END,
    p.user.username)
    FROM Song s
    JOIN s.playlists p
    WHERE (:cursor IS NULL OR s.id > :cursor)
    ORDER BY SIZE(s.likes) DESC, s.id ASC""")
    List<SongInfoLikeDto> getAllPopularPage(
            @Param("userId") Long userId,
            @Param("cursor") Long cursor,
            Pageable pageable
    );

    @Query("""
        SELECT new com.example.musicapi.dtos.song_dtos.SongInfoLikeDto(
            s.id,
            s.title,
            s.author,
            s.addedAt,
            SIZE(s.likes),
            s.duration,
            s.genre.id,
            false, ""
        )
        FROM Playlist p JOIN p.songs s
        WHERE p.user.id = :userId
    """)
    List<SongInfoLikeDto> getAllSongsOfUser(@Param("userId") Long userId);
}
