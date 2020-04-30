package com.syscom.ssm.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;


public class MyTag extends SimpleTagSupport {

    private String message;
    private StringWriter sw = new StringWriter();

    public void setMessage(String msg) {
        this.message = msg;
    }


    @Override
    public void doTag() throws JspException, IOException{
        JspWriter out = getJspContext().getOut();
        if (message != null) {
            /* 从属性中使用消息 */
            out.println("message = " + message);
        }else {
            /* 从内容体中使用消息 */
            getJspBody().invoke(sw);
            out.println(sw.toString());
        }
    }


}