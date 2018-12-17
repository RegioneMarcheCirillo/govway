package org.openspcoop2.core.config.rs.server.model;

import org.openspcoop2.core.config.rs.server.model.ApiItem;

public class ApiViewItem extends ApiItem {

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiViewItem {\n");
    sb.append("    ").append(ApiViewItem.toIndentedString(super.toString())).append("\n");
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
