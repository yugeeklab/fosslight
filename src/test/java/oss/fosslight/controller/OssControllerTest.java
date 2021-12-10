/*
Copyright (c) 2021 Jongun Chae
Copyright (c) 2021 JaeHyeuk Lee
SPDX-License-Identifier: AGPL-3.0-only
*/
package oss.fosslight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import oss.fosslight.domain.OssMaster;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@Transactional
public class OssControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("oss list add should be success When parameter is valid")
    void ossListAddShouldBeSuccess() throws Exception{
        mockMvc.perform(post("/oss/saveAjax")
                .param("ossId","")
                .param("ossName","testOssAdd")
                .param("ossVersion","v1")
                .param("copyright","")
                .param("licenseDiv","")
                .param("downloadLocation","")
                .param("homepage","")
                .param("summaryDescription","")
                .param("ossType", "")
                .param("licenseId ","")
                .param("ossLicensesJson","[]")
                .param("ossNicknames ","")
                .param("licenseType ","PMS")
                .param("obligationType","10")
                .param("comment","")
                .param("validationType ","HOMEPAGE")
                .param("attribution ","")
                .param("addNicknameYn ","N")
                .param("deactivateFlag ","N")
                .param("loginUserName ","user")
                .param("loginUserRole ","ROLE_ADMIN")
                .param("sortField ","")
                .param("sortOrder ","")
                .param("hotYn ","N")
                .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.107 Safari/537.36")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> assertOssAdded(result.getResponse()));

    }

    @Test
    @DisplayName("oss list add should be fail When parameter(ossName) is invalid")
    void ossListAddShouldBeFail() throws Exception{
        mockMvc.perform(post("/oss/saveAjax")
                .param("ossId","")
                .param("ossVersion","v1")
                .param("copyright","")
                .param("licenseDiv","")
                .param("downloadLocation","")
                .param("homepage","")
                .param("summaryDescription","")
                .param("ossType", "")
                .param("licenseId ","")
                .param("ossLicensesJson","[]")
                .param("ossNicknames ","")
                .param("licenseType ","PMS")
                .param("obligationType","10")
                .param("comment","")
                .param("validationType ","HOMEPAGE")
                .param("attribution ","")
                .param("addNicknameYn ","N")
                .param("deactivateFlag ","N")
                .param("loginUserName ","user")
                .param("loginUserRole ","ROLE_ADMIN")
                .param("sortField ","")
                .param("sortOrder ","")
                .param("hotYn ","N")
                .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.107 Safari/537.36")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> {
                    Map<String,String> responseMap = new ObjectMapper().readValue(result.getResponse().getContentAsString(),Map.class);
                    assertThat(responseMap.get("resCd")).isEqualTo("00");
                });

    }

    @Test
    @DisplayName("oss list should be success")
    void ossListShouldBeSuccess() throws Exception{
        mockMvc.perform(get("/oss/list")
                .param("type","oss")
                .param("parameter","")
                .param("ossName","")
                .param("ossNameAllSearchFlag","")
                .param("copyrights","")
                .param("licenseName","")
                .param("licenseNameAllSearchFlag","")
                .param("summaryDescription", "")
                .param("homepage","")
                .param("deactivateFlag","")
                .param("creator","")
                .param("cStartDate ","")
                .param("cEndDate","")
                .param("modifier","")
                .param("mStartDate","")
                .param("mEndDate","")
                .param("licenseType","")
                .param("act","search")
                .param("ossTypeSearch","")
                .param("_search","false")
                .param("nd","1638956903205")
                .param("rows","15")
                .param("page","1")
                .param("sidx","groupKey+asc%2C+")
                .param("sord","asc")
                .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.107 Safari/537.36"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("oss autocomplete should be success")
    void ossAutoCompleteShouldBeSuccess() throws Exception{
        final String OSS_LIST_LENGTH = "23";

        mockMvc.perform(get("/oss/autoCompleteAjax")
                .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.107 Safari/537.36"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    List<OssMaster> responseList = new ObjectMapper().readValue(result.getResponse().getContentAsString(), List.class);
                    assertThat(responseList.size()).isEqualTo(OSS_LIST_LENGTH);
                });
    }

    @Test
    @DisplayName("oss autocomplete should be success")
    void ossAutoCompleteShouldBeSuccess() throws Exception{
        final String OSS_LIST_LENGTH = "23";

        mockMvc.perform(get("/oss/autoCompleteAjax")
                .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.107 Safari/537.36"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    List<OssMaster> responseList = new ObjectMapper().readValue(result.getResponse().getContentAsString(), List.class);
                    assertThat(responseList.size()).isEqualTo(OSS_LIST_LENGTH);
                });
    }

    private void assertOssAdded(MockHttpServletResponse response) throws Exception {
        Map<String, String> responseMap = new ObjectMapper().readValue(response.getContentAsString(), Map.class);
        String ossId = responseMap.get("ossId");
        mockMvc.perform(MockMvcRequestBuilders.get("/oss/edit/" + ossId)
                .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.107 Safari/537.36"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String oss = (String) result.getModelAndView().getModel().get("ossId");
                    assertThat(oss).isEqualTo(ossId);
                });
    }
}
