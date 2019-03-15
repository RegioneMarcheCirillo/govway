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

import org.openspcoop2.core.config.rs.server.model.ModalitaIdentificazioneAzioneEnum;
import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;

public class ApiImplModalitaIdentificazioneAzione  {
  
  @Schema(required = true, description = "")
  private ModalitaIdentificazioneAzioneEnum modalita = null;
  
  @Schema(description = "XPath/JsonPath nel caso di modalità 'content-based' o espressione regolare nel caso 'url-based'")
 /**
   * XPath/JsonPath nel caso di modalità 'content-based' o espressione regolare nel caso 'url-based'  
  **/
  private String pattern = null;
  
  @Schema(description = "Nome dell'header http nel caso di modalità 'header-based'")
 /**
   * Nome dell'header http nel caso di modalità 'header-based'  
  **/
  private String nome = null;
  
  @Schema(description = "Indicazione se oltre alla modalità indicata per individuare l'azione viene usata comunque la modalità 'interface-based'")
 /**
   * Indicazione se oltre alla modalità indicata per individuare l'azione viene usata comunque la modalità 'interface-based'  
  **/
  private Boolean forceInterface = true;
 /**
   * Get modalita
   * @return modalita
  **/
  @JsonProperty("modalita")
  @NotNull
  @Valid
  public ModalitaIdentificazioneAzioneEnum getModalita() {
    return this.modalita;
  }

  public void setModalita(ModalitaIdentificazioneAzioneEnum modalita) {
    this.modalita = modalita;
  }

  public ApiImplModalitaIdentificazioneAzione modalita(ModalitaIdentificazioneAzioneEnum modalita) {
    this.modalita = modalita;
    return this;
  }

 /**
   * XPath/JsonPath nel caso di modalità 'content-based' o espressione regolare nel caso 'url-based'
   * @return pattern
  **/
  @JsonProperty("pattern")
  @Valid
 @Size(max=255)  public String getPattern() {
    return this.pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public ApiImplModalitaIdentificazioneAzione pattern(String pattern) {
    this.pattern = pattern;
    return this;
  }

 /**
   * Nome dell'header http nel caso di modalità 'header-based'
   * @return nome
  **/
  @JsonProperty("nome")
  @Valid
 @Size(max=255)  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public ApiImplModalitaIdentificazioneAzione nome(String nome) {
    this.nome = nome;
    return this;
  }

 /**
   * Indicazione se oltre alla modalità indicata per individuare l'azione viene usata comunque la modalità 'interface-based'
   * @return forceInterface
  **/
  @JsonProperty("force_interface")
  @Valid
  public Boolean isForceInterface() {
    return this.forceInterface;
  }

  public void setForceInterface(Boolean forceInterface) {
    this.forceInterface = forceInterface;
  }

  public ApiImplModalitaIdentificazioneAzione forceInterface(Boolean forceInterface) {
    this.forceInterface = forceInterface;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiImplModalitaIdentificazioneAzione {\n");
    
    sb.append("    modalita: ").append(ApiImplModalitaIdentificazioneAzione.toIndentedString(this.modalita)).append("\n");
    sb.append("    pattern: ").append(ApiImplModalitaIdentificazioneAzione.toIndentedString(this.pattern)).append("\n");
    sb.append("    nome: ").append(ApiImplModalitaIdentificazioneAzione.toIndentedString(this.nome)).append("\n");
    sb.append("    forceInterface: ").append(ApiImplModalitaIdentificazioneAzione.toIndentedString(this.forceInterface)).append("\n");
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
