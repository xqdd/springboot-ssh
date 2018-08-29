package ${groupId}.mvc.bean.redis;

/**
 * 用于保存用户token信息
 */
public class UserToken {

    public UserToken(String deviceId, String secret) {
        this.deviceId = deviceId;
        this.secret = secret;
    }

    private String deviceId;
    private String secret;


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
