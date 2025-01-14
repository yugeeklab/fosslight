/*
 * Copyright (c) 2021 LG Electronics Inc.
 * SPDX-License-Identifier: AGPL-3.0-only 
 */

package oss.fosslight.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiOssMapper {
	List<Map<String, Object>> getOssInfo(Map<String, Object> paramMap);
	
	String getOssName(String ossName);
	
	List<Map<String, Object>> getOssInfoByDownloadLocation(String downloadLocation);
	
	List<Map<String, Object>> getLicenseInfo(String licenseName);
	
	List<String> selectOssNicknameList(String ossName); 
}
