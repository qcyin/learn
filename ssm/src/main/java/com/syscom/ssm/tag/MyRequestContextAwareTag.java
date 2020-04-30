package com.syscom.ssm.tag;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yqc
 * @date 2020/4/2523:10
 */
public class MyRequestContextAwareTag extends RequestContextAwareTag {
    @Override
    protected int doStartTagInternal() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("name", "name");
        pageContext.setAttribute("levelCodeList", map);
        return 0;
    }


}
