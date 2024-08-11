package com.sumerge.mapper;

import com.sumerge.enums.AnswerStatus;

public class AnswerStatusMapper {
    public int mapStatus (AnswerStatus answerStatus){
        if (answerStatus.equals(AnswerStatus.CORRECT)){
            return 1;
        }
        return 0;
    }
}
