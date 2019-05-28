/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2019 Link.it srl (http://link.it).
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openspcoop2.core.config.rs.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets RateLimitingChiaveEnum
 */
public enum RateLimitingChiaveEnum {
CONTENT_BASED("content-based"),
  HEADER_BASED("header-based"),
  SOAP_ACTION_BASED("soap-action-based"),
  URL_BASED("url-based"),
  FORM_BASED("form-based"),
  INDIRIZZO_IP("indirizzo-ip"),
  INDIRIZZO_IP_FORWARDED("indirizzo-ip-forwarded"),
  PLUGIN_BASED("plugin-based");

  private String value;

  RateLimitingChiaveEnum(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(this.value);
  }

  @JsonCreator
  public static RateLimitingChiaveEnum fromValue(String text) {
    for (RateLimitingChiaveEnum b : RateLimitingChiaveEnum.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
  
}
