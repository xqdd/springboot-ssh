package ${groupId}.mvc.bean.entity;

import ${groupId}.mvc.bean.entity.other.Address;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
public class User {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;
    private String openId;
    private String avatarUrl;
    private String nickName;
    private String trueName;
    private String phoneNumber;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    private String idCardNumber;
    private String payType;
    private String payAccount;


    //介绍人

    //提现金额

    //当前可用金额

    //总金额


}
