package org.openspcoop2.core.config.rs.server.model;

import org.openspcoop2.core.config.rs.server.model.BaseSoggettoItem;
import org.openspcoop2.core.config.rs.server.model.StatoApiEnum;
import org.openspcoop2.core.config.rs.server.model.TipoApiEnum;
import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiImplItem extends BaseSoggettoItem {
  
  @Schema(required = true, description = "")
  private String apiNome = null;
  
  @Schema(required = true, description = "")
  private Integer apiVersione = null;
  
  @Schema(description = "")
  private String apiSoapServizio = null;
  
  @Schema(description = "")
  private String tipoServizio = null;
  
  @Schema(required = true, description = "")
  private String nome = null;
  
  @Schema(required = true, description = "")
  private Integer versione = null;
  
  @Schema(description = "")
  private TipoApiEnum apiTipo = null;
  
  @Schema(required = true, description = "")
  private StatoApiEnum stato = null;
  
  @Schema(required = true, description = "")
  private String statoDescrizione = null;
 /**
   * Get apiNome
   * @return apiNome
  **/
  @JsonProperty("api_nome")
  @NotNull
  public String getApiNome() {
    return this.apiNome;
  }

  public void setApiNome(String apiNome) {
    this.apiNome = apiNome;
  }

  public ApiImplItem apiNome(String apiNome) {
    this.apiNome = apiNome;
    return this;
  }

 /**
   * Get apiVersione
   * @return apiVersione
  **/
  @JsonProperty("api_versione")
  @NotNull
  public Integer getApiVersione() {
    return this.apiVersione;
  }

  public void setApiVersione(Integer apiVersione) {
    this.apiVersione = apiVersione;
  }

  public ApiImplItem apiVersione(Integer apiVersione) {
    this.apiVersione = apiVersione;
    return this;
  }

 /**
   * Get apiSoapServizio
   * @return apiSoapServizio
  **/
  @JsonProperty("api_soap_servizio")
  public String getApiSoapServizio() {
    return this.apiSoapServizio;
  }

  public void setApiSoapServizio(String apiSoapServizio) {
    this.apiSoapServizio = apiSoapServizio;
  }

  public ApiImplItem apiSoapServizio(String apiSoapServizio) {
    this.apiSoapServizio = apiSoapServizio;
    return this;
  }

 /**
   * Get tipoServizio
   * @return tipoServizio
  **/
  @JsonProperty("tipo_servizio")
  public String getTipoServizio() {
    return this.tipoServizio;
  }

  public void setTipoServizio(String tipoServizio) {
    this.tipoServizio = tipoServizio;
  }

  public ApiImplItem tipoServizio(String tipoServizio) {
    this.tipoServizio = tipoServizio;
    return this;
  }

 /**
   * Get nome
   * @return nome
  **/
  @JsonProperty("nome")
  @NotNull
  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public ApiImplItem nome(String nome) {
    this.nome = nome;
    return this;
  }

 /**
   * Get versione
   * @return versione
  **/
  @JsonProperty("versione")
  @NotNull
  public Integer getVersione() {
    return this.versione;
  }

  public void setVersione(Integer versione) {
    this.versione = versione;
  }

  public ApiImplItem versione(Integer versione) {
    this.versione = versione;
    return this;
  }

 /**
   * Get apiTipo
   * @return apiTipo
  **/
  @JsonProperty("api_tipo")
  public TipoApiEnum getApiTipo() {
    return this.apiTipo;
  }

  public void setApiTipo(TipoApiEnum apiTipo) {
    this.apiTipo = apiTipo;
  }

  public ApiImplItem apiTipo(TipoApiEnum apiTipo) {
    this.apiTipo = apiTipo;
    return this;
  }

 /**
   * Get stato
   * @return stato
  **/
  @JsonProperty("stato")
  @NotNull
  public StatoApiEnum getStato() {
    return this.stato;
  }

  public void setStato(StatoApiEnum stato) {
    this.stato = stato;
  }

  public ApiImplItem stato(StatoApiEnum stato) {
    this.stato = stato;
    return this;
  }

 /**
   * Get statoDescrizione
   * @return statoDescrizione
  **/
  @JsonProperty("stato_descrizione")
  @NotNull
  public String getStatoDescrizione() {
    return this.statoDescrizione;
  }

  public void setStatoDescrizione(String statoDescrizione) {
    this.statoDescrizione = statoDescrizione;
  }

  public ApiImplItem statoDescrizione(String statoDescrizione) {
    this.statoDescrizione = statoDescrizione;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiImplItem {\n");
    sb.append("    ").append(ApiImplItem.toIndentedString(super.toString())).append("\n");
    sb.append("    apiNome: ").append(ApiImplItem.toIndentedString(this.apiNome)).append("\n");
    sb.append("    apiVersione: ").append(ApiImplItem.toIndentedString(this.apiVersione)).append("\n");
    sb.append("    apiSoapServizio: ").append(ApiImplItem.toIndentedString(this.apiSoapServizio)).append("\n");
    sb.append("    tipoServizio: ").append(ApiImplItem.toIndentedString(this.tipoServizio)).append("\n");
    sb.append("    nome: ").append(ApiImplItem.toIndentedString(this.nome)).append("\n");
    sb.append("    versione: ").append(ApiImplItem.toIndentedString(this.versione)).append("\n");
    sb.append("    apiTipo: ").append(ApiImplItem.toIndentedString(this.apiTipo)).append("\n");
    sb.append("    stato: ").append(ApiImplItem.toIndentedString(this.stato)).append("\n");
    sb.append("    statoDescrizione: ").append(ApiImplItem.toIndentedString(this.statoDescrizione)).append("\n");
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
