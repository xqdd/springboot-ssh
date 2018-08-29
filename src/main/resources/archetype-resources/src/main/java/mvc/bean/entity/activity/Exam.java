package ${groupId}.mvc.bean.entity.activity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam {

    @Id
    private Integer id;
    private String question;
    private String a;
    private String b;
    private String c;
    private String d;
    private Integer ta;
    private String imageurl;
    @Column(length = 2000)
    private String bestanswer;
    private String bestanswerid;
    @JsonProperty("Type")
    private Integer Type;
    private String sinaimg;


}
