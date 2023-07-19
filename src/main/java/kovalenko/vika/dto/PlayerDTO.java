package kovalenko.vika.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class PlayerDTO {
    private String nickName;
    private Map<String, Integer> playerStatistic;
}
