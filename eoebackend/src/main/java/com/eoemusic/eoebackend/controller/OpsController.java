package com.eoemusic.eoebackend.controller;

import com.eoemusic.eoebackend.config.Constants;
import com.eoemusic.eoebackend.entity.Music;
import com.eoemusic.eoebackend.enums.SyncCSVEnum;
import com.eoemusic.eoebackend.repository.MusicRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

/**
 * @description: 运维操作Controller
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 18/01/23 4:16 AM
 */
@RestController
@Slf4j
@RequestMapping("/eoebackend")
public class OpsController {

  @Autowired
  private MusicRepository musicRepository;
  
  @PostMapping("/syncDatabase")
  public ResponseEntity<String> syncDatabase(@RequestParam("file") MultipartFile file,
      @RequestParam("alistAudioPath") String alistAudioPath,
      @RequestParam("alistCoverPath") String alistCoverPath) throws IOException {

    BufferedReader fileReader = new BufferedReader(
        new InputStreamReader(file.getInputStream(), "UTF-8"));
    CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withTrim());
    ArrayList<Music> musicList = new ArrayList<>();
    List<CSVRecord> csvData = csvParser.getRecords();
    List<Music> allMusicDB = musicRepository.findAll();
    for (CSVRecord csvDatum : csvData) {
      String[] singerArr = csvDatum.get(SyncCSVEnum.SINGER.getColumnNum()).split(" ");
      String singer = csvDatum.get(SyncCSVEnum.SINGER.getColumnNum()).replaceAll("\\s+", "");
      if (!isJoint(singerArr)) {
        singer = "L";
      }
      String versionRemark =
          csvDatum.get(SyncCSVEnum.VERSION_REMARK.getColumnNum()).length() == 0 ? ""
              : " 【" + csvDatum.get(SyncCSVEnum.VERSION_REMARK.getColumnNum()) + "】";
      String partialUrl = "/" + encodeValue(
          new StringBuilder().append(csvDatum.get(SyncCSVEnum.SONG_DATE.getColumnNum()))
              .append(" ")
              .append(singer).append(" ")
              .append(csvDatum.get(SyncCSVEnum.SONG_NAME.getColumnNum()))
              .append(versionRemark).append(".").append(csvDatum
              .get(SyncCSVEnum.AUDIO_MEDIA_TYPE.getColumnNum())).toString());
      String coverMediaType = "png";
      String musicID = csvDatum.get(SyncCSVEnum.ID.getColumnNum()).replaceAll("[^A-Za-z0-9]", "");
      Music musicDB = allMusicDB.stream().filter(m -> m.getId().equals(musicID)).findFirst()
          .orElse(null);
      Music music = musicDB == null ? new Music() : musicDB;
      music.setId(musicID);
      music.setUpdateTime(Long.valueOf(csvDatum.get(SyncCSVEnum.UPDATE_TIME.getColumnNum())));
      music.setSongName(csvDatum.get(SyncCSVEnum.SONG_NAME.getColumnNum()));
      music.setSongNameAlias(csvDatum.get(SyncCSVEnum.SONG_NAME_ALIAS.getColumnNum()));
      music.setSinger(csvDatum.get(SyncCSVEnum.SINGER.getColumnNum()));
      music.setSongDate(csvDatum.get(SyncCSVEnum.SONG_DATE.getColumnNum()));
      music.setVersionRemark(csvDatum.get(SyncCSVEnum.VERSION_REMARK.getColumnNum()));
      music.setAudioMediaType(csvDatum.get(SyncCSVEnum.AUDIO_MEDIA_TYPE.getColumnNum()));
      music.setCoverMediaType(coverMediaType);
      music.setDuration(Integer.valueOf(csvDatum.get(SyncCSVEnum.DURATION.getColumnNum())));
      music.setSongLanguage(csvDatum.get(SyncCSVEnum.SONG_LANGUAGE.getColumnNum()));
      music.setSongStatus(csvDatum.get(SyncCSVEnum.SONG_STATUS.getColumnNum()));
      music.setAlistAudioPath(alistAudioPath);
      music.setAlistCoverPath(alistCoverPath);
      music.setPartialUrl(partialUrl);
      music.setHitCount(musicDB == null ? 0 : musicDB.getHitCount());
      musicList.add(music);
    }
    musicRepository.saveAll(musicList);
    return ResponseEntity.status(HttpStatus.OK).body("successfully sync");
  }

  private boolean isJoint(String[] singers) {
    for (String singer : singers) {
      if (!Constants.eoeSinger.contains(singer)) {
        return false;
      }
    }
    return true;
  }

  private String encodeValue(String value) {
    return UriUtils.encodePath(value, "UTF-8");
  }


}
