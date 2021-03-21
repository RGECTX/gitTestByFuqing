
package com.greathack.homlin.pojo.system;

/**
 * 
 * @Description: TODO
 * @author greathack
 * @date 2017年7月5日
 * @version V1.0
 */
public class AccessToken {

    private String accessToken;
    private Long expireIn;
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public Long getExpireIn() {
        return expireIn;
    }
    public void setExpireIn(Long expireIn) {
        this.expireIn = expireIn;
    }

}
