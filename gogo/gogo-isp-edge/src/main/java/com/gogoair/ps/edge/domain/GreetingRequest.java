package com.gogoair.ps.edge.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GreetingRequest {
  private String name;
  private String languageCode;
  
  public GreetingRequest() {}
  
  public GreetingRequest(String name, String languageCode) {
    this.name = name;
    this.languageCode = languageCode;
  }

  public String getName() {
	return name;
  }

  public void setName(String name) {
	this.name = name;
  }
 
  public String getLanguageCode() {
	return languageCode;
  }

  public void setLanguageCode(String languageCode) {
	this.languageCode = languageCode;
  }
}
