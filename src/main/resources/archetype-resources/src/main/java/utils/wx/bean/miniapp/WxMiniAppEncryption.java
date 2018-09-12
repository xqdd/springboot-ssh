package ${groupId}.utils.wx.bean.miniapp;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class WxMiniAppEncryption {
    @NotEmpty
    private String iv;

    @NotEmpty
    private String encryptedData;


}
