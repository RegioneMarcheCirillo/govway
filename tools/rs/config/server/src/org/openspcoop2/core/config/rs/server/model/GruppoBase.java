package org.openspcoop2.core.config.rs.server.model;

import java.util.ArrayList;
import java.util.List;
import org.openspcoop2.core.config.rs.server.model.GruppoNome;
import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;

public class GruppoBase extends GruppoNome {
  
  @Schema(example = "[\"az1\",\"az2\",\"az3\"]", required = true, description = "")
  private List<String> azioni = new ArrayList<String>();
 /**
   * Get azioni
   * @return azioni
  **/
  @JsonProperty("azioni")
  @NotNull
  @Valid
  public List<String> getAzioni() {
    return this.azioni;
  }

  public void setAzioni(List<String> azioni) {
    this.azioni = azioni;
  }

  public GruppoBase azioni(List<String> azioni) {
    this.azioni = azioni;
    return this;
  }

  public GruppoBase addAzioniItem(String azioniItem) {
    this.azioni.add(azioniItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GruppoBase {\n");
    sb.append("    ").append(GruppoBase.toIndentedString(super.toString())).append("\n");
    sb.append("    azioni: ").append(GruppoBase.toIndentedString(this.azioni)).append("\n");
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
