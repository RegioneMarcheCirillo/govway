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

import org.openspcoop2.core.config.rs.server.model.RateLimitingPolicyBaseErogazione;
import org.openspcoop2.core.config.rs.server.model.RateLimitingPolicyEnum;
import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;

public class RateLimitingPolicyErogazioneNew extends RateLimitingPolicyBaseErogazione {
  
  @Schema(required = true, description = "")
  private RateLimitingPolicyEnum policy = null;
 /**
   * Get policy
   * @return policy
  **/
  @JsonProperty("policy")
  @NotNull
  @Valid
  public RateLimitingPolicyEnum getPolicy() {
    return this.policy;
  }

  public void setPolicy(RateLimitingPolicyEnum policy) {
    this.policy = policy;
  }

  public RateLimitingPolicyErogazioneNew policy(RateLimitingPolicyEnum policy) {
    this.policy = policy;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RateLimitingPolicyErogazioneNew {\n");
    sb.append("    ").append(RateLimitingPolicyErogazioneNew.toIndentedString(super.toString())).append("\n");
    sb.append("    policy: ").append(RateLimitingPolicyErogazioneNew.toIndentedString(this.policy)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private static String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
