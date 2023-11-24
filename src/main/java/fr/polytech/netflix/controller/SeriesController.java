package fr.polytech.netflix.controller;

import fr.polytech.netflix.dtos.SeriesDto;
import fr.polytech.netflix.services.MinioService;
import fr.polytech.netflix.services.SeriesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SeriesController {

    private final SeriesService seriesService;
    private final MinioService minioService;

    @GetMapping("/series")
    public @ResponseBody List<SeriesDto> getAllSeries(){
        log.info("Get: all series");
        return seriesService.getAllSeries().stream()
                .map(SeriesDto::toDto)
                .toList();
    }

    @GetMapping("/series/{id}")
    public @ResponseBody SeriesDto getSeriesById(@PathVariable Integer id){
        log.info("Get: series with id: " + id);
        return SeriesDto.toDto(seriesService.getSeries(id));
    }

    @PostMapping("/series")
    public void addSeries(@RequestBody SeriesDto series){
        log.info("Post: adding new series " + series.getName());
        // TODO validation input
        seriesService.createSeries(series);
    }

    @DeleteMapping("/series/{id}")
    public void removeSeries(@PathVariable Integer id){
        log.info("Delete: deleting series with id: " + id);
        seriesService.deleteSeries(id);
    }

    @GetMapping("/series/{id}/cover/uploadLink")
    public String getLinkUploadSeriesCover(@PathVariable Integer id){
        log.info("Get: update link for cover of series with id: " + id);
        return seriesService.getUploadLink(id);
    }

    @GetMapping("/series/{id}/cover/downloadLink")
    public String getLinkDownloadSeriesCover(@PathVariable Integer id){
        log.info("Get: downloading link for cover of series with id: " + id);
        return seriesService.getDownloadLink(id);
    }

}
