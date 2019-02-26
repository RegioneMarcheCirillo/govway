package org.openspcoop2.core.config.rs.server.model;

import org.openspcoop2.core.config.rs.server.model.BaseItem;
import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;

public class ApiReferenteView extends BaseItem {
  
  @Schema(required = true, description = "")
  private String referente = null;
 /**
   * Get referente
   * @return referente
  **/
  @JsonProperty("referente")
  @NotNull
  @Valid
 @Pattern(regexp="^[0-9A-Za-z]+$") @Size(max=255)  public String getReferente() {
    return this.referente;
  }

  public void setReferente(String referente) {
    this.referente = referente;
  }

  public ApiReferenteView referente(String referente) {
    this.referente = referente;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiReferenteView {\n");
    sb.append("    ").append(ApiReferenteView.toIndentedString(super.toString())).append("\n");
    sb.append("    referente: ").append(ApiReferenteView.toIndentedString(this.referente)).append("\n");
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
