package com.choice.soap.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchema;

class WebServiceConfigTest {

  private WebServiceConfig webServiceConfig;
  private ApplicationContext applicationContext;
  private XsdSchema xsdSchema;

  @BeforeEach
  void setUp() {
    applicationContext = mock(ApplicationContext.class);
    webServiceConfig = new WebServiceConfig();
    xsdSchema = mock(XsdSchema.class);
  }

  @AfterEach
  void tearDown() {
    applicationContext = null;
    webServiceConfig = null;
    xsdSchema = null;
  }

  @Test
  void messageDispatcherServlet() {
    ServletRegistrationBean response = webServiceConfig.messageDispatcherServlet(
        applicationContext);
    assertEquals("[/ws/*]", response.getUrlMappings().toString());
  }

  @Test
  void defaultWsdl11Definition() {
    DefaultWsdl11Definition response = webServiceConfig.defaultWsdl11Definition(xsdSchema);
    assertNotNull(response);
  }

  @Test
  void hotelsSchema() {
    XsdSchema response = webServiceConfig.hotelsSchema();
    assertNotNull(response);
  }
}