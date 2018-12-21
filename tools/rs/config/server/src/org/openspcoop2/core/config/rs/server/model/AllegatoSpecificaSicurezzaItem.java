package org.openspcoop2.core.config.rs.server.model;

import org.openspcoop2.core.config.rs.server.model.AllegatoGenericoItem;
import org.openspcoop2.core.config.rs.server.model.TipoSpecificaSicurezzaEnum;
import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AllegatoSpecificaSicurezzaItem extends AllegatoGenericoItem {
  
  @Schema(required = true, description = "")
  private TipoSpecificaSicurezzaEnum tipo = null;
 /**
   * Get tipo
   * @return tipo
  **/
  @JsonProperty("tipo")
  @NotNull
  public TipoSpecificaSicurezzaEnum getTipo() {
    return this.tipo;
  }

  public void setTipo(TipoSpecificaSicurezzaEnum tipo) {
    this.tipo = tipo;
  }

  public AllegatoSpecificaSicurezzaItem tipo(TipoSpecificaSicurezzaEnum tipo) {
    this.tipo = tipo;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllegatoSpecificaSicurezzaItem {\n");
    sb.append("    ").append(AllegatoSpecificaSicurezzaItem.toIndentedString(super.toString())).append("\n");
    sb.append("    tipo: ").append(AllegatoSpecificaSicurezzaItem.toIndentedString(this.tipo)).append("\n");
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