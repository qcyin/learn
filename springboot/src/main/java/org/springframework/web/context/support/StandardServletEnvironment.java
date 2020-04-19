package org.springframework.web.context.support;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

public class StandardServletEnvironment extends StandardEnvironment implements ConfigurableEnvironment {

    /** Servlet context init parameters property source name: {@value} */
    public static final String SERVLET_CONTEXT_PROPERTY_SOURCE_NAME = "servletContextInitParams";

    /** Servlet config init parameters property source name: {@value} */
    public static final String SERVLET_CONFIG_PROPERTY_SOURCE_NAME = "servletConfigInitParams";

    /** JNDI property source name: {@value} */
    public static final String JNDI_PROPERTY_SOURCE_NAME = "jndiProperties";
}
