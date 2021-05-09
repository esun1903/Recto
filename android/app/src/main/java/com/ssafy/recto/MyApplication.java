package com.ssafy.recto;

import android.app.Application;

<<<<<<< HEAD
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
=======
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
>>>>>>> 03ea468 (feat: ✨ APP 사진형 카드 제작 API 연결)
public class MyApplication extends Application {
    private Boolean cardPublic;
    private Integer cardDesign;
    private String cardVideo;
    private String cardPhoto;
    private String cardPhrases;
    private String cardDate;
    private String cardDateNum;
    private String cardPassword;
<<<<<<< HEAD
}
=======
}
>>>>>>> 03ea468 (feat: ✨ APP 사진형 카드 제작 API 연결)
