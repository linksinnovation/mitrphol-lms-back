/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mirtphol.lms.model.authen;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
@lombok.Data
public class Data {
    @JsonProperty("user_info")
    private UserInfo userInfo;
       
}
