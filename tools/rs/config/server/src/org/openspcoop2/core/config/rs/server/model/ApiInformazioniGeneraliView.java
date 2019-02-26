package org.openspcoop2.core.config.rs.server.model;

import org.openspcoop2.core.config.rs.server.model.BaseItem;
import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;

public class ApiInformazioniGeneraliView extends BaseItem {
  
  @Schema(required = true, description = "")
  private String nome = null;
  
  @Schema(example = "1", required = true, description = "")
  private Integer versione = null;
 /**
   * Get nome
   * @return nome
  **/
  @JsonProperty("nome")
  @NotNull
  @Valid
 @Pattern(regexp="^[_A-Za-z][\\-\\._A-Za-z0-9]*$") @Size(max=255)  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public ApiInformazioniGeneraliView nome(String nome) {
    this.nome = nome;
    return this;
  }

 /**
   * Get versione
   * minimum: 1
   * @return versione
  **/
  @JsonProperty("versione")
  @NotNull
  @Valid
 @Min(1)  public Integer getVersione() {
    return this.versione;
  }

  public void setVersione(Integer versione) {
    this.versione = versione;
  }

  public ApiInformazioniGeneraliView versione(Integer versione) {
    this.versione = versione;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiInformazioniGeneraliView {\n");
    sb.append("    ").append(ApiInformazioniGeneraliView.toIndentedString(super.toString())).append("\n");
    sb.append("    nome: ").append(ApiInformazioniGeneraliView.toIndentedString(this.nome)).append("\n");
    sb.append("    versione: ").append(ApiInformazioniGeneraliView.toIndentedString(this.versione)).append("\n");
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
